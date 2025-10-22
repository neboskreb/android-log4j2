package com.github.neboskreb.log4j2.examples.app

import com.github.neboskreb.log4j2.android.AndroidLog4jExtension
import com.github.neboskreb.log4j2.examples.lib.AwesomeWorker
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(AndroidLog4jExtension::class)
class GreatManagerAndroidTest {
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
