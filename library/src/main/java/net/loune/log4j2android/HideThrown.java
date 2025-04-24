package net.loune.log4j2android;

import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.impl.ThrowableProxy;

@RequiredArgsConstructor
public class HideThrown implements LogEvent {
    @Delegate
    private final LogEvent original;

    @Override
    public Throwable getThrown() {
        return null;
    }

    @Override
    public ThrowableProxy getThrownProxy() {
        return null;
    }
}
