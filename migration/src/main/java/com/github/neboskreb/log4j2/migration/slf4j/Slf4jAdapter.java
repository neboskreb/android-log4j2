package com.github.neboskreb.log4j2.migration.slf4j;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.github.neboskreb.log4j2.migration.ILogAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.slf4j.spi.LoggingEventBuilder;

public class Slf4jAdapter implements ILogAdapter {
    @Override
    public boolean isLoggable(ANDROID_LEVEL androidLevel, @NonNull String tag) {
        return getLogger(tag).isEnabledForLevel(toSlfLevel(androidLevel));
    }

    @Override
    public int logIfLoggable(ANDROID_LEVEL androidLevel, @NonNull String tag, @Nullable String msg, @Nullable Throwable tr) {
        if (msg == null && tr == null) {
            return 0;
        }

        Level level = toSlfLevel(androidLevel);
        Logger logger = getLogger(tag);
        if (!logger.isEnabledForLevel(level)) {
            return 0;
        }

        LoggingEventBuilder builder = logger.atLevel(level);
        if (tr != null) {
            builder.setCause(tr);
        }
        if (msg != null) {
            builder.setMessage(msg);
        }
        builder.log();

        return 1;
    }

    public Level toSlfLevel(ANDROID_LEVEL androidLevel) {
        return switch (androidLevel) {
            case VERBOSE -> Level.TRACE;
            case DEBUG   -> Level.DEBUG;
            case INFO    -> Level.INFO;
            case WARN    -> Level.WARN;
            case ERROR   -> Level.ERROR;
            case WTF     -> Level.ERROR;
        };
    }

    synchronized public Logger getLogger(@NonNull String tag) {
        return LoggerFactory.getLogger(tag);
    }
}
