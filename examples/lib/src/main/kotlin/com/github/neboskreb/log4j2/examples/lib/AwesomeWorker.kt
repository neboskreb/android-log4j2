package com.github.neboskreb.log4j2.examples.lib

import org.slf4j.ext.XLoggerFactory

private val log = XLoggerFactory.getXLogger(AwesomeWorker::class.java)

fun logSomething() {
    log.warn("A test warning from static context")
}

class AwesomeWorker {
    fun doGreatJob(param1: String, param2: String): String {
        log.entry(param1, param2)

        val result = param1 + param2

        return log.exit(result)
    }

    fun doNotSoGreatJob() {
        try {
            epicFail()
        } catch (e: Exception) {
            log.warn("Arhhhh, the job would be great if not this epic failure...", e)
        }
    }

    @Throws(Exception::class)
    fun epicFail() {
        throw log.throwing(Exception("Boo!"))
    }
}
