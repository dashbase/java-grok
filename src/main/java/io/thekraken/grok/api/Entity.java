package io.thekraken.grok.api;

import com.google.common.collect.Lists;

import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;

public class Entity {
    public String groupName;
    public final int start;
    public final int end;

    private List<Entity> additionalEntities = null;

    @Nullable
    private IConverter converter;

    public Entity(String groupName, int start, int end) {
        this.groupName = groupName;
        this.start = start;
        this.end = end;
    }

    public void setConverter(IConverter converter) {
        this.converter = converter;
    }

    public Object getValue(CharSequence subject) {
        var substring = subject.subSequence(start, end);
        if (converter != null) {
            return converter.convert(substring.toString());
        }
        return substring;
    }

    public void addEntity(Entity entity) {
        if (additionalEntities == null) {
            additionalEntities = new LinkedList<>();
        }
        additionalEntities.add(entity);
    }

    @Override
    public String toString() {
        return groupName + "[" + start + "," + end + "]";
    }
}
