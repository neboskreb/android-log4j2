package com.github.neboskreb.log4j2.examples.app

import android.app.Application
import net.loune.log4j2android.AndroidLog4jHelper

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        AndroidLog4jHelper.initialize(applicationContext)
    }
}
