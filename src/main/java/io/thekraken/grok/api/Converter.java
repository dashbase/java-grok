package io.thekraken.grok.api;

import com.google.common.base.CharMatcher;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Convert String argument to the right type.
 *
 * @author anthonyc
 *
 */
public class Converter {
  public enum Type {
    BYTE(s -> Byte.parseByte(s.toString())),
    BOOLEAN(s -> Boolean.parseBoolean(s.toString())),
    SHORT(s -> Short.parseShort(s.toString())),
    INT(s -> Integer.parseInt(s, 0, s.length(), 10), "integer"),
    LONG(s -> Long.parseLong(s, 0, s.length(), 10)),
    FLOAT(s -> Float.parseFloat(s.toString())),
    DOUBLE(s -> Double.parseDouble(s.toString())),
    DATETIME(new DateConverter(), "date"),
    STRING(v -> v, "text"),

    // Dashbase specific type
    META(v -> v, "sorted"),

    // Dashbase specific type
    ID(v -> v, "key");

    public final IConverter<?> converter;
    public final List<String> aliases;

    Type(IConverter<?> converter, String... aliases) {
      this.converter = converter;
      this.aliases = Arrays.asList(aliases);
    }

  }

  private static final CharMatcher DELIMITER = CharMatcher.anyOf(";:");

  private static final Splitter SPLITTER = Splitter.on(DELIMITER).limit(3);

  private static final Map<String, Type> TYPES =
      Arrays.asList(Type.values()).stream()
          .collect(Collectors.toMap(t -> t.name().toLowerCase(), t -> t));

  private static final Map<String, Type> TYPE_ALIASES =
      Arrays.asList(Type.values()).stream()
        .flatMap(type -> type.aliases.stream().map(alias -> new AbstractMap.SimpleEntry<>(alias, type)))
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

  private static Type getType(String key) {
    key = key.toLowerCase();
    Type type = TYPES.getOrDefault(key, TYPE_ALIASES.get(key));
    if (type == null) {
      throw new IllegalArgumentException("Invalid data type :" + key);
    }
    return type;
  }

  public static Map<String, IConverter> getConverters(Collection<String> groupNames, Object... params) {
    return groupNames.stream()
        .filter(group -> Converter.DELIMITER.matchesAnyOf(group))
        .collect(Collectors.toMap(Function.identity(), key -> {
          List<String> list = SPLITTER.splitToList(key);
          IConverter converter = getType(list.get(1)).converter;
          if (list.size() == 3) {
            converter = converter.newConverter(list.get(2), params);
          }
          return converter;
        }));
  }

  public static Map<String, Type> getGroupTypes(Collection<String> groupNames) {
    return groupNames.stream()
        .filter(group -> Converter.DELIMITER.matchesAnyOf(group))
        .map(group -> SPLITTER.splitToList(group))
        .collect(Collectors.toMap(
            l -> l.get(0),
            l -> getType(l.get(1))
        ));
  }

  public static String extractKey(String key) {
    if (DELIMITER.matchesAnyOf(key)) {
      return SPLITTER.split(key).iterator().next();
    }
    return key;
  }
}

//
// Converters
//
interface IConverter<T> {
  T convert(CharSequence value);

  default IConverter<T> newConverter(String param, Object... params) {
    return this;
  }
}


class DateConverter implements IConverter<Instant> {
  private final DateTimeFormatter formatter;
  private final ZoneId timeZone;
  private final boolean useCache;
  private final String pattern;

  private final Cache<CharSequence, Instant> timestampCache =
      CacheBuilder.newBuilder().maximumSize(1000).build();

  public DateConverter() {
    this.formatter = DateTimeFormatter.ISO_DATE_TIME;
    this.timeZone = ZoneOffset.UTC;
    this.useCache = true;
    this.pattern = null;
  }

  private DateConverter(DateTimeFormatter formatter, String pattern, ZoneId timeZone, boolean useCache) {
    this.formatter = formatter;
    this.timeZone = timeZone;
    this.useCache = useCache;
    this.pattern = pattern;
  }

  public boolean containsYear() {
    if (pattern != null) {
      var format = pattern.toLowerCase();
      return format.contains("u") || format.contains("y");
    }
    return false;
  }

  @Override
  public Instant convert(CharSequence value) {
    if (useCache) {
      var ts = timestampCache.getIfPresent(value);
      if (ts != null) {
        return ts;
      }
    }

    var formatter = this.formatter;

    if (!containsYear()) {
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
    boolean useCache = !(param.contains("S") || param.contains("n") || param.contains("N") || param.contains("A"));
    return new DateConverter(DateTimeFormatter.ofPattern(param), param, (ZoneId) params[0], useCache);
  }
}
