package com.github.neboskreb.log4j2.examples.app;

import androidx.test.platform.app.InstrumentationRegistry;
import com.github.neboskreb.log4j2.examples.lib.AwesomeWorker;
import net.loune.log4j2android.AndroidLog4jHelper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class GreatManagerAndroidTest {
    @BeforeAll
    public static void beforeAll() {
        AndroidLog4jHelper.initialize(InstrumentationRegistry.getInstrumentation());
    }


    @Test
    public void onLogFatalMessageClicked() {
        // GIVEN
        AwesomeWorker awesomeWorker = new AwesomeWorker();
        GreatManager manager = new GreatManager(awesomeWorker);

        // WHEN
        manager.onLogFatalMessageClicked();

        // THEN see the logs
    }

    @Test
    public void onLogWarnMessageClicked() {
        // GIVEN
        AwesomeWorker awesomeWorker = new AwesomeWorker();
        GreatManager manager = new GreatManager(awesomeWorker);

        // WHEN
        manager.onLogWarnMessageClicked();

        // THEN see the logs
    }

    @Test
    public void onLogDebugMessageClicked() {
        // GIVEN
        AwesomeWorker awesomeWorker = new AwesomeWorker();
        GreatManager manager = new GreatManager(awesomeWorker);

        // WHEN
        manager.onLogDebugMessageClicked();

        // THEN see the logs
    }
}
