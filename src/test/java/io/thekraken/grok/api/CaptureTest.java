package io.thekraken.grok.api;

import com.google.common.io.Resources;
import io.thekraken.grok.api.exception.GrokException;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.Map;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CaptureTest {

    GrokCompiler compiler;

    @Before
    public void setUp() throws Exception {
        compiler = GrokCompiler.newInstance();
        compiler.register(Resources.getResource(ResourceManager.PATTERNS).openStream());
    }

    @Test
    public void test001_captureMathod() throws Exception {
        compiler.register("foo", ".*");
        Grok grok = compiler.compile("%{foo}");
        Match m = grok.match("Hello World");
        assertEquals("(?<name0>.*)", grok.getNamedRegex());
        assertEquals("Hello World", m.getSubject());
        Map<String, Entity> map = m.capture();
        assertEquals(1, map.size());
        assertEquals("{foo=Hello World[0,11]}", map.toString());
    }

    @Test
    public void test002_captureMathodMulti() throws GrokException {
        compiler.register("foo", ".*");
        compiler.register("bar", ".*");
        Grok grok = compiler.compile("%{foo} %{bar}");
        Match m = grok.match("Hello World");
        assertEquals("(?<name0>.*) (?<name1>.*)", grok.getNamedRegex());
        assertEquals("Hello World", m.getSubject());
        Map<String, Entity> map = m.capture();
        assertEquals(2, map.size());
        assertEquals("{bar=World[6,11], foo=Hello[0,5]}", map.toString());
    }

    @Test
    public void test003_captureMathodNasted() throws GrokException {
        compiler.register("foo", "\\w+ %{bar}");
        compiler.register("bar", "\\w+");
        Grok grok = compiler.compile("%{foo}");
        Match m = grok.match("Hello World");
        assertEquals("(?<name0>\\w+ (?<name1>\\w+))", grok.getNamedRegex());
        assertEquals("Hello World", m.getSubject());
        Map<String, Entity> map = m.capture();
        assertEquals(2, map.size());
        assertEquals("{bar=World[6,11], foo=Hello World[0,11]}", map.toString());
    }

    @Test
    public void test004_captureNastedRecustion() throws GrokException {
        compiler.register("foo", "%{foo}");
        boolean thrown = false;
        /** Must raise `Deep recursion pattern` execption */
        try {
            compiler.compile("%{foo}");
        } catch (Exception e) {
            thrown = true;
        }
        assertTrue(thrown);
    }

    @Test
    public void test005_captureSubName() throws GrokException {
        String name = "foo";
        String subname = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_abcdef";
        compiler.register(name, "\\w+");
        Grok grok = compiler.compile("%{" + name + ":" + subname + "}");
        Match m = grok.match("Hello");
        Map<String, Entity> map = m.capture();
        assertEquals(1, map.size());
        assertEquals("Hello[0,5]", map.get(subname).toString());
        assertEquals("{abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_abcdef=Hello[0,5]}", map.toString());
    }

    @Test
    public void test006_captureOnlyNamed() throws GrokException {
        compiler.register("abcdef", "[a-zA-Z]+");
        compiler.register("ghijk", "\\d+");
        Grok grok = compiler.compile("%{abcdef:abcdef}%{ghijk}", true);
        Match m = grok.match("abcdef12345");
        Map<String, Entity> map = m.capture();
        assertEquals(map.size(), 1);
        assertNull(map.get("ghijk"));
        assertEquals(map.get("abcdef").toString(), "abcdef[0,6]");
    }
    
    @SuppressWarnings("unchecked")
	@Test
    public void test007_captureDuplicateName() throws GrokException {
    	Grok grok = compiler.compile("%{INT:id} %{INT:id}");
    	Match m = grok.match("123 456");
        Map<String, Entity> map = m.capture();
    	assertEquals(map.size(), 1);
    	assertEquals("123[0,3]", map.get("id").toString());
    	assertEquals(1, map.get("id").additionalEntities.size());
    	assertEquals("456[4,7]", map.get("id").additionalEntities.get(0).toString());
    }

    @SuppressWarnings("unchecked")
	@Test
    public void test008_flattenDuplicateKeys() throws GrokException {
        Grok grok = compiler.compile("(?:foo %{INT:id} bar|bar %{INT:id} foo)");
        Match m = grok.match("foo 123 bar");
        Map<String, Entity> map = m.captureFlattened();
        assertEquals(map.size(), 1);
        assertEquals(map.get("id").toString(), "123[4,7]");
        Match m2 = grok.match("bar 123 foo");
        map = m2.captureFlattened();
        assertEquals(map.size(), 1);
        assertEquals(map.get("id").toString(), "123[4,7]");

        grok = compiler.compile("%{INT:id} %{INT:id}");
        Match m3 = grok.match("123 456");

        try {
            m3.captureFlattened();
            fail("should report error due tu ambiguity");
        } catch (RuntimeException e) {
            assertThat(e.getMessage(), containsString("has multiple non-null values, this is not allowed in flattened mode"));
        }
    }
}
