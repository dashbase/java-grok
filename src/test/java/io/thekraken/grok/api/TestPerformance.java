package io.thekraken.grok.api;

import com.google.common.io.Resources;
import io.dashbase.grok.AccessLogParser;

import java.util.Map;

public class TestPerformance {
    public static void main(String[] args) throws Exception {
        String input = "98.210.116.51 \"-\" \"-\" [25/Feb/2018:23:10:39 +0000] \"GET /assets/demo-77048f81b565db090ab2f906c9779b5a92629d996e3b77a6680a7136c492a956.png HTTP/1.1\" 304 \"-\"";

        GrokCompiler compiler = GrokCompiler.newInstance();
        compiler.register(Resources.getResource(ResourceManager.PATTERNS).openStream());

        String pattern = "%{IPORHOST:clientip} \"%{USER:ident}\" \"%{USER:auth}\" \\[%{HTTPDATE:timestamp:datetime:dd/MMM/yyyy:HH:mm:ss Z}\\] \"(?:%{WORD:verb} %{NOTSPACE:request}(?: HTTP/%{NUMBER:httpversion})?|%{DATA:rawrequest})\" %{NUMBER:response:int} \"(?:%{NUMBER:bytes}|-)\"";
        Grok grok = compiler.compile(pattern);
        for (int i = 0; i < 1000000; i++) {
            Match match = grok.match(input);
            Map<String, Entity> map = match.capture();
        }

        long start = System.currentTimeMillis();
        for (int i = 0; i < 5000000; i++) {
            Match match = grok.match(input);
            Map<String, Entity> map = match.capture();
        }
        long end = System.currentTimeMillis();
        System.out.println("grok took: " + (end - start) + " millis");


        var parser = new AccessLogParser();
        for (int i = 0; i < 1000000; i++) {
            var map = parser.parser(input);
        }
        start = System.currentTimeMillis();
        for (int i = 0; i < 5000000; i++) {
            var map = parser.parser(input);
        }
        end = System.currentTimeMillis();
        System.out.println("ragel took: " + (end - start) + " millis");
    }
}
