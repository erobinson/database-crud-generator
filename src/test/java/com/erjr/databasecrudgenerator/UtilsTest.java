package com.erjr.databasecrudgenerator;

import static org.junit.Assert.*;
import org.junit.Test;
import static com.erjr.databasecrudgenerator.Utils.*;

public class UtilsTest {

    @Test
    public void camelCaseStringTest() {
        assertEquals(null, camelCaseString(null));
        assertEquals("test", camelCaseString("test"));
        assertEquals("testTest", camelCaseString("test_test"));
        assertEquals("testTest", camelCaseString("test-test"));
        assertEquals("testTest", camelCaseString("test test"));
        assertEquals("testTest", camelCaseString("test_tEst"));
    }
}
