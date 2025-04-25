package com.github.neboskreb.log4j2.examples.lib;

import net.loune.log4j2android.AndroidLog4jExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(AndroidLog4jExtension.class)
public class AwesomeWorkerAndroidTest {

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
