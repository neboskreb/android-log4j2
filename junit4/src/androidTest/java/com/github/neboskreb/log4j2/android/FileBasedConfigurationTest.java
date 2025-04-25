package com.github.neboskreb.log4j2.android;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


@RunWith(AndroidJUnit4.class)
public class FileBasedConfigurationTest {
    @ClassRule
    public static final AndroidLog4jRule rule = new AndroidLog4jRule();

    private final ByteArrayOutputStream outputCaptor = new ByteArrayOutputStream();

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outputCaptor));
        System.setErr(new PrintStream(outputCaptor));
    }

    @Test
    public void useLog() {
        // GIVEN the configuration in assets/log4j2.properties
        Logger log = LogManager.getLogger("Logger");

        // WHEN
        log.trace("trace {}", "hidden");
        log.warn("warn {}", "shown");

        // THEN
        String output = outputCaptor.toString();
        assertNotNull(output);
        assertTrue(output.contains("warn shown"));
        assertFalse(output.contains("trace hidden"));
    }
}
