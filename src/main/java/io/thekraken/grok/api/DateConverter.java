package io.thekraken.grok.api;

import com.google.common.base.Preconditions;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;

public class DateConverter implements IConverter<Instant> {
  public static final String EPOCH_PATTERN = "epoch";

  private final DateTimeFormatter formatter;
  private final ZoneId timeZone;
  private final boolean useCache;
  private final String pattern;
  private final boolean containsYear;

  private final Cache<CharSequence, Instant> timestampCache =
      CacheBuilder.newBuilder().maximumSize(1000).build();

  public DateConverter() {
    this.formatter = DateTimeFormatter.ISO_DATE_TIME;
    this.timeZone = ZoneOffset.UTC;
    this.useCache = true;
    this.pattern = null;
    this.containsYear = true;
  }

  private DateConverter(String pattern, ZoneId timeZone) {
    this.formatter = EPOCH_PATTERN.equals(pattern) ? null : DateTimeFormatter.ofPattern(pattern);
    this.timeZone = timeZone;
    this.useCache = !(pattern.contains("S") || pattern.contains("n") || pattern.contains("N") || pattern.contains("A"));
    this.pattern = pattern;
    var format = pattern.toLowerCase();
    this.containsYear = format.contains("u") || format.contains("y");
  }

  @Override
  public Instant convert(CharSequence value) {
    if (useCache) {
      var ts = timestampCache.getIfPresent(value);
      if (ts != null) {
        return ts;
      }
    }

    if (this.formatter == null) {
      // epoch
      try {
        var epoch = Long.parseLong(value, 0, value.length(), 10);
        if (epoch > 946731600000L) { // 946731600000 = 2000/1/1
          // parse as milli second
          return Instant.ofEpochMilli(epoch);
        }
        return Instant.ofEpochSecond(epoch);
      } catch (NumberFormatException e) {
        int indexOfDecimal = value.toString().indexOf('.');
        if (indexOfDecimal < 0) {
          throw new IllegalArgumentException(value + " cannot be parsed as epoch");
        }
        var epochSec = Long.parseLong(value, 0, indexOfDecimal, 10);
        var epochNano = Long.parseLong(value, indexOfDecimal + 1, value.length(), 10);
        return Instant.ofEpochSecond(epochSec, epochNano);
      }
    }

    var formatter = this.formatter;

    if (!containsYear) {
      LocalDate today = LocalDate.now(timeZone);
      formatter = new DateTimeFormatterBuilder().append(this.formatter)
              .parseDefaulting(ChronoField.YEAR, today.getYear())
              .parseDefaulting(ChronoField.MONTH_OF_YEAR, today.getMonthValue())
              .parseDefaulting(ChronoField.DAY_OF_MONTH, today.getDayOfMonth())
              .toFormatter().withZone(timeZone);
    }

    TemporalAccessor dt = formatter.parseBest(value, ZonedDateTime::from, LocalDateTime::from);
    Instant ts;
    if (dt instanceof ZonedDateTime) {
      ts =  ((ZonedDateTime)dt).toInstant();
    } else {
      ts = ((LocalDateTime) dt).atZone(timeZone).toInstant();
    }
    if (useCache) {
      timestampCache.put(value, ts);
    }
    return ts;
  }

  @Override
  public DateConverter newConverter(String param, Object... params) {
    Preconditions.checkArgument(params.length == 1 && params[0] instanceof ZoneId);
    return new DateConverter(param, (ZoneId) params[0]);
  }
}
