package com.github.neboskreb.log4j2.examples.app;

import com.github.neboskreb.log4j2.examples.lib.AwesomeWorker;
import org.junit.jupiter.api.Test;

public class GreatManagerTest {

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
