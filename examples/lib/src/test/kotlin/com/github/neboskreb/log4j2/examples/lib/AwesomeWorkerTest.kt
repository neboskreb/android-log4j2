package com.github.neboskreb.log4j2.examples.lib

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class AwesomeWorkerTest {
    @Test
    fun doGreatJob() {
        // GIVEN
        val awesomeClass = AwesomeWorker()

        // WHEN
        val result = awesomeClass.doGreatJob("foo", "bar")

        // THEN
        Assertions.assertEquals("foobar", result)
    }

    @Test
    fun doNotSoGreatJob() {
        // GIVEN
        val awesomeClass = AwesomeWorker()

        // WHEN
        awesomeClass.doNotSoGreatJob()

        // THEN no exceptions thrown
    }

    @Test
    fun staticLogging() {
        // WHEN
        logSomething()

        // THEN see logs
    }
}
