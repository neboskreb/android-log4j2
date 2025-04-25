package com.github.neboskreb.log4j2.android;

import androidx.test.platform.app.InstrumentationRegistry;
import net.loune.log4j2android.AndroidLog4jHelper;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

/**
 * This extension applies the log configuration from file `assets/log4j2.properties` to your JUnit 5 test.
 * <p>
 * Usage:
 * <pre>{@code
 * @ExtendWith(AndroidLog4jExtension.class)
 * class MyJunit5Test {
 *
 *     @Test
 *     void myTest() {
 *       ...
 *     }
 * }
 * }</pre>
 * <p>
 * NOTE: You do NOT need to apply this extension to silence the logging in your tests. The default log level in tests is "OFF".
 */
public class AndroidLog4jExtension implements BeforeAllCallback {
    private static boolean isInitialized;

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        if (!isInitialized) {
            isInitialized = true;
            AndroidLog4jHelper.initialize(InstrumentationRegistry.getInstrumentation());
        }
    }
}
