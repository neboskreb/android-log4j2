package com.github.neboskreb.log4j2.android;

import androidx.test.platform.app.InstrumentationRegistry;
import net.loune.log4j2android.AndroidLog4jHelper;
import org.junit.rules.ExternalResource;

/**
 * This rule applies the log configuration from file `assets/log4j2.properties` to your JUnit 4 test.
 * <p>
 * Usage:
 * <pre>{@code
 * @RunWith(AndroidJUnit4.class)
 * public class MyJUnit4Test {
 *     @ClassRule
 *     public static final AndroidLog4jRule rule = new AndroidLog4jRule();
 *
 *     @Test
 *     public void myTest() {
 *       ...
 *     }
 * }
 * }</pre>
 * <p>
 * NOTE: You do NOT need to apply this rule to silence the logging in your tests. The default log level in tests is "OFF".
 */
public class AndroidLog4jRule extends ExternalResource {
    private static boolean isInitialized;

    @Override
    protected void before() throws Throwable {
        if (!isInitialized) {
            isInitialized = true;
            AndroidLog4jHelper.initialize(InstrumentationRegistry.getInstrumentation());
        }
    }
}
