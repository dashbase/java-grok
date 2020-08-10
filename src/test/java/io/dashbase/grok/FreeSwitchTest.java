package io.dashbase.grok;

import io.thekraken.grok.api.exception.GrokException;
import org.junit.Test;

import java.io.IOException;

public class FreeSwitchTest {

    @Test
    public void test001_httpd_access() throws GrokException, IOException {
        var parser = new FreeSwitchParser();
        var t = parser.parser("d9091bf9-accd-49b8-ac75-8fa7a86d3cf4 2020-08-10 16:01:57.055298 [NOTICE] switch_channel.c:1118 New Channel sofia/internal/1018@18.191.136.77 [d9091bf9-accd-49b8-ac75-8fa7a86d3cf4]");
        System.out.println(t.uuid);
        System.out.println(t.time);
        System.out.println(t.level);
        System.out.println(t.source_line);
        System.out.println(t.source_file);
        System.out.println(t.message);
    }

}
