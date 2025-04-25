package com.example.extension;

import com.github.neboskreb.log4j2.android.AndroidLog4jExtension;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(AndroidLog4jExtension.class)
class FileBasedConfigurationTest {

    private final ByteArrayOutputStream outputCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputCaptor));
        System.setErr(new PrintStream(outputCaptor));
    }

    @Test
    void useLog() {
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
