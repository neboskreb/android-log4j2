package com.github.neboskreb.log4j2.examples.app

import com.github.neboskreb.log4j2.examples.lib.AwesomeWorker
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

private val log: Logger = LogManager.getLogger(GreatManager::class.java)

/**
 * Assume, this Manager uses a Worker to do stuff, and the worker prints messages to the log.
 *
 * Though you can't change the Worker's source from here, you can perfectly well control what it logs
 * from your log4j2.properties, and see the result here.
 */
class GreatManager(
    private val worker: AwesomeWorker
) {
    fun onLogFatalMessageClicked() {
        worker.doGreatJob("Lorem", "Ipsum")

        val throwable = Error("WTF (What a Terrible Failure!)")
        log.fatal("This is FATAL level message", throwable)
    }

    fun onLogWarnMessageClicked() {
        worker.doGreatJob("Lorem", "Ipsum")

        val exception = Exception("Some error which happened elsewhere")
        log.warn("This is WARN level message with suppressed exception", exception)
    }

    fun onLogDebugMessageClicked() {
        worker.doNotSoGreatJob()

        log.debug("This is DEBUG level message")
    }
}
