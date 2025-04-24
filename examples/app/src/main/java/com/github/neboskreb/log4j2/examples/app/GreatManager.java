package com.github.neboskreb.log4j2.examples.app;

import com.github.neboskreb.log4j2.examples.lib.AwesomeWorker;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
public class GreatManager {
    // Internally, the worker prints messages to the log.
    // You can control it in your log4j2.properties and see the result here.
    private final AwesomeWorker worker;

    public void onLogFatalMessageClicked() {
        worker.doGreatJob("Lorem", "Ipsum");

        Error throwable = new Error("WTF (What a Terrible Failure!)");
        log.fatal("This is FATAL level message", throwable);
    }

    public void onLogWarnMessageClicked() {
        worker.doGreatJob("Lorem", "Ipsum");

        Exception exception = new Exception("Some error which happened elsewhere");
        log.warn("This is WARN level message with suppressed exception", exception);
    }

    public void onLogDebugMessageClicked() {
        worker.doNotSoGreatJob();

        log.debug("This is DEBUG level message");
    }
}
