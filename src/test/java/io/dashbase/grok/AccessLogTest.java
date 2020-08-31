package io.dashbase.grok;

import io.thekraken.grok.api.exception.GrokException;
import org.junit.Test;

import java.io.IOException;

public class AccessLogTest {

    @Test
    public void test001_httpd_access() throws GrokException, IOException {
        var parser = new AccessLogParser();
        String input = "98.210.116.51 \"-\" \"-\" [25/Feb/2018:23:10:39 +0000] \"GET /assets/demo-77048f81b565db090ab2f906c9779b5a92629d996e3b77a6680a7136c492a956.png HTTP/1.1\" 304 \"-\"";

        var t = parser.parser(input);
        var e = t.get("message");
        System.out.println(input.substring(e.from, e.to));
    }

}
