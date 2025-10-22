package com.github.neboskreb.log4j2.examples.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.github.neboskreb.log4j2.examples.app.databinding.ActivityMainBinding
import com.github.neboskreb.log4j2.examples.lib.AwesomeWorker
import com.github.neboskreb.log4j2.examples.lib.logSomething
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

private val log: Logger = LogManager.getLogger(MainActivity::class.java)

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        logSomething()

        val databinding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        databinding.setController(GreatManager(AwesomeWorker()))

        log.info("Activity created")
    }
}
