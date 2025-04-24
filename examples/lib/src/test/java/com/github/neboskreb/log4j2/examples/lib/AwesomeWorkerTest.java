package com.github.neboskreb.log4j2.examples.lib;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AwesomeWorkerTest {
    @Test
    void doGreatJob() {
        // GIVEN
        AwesomeWorker awesomeClass = new AwesomeWorker();

        // WHEN
        String result = awesomeClass.doGreatJob("foo", "bar");

        // THEN
        assertEquals("foobar", result);
    }

    @Test
    void doNotSoGreatJob() {
        // GIVEN
        AwesomeWorker awesomeClass = new AwesomeWorker();

        // WHEN
        awesomeClass.doNotSoGreatJob();

        // THEN no exceptions thrown
    }
}
