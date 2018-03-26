package io.thekraken.grok.api;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import io.thekraken.grok.api.exception.GrokException;
import org.apache.commons.lang3.StringUtils;
import org.joni.Matcher;
import org.joni.Option;
import org.joni.Regex;
import org.joni.Region;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Map;

import static java.lang.String.format;

public class GrokCompiler {
  /**
   * {@code Grok} patterns definitions.
   */
  private final Map<String, String> grokPatternDefinitions = Maps.newHashMap();

  private GrokCompiler() {}

  public static GrokCompiler newInstance() {
    return new GrokCompiler();
  }

  public Map<String, String> getPatternDefinitions() {
    return grokPatternDefinitions;
  }

  /**
   * Registers a new pattern definition
   *
   * @param name : Pattern Name
   * @param pattern : Regular expression Or {@code Grok} pattern
   * @throws GrokException runtime expt
   **/
  public void register(String name, String pattern) {
    name = Preconditions.checkNotNull(name).trim();
    pattern = Preconditions.checkNotNull(pattern).trim();

    if (!name.isEmpty() && !pattern.isEmpty()) {
      grokPatternDefinitions.put(name, pattern);
    }
  }

  /**
   * Registers multiple pattern definitions
   */
  public void register(Map<String, String> patternDefinitions) {
    Preconditions.checkNotNull(patternDefinitions);
    patternDefinitions.forEach((name, pattern) -> register(name, pattern));
  }

  /**
   * Registers multiple pattern definitions from a given inputStream
   */
  public void register(InputStream input) throws IOException {
    // We don't want \n and commented line
    Regex pattern = new Regex("^([A-z0-9_]+)\\s+(.*)$");

    try (
        BufferedReader in =
            new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8))
    ) {
      in.lines().forEach(l -> {
        byte[] bytes = l.getBytes(StandardCharsets.UTF_8);
         Matcher matcher = pattern.matcher(bytes);
         if (matcher.match(0, bytes.length, Option.DEFAULT) != -1) {
           Region region = matcher.getEagerRegion();
           String name = new String(bytes, region.beg[1], region.end[1] - region.beg[1], StandardCharsets.UTF_8);
           String pat = new String(bytes, region.beg[2], region.end[2] - region.beg[2], StandardCharsets.UTF_8);
           register(name, pat);
         }
      });
    }
  }

  /**
   * Compiles a given Grok pattern and returns a Grok object which can parse the pattern.
   */
  public Grok compile(String pattern) {
    return compile(pattern, false);
  }

  public Grok compile(final String pattern, boolean namedOnly) {
    return compile(pattern, ZoneOffset.UTC, namedOnly);
  }

  /**
   * Compiles a given Grok pattern and returns a Grok object which can parse the pattern.
   *
   * @param pattern : Grok pattern (ex: %{IP})
   * @param defaultTimeZone: time zone used to parse a timestamp when it doesn't contain the time zone
   * @param namedOnly : Whether to capture named expressions only or not (i.e. %{IP:ip} but not ${IP})
   */
  public Grok compile(final String pattern, ZoneId defaultTimeZone, boolean namedOnly) {

    if (StringUtils.isBlank(pattern)) {
      throw new IllegalArgumentException("{pattern} should not be empty or null");
    }

    String namedRegex = pattern;
    int index = 0;
    /** flag for infinite recursion */
    int iterationLeft = 1000;
    Boolean continueIteration = true;
    Map<String, String> patternDefinitions = Maps.newHashMap(grokPatternDefinitions);

    // output
    Map<String, String> namedRegexCollection = Maps.newHashMap();

    // Replace %{foo} with the regex (mostly group name regex)
    // and then compile the regex
    while (continueIteration) {
      continueIteration = false;
      if (iterationLeft <= 0) {
        throw new IllegalArgumentException("Deep recursion pattern compilation of " + pattern);
      }
      iterationLeft--;

      Regex regex = new Regex(GrokUtils.GROK_PATTERN);
      byte[] bytes = namedRegex.getBytes(StandardCharsets.UTF_8);
      Matcher m = new Regex(GrokUtils.GROK_PATTERN).matcher(bytes);
      // Match %{Foo:bar} -> pattern name and subname
      // Match %{Foo=regex} -> add new regex definition
      if (m.search(0, bytes.length, Option.DEFAULT) != -1) {
        continueIteration = true;
        Map<String, String> group = GrokUtils.namedGroups(bytes, m, regex);
        if (group.get("definition") != null) {
          patternDefinitions.put(group.get("pattern"), group.get("definition"));
          group.put("name", group.get("name") + "=" + group.get("definition"));
        }
        int count = StringUtils.countMatches(namedRegex, "%{" + group.get("name") + "}");
        for (int i = 0; i < count; i++) {
          String definitionOfPattern = patternDefinitions.get(group.get("pattern"));
          if (definitionOfPattern == null) {
            throw new IllegalArgumentException(format("No definition for key '%s' found, aborting",
                group.get("pattern")));
          }
          String replacement = String.format("(?<name%d>%s)", index, definitionOfPattern);
          if (namedOnly && group.get("subname") == null) {
            replacement = String.format("(?:%s)", definitionOfPattern);
          }
          namedRegexCollection.put("name" + index,
              (group.get("subname") != null ? group.get("subname") : group.get("name")));
          namedRegex =
              StringUtils.replace(namedRegex, "%{" + group.get("name") + "}", replacement,1);
          // System.out.println(_expanded_pattern);
          index++;
        }
      }
    }

    if (namedRegex.isEmpty()) {
      throw new IllegalArgumentException("Pattern not found");
    }

    return new Grok(
        pattern,
        namedRegex,
        namedRegexCollection,
        patternDefinitions,
        defaultTimeZone
    );
  }
}
