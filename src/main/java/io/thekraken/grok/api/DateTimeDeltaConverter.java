package io.thekraken.grok.api;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class DateTimeDeltaConverter implements IConverter<Duration> {
    private final ChronoUnit unit;

    public DateTimeDeltaConverter() {
        this(ChronoUnit.MICROS.name());
    }

    public DateTimeDeltaConverter(String unit) {
        this.unit = ChronoUnit.valueOf(unit.toUpperCase());
    }


    @Override
    public IConverter<Duration> newConverter(String param, Object... params) {
        return new DateTimeDeltaConverter(param);
    }

    @Override
    public Duration convert(CharSequence text) {
        long amount = Long.parseLong(text, 0, text.length(), 10);
        return Duration.of(amount, unit);
    }
}
