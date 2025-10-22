package com.github.neboskreb.log4j2.migration;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface ILogAdapter {
    enum ANDROID_LEVEL {
        VERBOSE, DEBUG, INFO, WARN, ERROR, WTF
    }

    boolean isLoggable(ANDROID_LEVEL androidLevel, @NonNull String tag);
    int logIfLoggable(ANDROID_LEVEL androidLevel, @NonNull String tag, @Nullable String msg, @Nullable Throwable tr);
}
