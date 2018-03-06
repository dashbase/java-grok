package io.thekraken.grok.api;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * {@code GrokUtils} contain set of useful tools or methods.
 *
 * @author anthonycorbacho
 * @since 0.0.6
 */
public class GrokUtils {

  /**
   * Extract Grok patter like %{FOO} to FOO, Also Grok pattern with semantic.
   */
  public static final Pattern GROK_PATTERN = Pattern.compile(
      "%\\{" +
      "(?<name>" +
        "(?<pattern>[A-z0-9]+)" +
          "(?::(?<subname>[A-z0-9_:;\\/\\s\\.]+))?" +
          ")" +
          "(?:=(?<definition>" +
            "(?:" +
            "(?:[^{}]+|\\.+)+" +
            ")+" +
            ")" +
      ")?" +
      "\\}");
  
  public static final Pattern NAMED_REGEX = Pattern
      .compile("\\(\\?<([a-zA-Z][a-zA-Z0-9]*)>");

  public static Set<String> getNameGroups(String regex) {
    Set<String> namedGroups = new LinkedHashSet<String>();
    Matcher m = NAMED_REGEX.matcher(regex);
    while (m.find()) {
      namedGroups.add(m.group(1));
    }
    return namedGroups;
  }

  private static ConcurrentHashMap<String, Set<String>> cachedGroupNames = new ConcurrentHashMap<>();
  private static volatile String lastRegex = null;
  private static volatile Set<String> lastGroupNames = null;

  private static Set<String> parseGroupNames(String regex) {
    Set<String> groupNames = null;
    if (regex.equals(lastRegex)) {
      groupNames = lastGroupNames;
    } else {
      groupNames = cachedGroupNames.get(regex);
      if (groupNames == null) {
        groupNames = getNameGroups(regex);
        cachedGroupNames.put(regex, groupNames);
      }
      lastRegex = regex;
      lastGroupNames = groupNames;
    }
    return groupNames;
  }

  private static ConcurrentHashMap<String, Map<String, String>> cachedLocalGroupNames = new ConcurrentHashMap<>();

  public static Map<String, String> namedGroups(Matcher matcher,
      String namedRegex) {
    String regex = matcher.pattern().pattern();
    Set<String> groupNames = parseGroupNames(regex);
    Map<String, String> namedGroups = cachedLocalGroupNames.get(namedRegex);
    if (namedGroups == null) {
      Matcher localMatcher = matcher.pattern().matcher(namedRegex);
      namedGroups = new LinkedHashMap<String, String>();
      if (localMatcher.find()) {
        for (String groupName : groupNames) {
          String groupValue = localMatcher.group(groupName);
          namedGroups.put(groupName, groupValue);
        }
      }
      cachedLocalGroupNames.put(namedRegex, namedGroups);
    }
    return namedGroups;
  }
}
