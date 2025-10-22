package com.github.neboskreb.log4j2.examples.migration

import android.app.Activity
import android.os.Bundle
import com.github.neboskreb.log4j2.migration.Log

import com.github.neboskreb.log4j2.examples.migration.lib.MyLibrary

private const val TAG = "MAIN_ACTIVITY"

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.w(null, "This is a NULL-TAG message")
        Log.w(TAG, "This is a test warning message")

        super.onCreate(savedInstanceState)
        Log.i(TAG, "Activity created")

        setContentView(R.layout.content_main)
        Log.d(TAG, "ContentView is set")

        Log.wtf(TAG, "This is a test WTF message with a stack trace", Throwable("WTF Exception"))

        MyLibrary().logSomething()
    }
}
