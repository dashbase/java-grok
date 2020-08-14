/*******************************************************************************
 * Copyright 2014 Anthony Corbacho and contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package io.thekraken.grok.api;


import com.google.common.collect.Maps;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.Map;
import java.util.regex.Matcher;

import static java.lang.String.format;

/**
 * {@code Match} is a representation in {@code Grok} world of your log.
 *
 * @author anthonycorbacho
 * @since 0.0.1
 */
public class Match {

  private final CharSequence subject; // texte
  private final Grok grok;
  private final Matcher match;
  private final int start;
  private final int end;
  private final boolean containsIgnore;

  private Map<String, Entity> capture = Collections.emptyMap();

  /**
   * Create a new {@code Match} object.
   */
  public Match(CharSequence subject, Grok grok, Matcher match, int start, int end, boolean containsIgnore) {
    this.subject = subject;
    this.grok = grok;
    this.match = match;
    this.start = start;
    this.end = end;
    this.containsIgnore = containsIgnore;
  }

  /**
   * Create Empty grok matcher.
   */
  public static final Match EMPTY = new Match("", null, null, 0, 0, false);

  public Matcher getMatch() {
    return match;
  }

  public int getStart() {
    return start;
  }

  public int getEnd() {
    return end;
  }

  /**
   * Retrurn the single line of log.
   *
   * @return the single line of log
   */
  public CharSequence getSubject() {
    return subject;
  }

  /**
   * Match to the <tt>subject</tt> the <tt>regex</tt> and save the matched element into a map.
   *
   * Multiple values for the same key are stored as list.
   *
   */
  public Map<String, Entity> capture() {
    return capture(false);
  }

  /**
   * Match to the <tt>subject</tt> the <tt>regex</tt> and save the matched element into a map
   *
   * Multiple values to the same key are flattened to one value: the sole non-null value will be captured.
   * Should there be multiple non-null values a RuntimeException is being thrown.
   *
   * This can be used in cases like: (foo (.*:message) bar|bar (.*:message) foo) where the regexp guarantees that only
   * one value will be captured.
   *
   * See also {@link #capture} which returns multiple values of the same key as list.
   *
   */
  public Map<String, Entity> captureFlattened() {
    return capture(true);
  }

  private Map<String, Entity> capture(boolean flattened ) {
    if (match == null) {
      return Collections.emptyMap();
    }

    if (!capture.isEmpty()) {
      return capture;
    }

    capture = Maps.newHashMap();

    this.grok.namedGroups.forEach(groupName -> {
      int start = match.start(groupName);
      int end = match.end(groupName);
      if (start < 0 || (containsIgnore && "ignore".equals(groupName))) {
        return;
      }
      String id = this.grok.getNamedRegexCollectionById(groupName);
      if (id != null && !id.isEmpty()) {
        groupName = id;
      }

      if ("UNWANTED".equals(groupName)) {
        return;
      }

      var converter = grok.converters.get(groupName);
      if (converter != null) {
        groupName = Converter.extractKey(groupName);
      }

      Entity entity = new Entity(subject, start, end,
          containsIgnore ? match.start("ignore") : -1,
          containsIgnore ? match.end("ignore") : -1,
          converter);

      Entity currentValue = capture.get(groupName);

      if (currentValue != null) {
        if (flattened) {
          throw new RuntimeException(
              format("key '%s' has multiple non-null values, this is not allowed in flattened mode, values:'%s', '%s'",
                  groupName,
                  currentValue,
                  entity));
        } else {
          currentValue.addEntity(entity);
        }
      } else {
        capture.put(groupName, entity);
      }
    });

    // if DATETIME_DELTA field exists, update the value of DATETIME field, and remove the DATETIME_DELTA field.
    grok.getFieldNameOf(Converter.Type.DATETIME).ifPresent(timestampField -> {
      grok.getFieldNameOf(Converter.Type.DATETIME_DELTA).ifPresent(deltaField -> {
        Duration delta = (Duration) capture.remove(deltaField).getValue();
        Entity timestamp = capture.get(timestampField);
        var instant = (Instant) timestamp.getValue();
        capture.put(timestampField, timestamp.withCustomValue(instant.plus(delta)));
      });
    });

    capture = Collections.unmodifiableMap(capture);

    return capture;
  }

  /**
   * Util fct.
   *
   * @return boolean
   */
  public Boolean isNull() {
    return this.match == null;
  }
}
