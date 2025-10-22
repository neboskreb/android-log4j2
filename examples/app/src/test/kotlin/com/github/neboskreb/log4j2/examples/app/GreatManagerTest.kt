package com.github.neboskreb.log4j2.examples.app

import com.github.neboskreb.log4j2.examples.lib.AwesomeWorker
import org.junit.jupiter.api.Test

class GreatManagerTest {
    @Test
    fun onLogFatalMessageClicked() {
        // GIVEN
        val awesomeWorker = AwesomeWorker()
        val manager = GreatManager(awesomeWorker)

        // WHEN
        manager.onLogFatalMessageClicked()

        // THEN see the logs
    }

    @Test
    fun onLogWarnMessageClicked() {
        // GIVEN
        val awesomeWorker = AwesomeWorker()
        val manager = GreatManager(awesomeWorker)

        // WHEN
        manager.onLogWarnMessageClicked()

        // THEN see the logs
    }

    @Test
    fun onLogDebugMessageClicked() {
        // GIVEN
        val awesomeWorker = AwesomeWorker()
        val manager = GreatManager(awesomeWorker)

        // WHEN
        manager.onLogDebugMessageClicked()

        // THEN see the logs
    }
}
