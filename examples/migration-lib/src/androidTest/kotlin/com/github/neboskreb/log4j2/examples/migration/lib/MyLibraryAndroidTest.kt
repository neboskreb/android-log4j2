package com.github.neboskreb.log4j2.examples.migration.lib

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import com.github.neboskreb.log4j2.android.AndroidLog4jExtension

@ExtendWith(AndroidLog4jExtension::class)
class MyLibraryAndroidTest {
    @Test
    fun logSomething() {
        // GIVEN
        val library = MyLibrary()

        // WHEN
        library.logSomething()

        // THEN see the logs
    }
}
