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

  public static Map<String, String> namedGroups(byte[] bytes, Matcher matcher, Regex regex) {
    Map<String, String> namedGroups = new LinkedHashMap<String, String>();

      Region region = matcher.getEagerRegion();
      for (Iterator<NameEntry> entry = regex.namedBackrefIterator(); entry.hasNext();) {
          NameEntry e = entry.next();
          int number = e.getBackRefs()[0];
          int begin = region.beg[number];
          int end = region.end[number];

          if (begin >= 0 && end > begin) {
              String groupName = new String(e.name, e.nameP, e.nameEnd - e.nameP, StandardCharsets.UTF_8);
              String groupValue = new String(bytes, begin, end - begin, StandardCharsets.UTF_8);
              namedGroups.put(groupName, groupValue);
          }
      }

    return namedGroups;
  }
}
