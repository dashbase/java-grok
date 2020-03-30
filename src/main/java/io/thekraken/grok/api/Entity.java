package io.thekraken.grok.api;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Entity {
    private final CharSequence subject;
    public final int start;
    public final int end;
    public final int ignoreStart;
    public final int ignoreEnd;
    @Nullable
    private final IConverter converter;

    public List<Entity> additionalEntities = Collections.emptyList();


    public Entity(CharSequence subject, int start, int end, int ignoreStart, int ignoreEnd, IConverter converter) {
        this.subject = subject;

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
        this.ignoreStart = ignoreStart;
        this.ignoreEnd = ignoreEnd;
        this.converter = converter;
    }

    public Object getValue() {
        CharSequence substring = ignoreStart >= start && ignoreEnd <= end
            ? subject.subSequence(start, ignoreStart).toString() + subject.subSequence(ignoreEnd, end)
            : subject.subSequence(start, end);

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
        return ignoreStart >= 0 ? getValue().toString() : getValue() + "[" + start + "," + end + "]";
    }
}
