package com.github.neboskreb.log4j2.migration.log4j2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.github.neboskreb.log4j2.migration.ILogAdapter;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log4j2Adapter implements ILogAdapter {
    @Override
    public boolean isLoggable(ANDROID_LEVEL androidLevel, @NonNull String tag) {
        return getLogger(tag).isEnabled(toLog4jLevel(androidLevel));
    }

    @Override
    public int logIfLoggable(ANDROID_LEVEL androidLevel, @NonNull String tag, @Nullable String msg, @Nullable Throwable tr) {
        if (msg == null && tr == null) {
            return 0;
        }

        Level level = toLog4jLevel(androidLevel);
        Logger logger = getLogger(tag);
        if (!logger.isEnabled(level)) {
            return 0;
        }

        LogBuilder builder = logger.atLevel(level);
        if (tr != null) {
            builder.withThrowable(tr);
        }
        if (msg != null) {
            builder.log(msg);
        } else {
            builder.log();
        }

        return 1;
    }

    public Level toLog4jLevel(ANDROID_LEVEL androidLevel) {
        return switch (androidLevel) {
            case VERBOSE -> Level.TRACE;
            case DEBUG   -> Level.DEBUG;
            case INFO    -> Level.INFO;
            case WARN    -> Level.WARN;
            case ERROR   -> Level.ERROR;
            case WTF     -> Level.FATAL;
        };
    }

    public Logger getLogger(@NonNull String tag) {
        // No `synchronized` needed here as Log4j2 logger registry is thread-safe
        return LogManager.getLogger(tag);
    }
}
