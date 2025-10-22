package com.github.neboskreb.log4j2.examples.lib

import com.github.neboskreb.log4j2.android.AndroidLog4jExtension
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(AndroidLog4jExtension::class)
class AwesomeWorkerAndroidTest {
    @Test
    fun doGreatJob() {
        // GIVEN
        val AwesomeWorker = AwesomeWorker()

        // WHEN
        val result = AwesomeWorker.doGreatJob("foo", "bar")

        // THEN
        Assertions.assertEquals("foobar", result)
    }

    @Test
    fun doNotSoGreatJob() {
        // GIVEN
        val AwesomeWorker = AwesomeWorker()

        // WHEN
        AwesomeWorker.doNotSoGreatJob()

        // THEN no exceptions thrown
    }

    @Test
    fun staticLogging() {
        // WHEN
        logSomething()

        // THEN see logs
    }
}
