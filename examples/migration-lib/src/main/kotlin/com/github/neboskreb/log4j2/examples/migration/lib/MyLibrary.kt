package com.github.neboskreb.log4j2.examples.migration.lib

import android.util.Log

private const val TAG = "MY_LIBRARY"

class MyLibrary {
    fun logSomething() {
        Log.i(TAG, "Library logged this INFO message")

        Log.w(TAG, "Library logged this WARN message")

        Log.wtf(TAG, "Library logged this WTF message")
    }
}
