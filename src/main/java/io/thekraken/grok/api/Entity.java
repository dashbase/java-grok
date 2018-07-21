package io.thekraken.grok.api;

import com.google.common.collect.Lists;

import java.util.List;

public class Entity {
    public Object value;
    public final int start;
    public final int end;

    public final List<Entity> additionalEntities = Lists.newLinkedList();

    public Entity(Object value, int start, int end) {
        this.value = value;
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        return value + "[" + start + "," + end + "]";
    }
}
