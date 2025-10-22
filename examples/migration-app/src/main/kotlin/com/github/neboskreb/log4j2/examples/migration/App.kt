package com.github.neboskreb.log4j2.examples.migration

import android.app.Application
import com.github.neboskreb.log4j2.migration.Log
import net.loune.log4j2android.AndroidLog4jHelper

class App : Application() {
    // NOTE: This is optional.
    // Force Log to invoke the Log4j calls directly, bypassing the SLF4J facade.
    init {
        Log.useLog4j2()
    }

    override fun onCreate() {
        super.onCreate()

        AndroidLog4jHelper.initialize(applicationContext)
    }
}
