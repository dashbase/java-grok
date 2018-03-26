package io.thekraken.grok.api;

import org.joni.NameEntry;
import org.joni.Regex;

import java.nio.charset.StandardCharsets;
import java.util.*;


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
  public static final String GROK_PATTERN =
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
      "\\}";
  
  public static Set<String> getNameGroups(String regex) {
    Set<String> namedGroups = new LinkedHashSet<>();
    Regex compiled = new Regex(regex);
    Iterator<NameEntry> it = compiled.namedBackrefIterator();
    while (it.hasNext()) {
      NameEntry ne = it.next();
      namedGroups.add(new String(ne.name, ne.nameP, ne.nameEnd - ne.nameP, StandardCharsets.UTF_8));
    }
    return namedGroups;
  }

  public static Map<String, String> namedGroups(Matcher matcher, Set<String> groupNames) {
    Map<String, String> namedGroups = new LinkedHashMap<String, String>();
    for (String groupName : groupNames) {
      String groupValue = matcher.group(groupName);
      namedGroups.put(groupName, groupValue);
    }
    return namedGroups;
  }
}
