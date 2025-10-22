package com.github.neboskreb.log4j2.examples.migration.lib

import org.junit.jupiter.api.Test

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
