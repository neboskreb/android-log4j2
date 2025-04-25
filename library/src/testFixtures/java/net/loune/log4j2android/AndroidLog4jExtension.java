package net.loune.log4j2android;

import androidx.test.platform.app.InstrumentationRegistry;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

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
