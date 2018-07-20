package io.thekraken.grok.api;

public class Entity {
    public final String value;
    public final int start;
    public final int end;

    public Entity(String value, int start, int end) {
        this.value = value;
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        return value + "[" + start + "," + end + "]";
    }
}
