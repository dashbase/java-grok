package io.thekraken.grok.api;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Entity {
    public final CharSequence subject;
    public final String groupName;
    public final int start;
    public final int end;
    @Nullable
    private final IConverter converter;

    public List<Entity> additionalEntities = Collections.emptyList();


    public Entity(CharSequence subject, String groupName, int start, int end, IConverter converter) {
        this.subject = subject;
        this.groupName = groupName;

        if (start != end) {
            char firstChar = subject.charAt(start);
            char lastChar = subject.charAt(end - 1);
            if (firstChar == lastChar && (firstChar == '"' || firstChar == '\'')) {
                start++;
                end--;
            }
        }
        this.start = start;
        this.end = end;
        this.converter = converter;
    }

    public Object getValue() {
        var substring = subject.subSequence(start, end);
        if (converter != null) {
            return converter.convert(substring);
        }
        return substring;
    }

    public void addEntity(Entity entity) {
        if (additionalEntities.isEmpty()) {
            additionalEntities = new LinkedList<>();
        }
        additionalEntities.add(entity);
    }

    @Override
    public String toString() {
        return getValue() + "[" + start + "," + end + "]";
    }
}
