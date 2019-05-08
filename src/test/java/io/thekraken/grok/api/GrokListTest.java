package io.thekraken.grok.api;

import com.google.common.io.Resources;
import io.thekraken.grok.api.exception.GrokException;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GrokListTest {
    GrokCompiler compiler;

    @Before
    public void setUp() throws Exception {
        compiler = GrokCompiler.newInstance();
        compiler.register(Resources.getResource(ResourceManager.PATTERNS).openStream());
    }

    @Test
    public void test_001() throws GrokException {
        List<String> logs = new ArrayList<String>();

        logs.add("178.21.82.201");
        logs.add("11.178.94.216");
        logs.add("238.222.236.81");
        logs.add("231.49.38.155");
        logs.add("206.0.116.17");
        logs.add("191.199.247.47");
        logs.add("43.131.249.156");
        logs.add("170.36.40.12");
        logs.add("124.2.84.36");


        Grok grok = compiler.compile("%{IP}");
        for (String log : logs) {
            var map = grok.match(log).capture();
            assertEquals(log, map.get("IP").getValue());
        }

    }

    @Test
    public void test_002() throws GrokException {
        List<String> logs = new ArrayList<String>();

        logs.add("178.21.82.201");
        logs.add("11.178.94.216");
        logs.add("231.49.38.155");
        logs.add("206.0.116.17");
        logs.add("191.199.247.47");
        logs.add("43.131.249.156");
        logs.add("170.36.40.12");
        logs.add("124.2.84.36");


        Grok grok = compiler.compile("%{IP}");
        for (String log : logs) {
            var map = grok.match(log).capture();
            assertEquals(log, map.get("IP").getValue());
        }

    }
}
