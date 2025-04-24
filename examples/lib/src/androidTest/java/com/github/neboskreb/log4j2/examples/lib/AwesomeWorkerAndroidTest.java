package com.github.neboskreb.log4j2.examples.lib;

import androidx.test.platform.app.InstrumentationRegistry;
import net.loune.log4j2android.AndroidLog4jHelper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AwesomeWorkerAndroidTest {
    @BeforeAll
    static void beforeAll() {
        AndroidLog4jHelper.initialize(InstrumentationRegistry.getInstrumentation());
    }


    @Test
    public void doGreatJob() {
        // GIVEN
        AwesomeWorker AwesomeWorker = new AwesomeWorker();

        // WHEN
        String result = AwesomeWorker.doGreatJob("foo", "bar");

        // THEN
        assertEquals("foobar", result);
    }

    @Test
    public void doNotSoGreatJob() {
        // GIVEN
        AwesomeWorker AwesomeWorker = new AwesomeWorker();

        // WHEN
        AwesomeWorker.doNotSoGreatJob();

        // THEN no exceptions thrown
    }
}
