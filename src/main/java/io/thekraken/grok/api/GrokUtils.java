package io.thekraken.grok.api;

import org.joni.Matcher;
import org.joni.NameEntry;
import org.joni.Regex;
import org.joni.Region;

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
  
  public static Map<String, Integer> getNameGroups(String regex) {
    Map<String, Integer> namedGroups = new LinkedHashMap<>();
    Regex compiled = new Regex(regex);
    if (compiled.numberOfNames() == 0) {
        return Collections.emptyMap();
    }

    Iterator<NameEntry> it = compiled.namedBackrefIterator();
    while (it.hasNext()) {
      NameEntry ne = it.next();
      namedGroups.put(new String(ne.name, ne.nameP, ne.nameEnd - ne.nameP, StandardCharsets.UTF_8), ne.getBackRefs()[0]);
    }
    return namedGroups;
  }

  public static Map<String, String> namedGroups(byte[] bytes, Matcher matcher, Map<String, Integer> groupNames) {
      Map<String, String> namedGroups = new LinkedHashMap<String, String>();

      Region region = matcher.getEagerRegion();
      groupNames.forEach((name, number) -> {
          int begin = region.beg[number];
          int end = region.end[number];
          if (begin >= 0 && end > begin) {
              String groupValue = new String(bytes, begin, end - begin, StandardCharsets.UTF_8);
              namedGroups.put(name, groupValue);
          }
      });

    return namedGroups;
  }
}
