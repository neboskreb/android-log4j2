package com.github.neboskreb.log4j2.migration;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.github.neboskreb.log4j2.migration.ILogAdapter.ANDROID_LEVEL;
import com.github.neboskreb.log4j2.migration.log4j2.Log4j2Adapter;
import com.github.neboskreb.log4j2.migration.slf4j.Slf4jAdapter;

import static com.github.neboskreb.log4j2.migration.ILogAdapter.ANDROID_LEVEL.*;
import static java.util.Objects.requireNonNullElse;

/**
 * @deprecated DO NOT USE IN NEW CODE. <br>
 *             BEWARE OF THE PERFORMANCE HIT! <br>
 *             This class is intended as an emergency patch while migrating your legacy logging to Log4j.
 *             After having patched your code, migrate it to proper logging as soon as possible.
 */
@Deprecated
@SuppressWarnings("unused")
public final class Log {
    private static ILogAdapter adapter = new Slf4jAdapter();

    public static void useLog4j2() {
        adapter = new Log4j2Adapter();
    }


    private Log() {
    }

    /**
     * Send a {@link android.util.Log#VERBOSE VERBOSE} log message.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @return A positive value if the message was loggable (see {@link #isLoggable}).
     */
    public static int v(@Nullable String tag, @NonNull String msg) {
        return log(VERBOSE, tag, msg, null);
    }

    /**
     * Send a {@link android.util.Log#VERBOSE VERBOSE} log message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr An exception to log.
     * @return A positive value if the message was loggable (see {@link #isLoggable}).
     */
    public static int v(@Nullable String tag, @Nullable String msg, @Nullable Throwable tr) {
        return log(VERBOSE, tag, msg, tr);
    }

    /**
     * Send a {@link android.util.Log#DEBUG DEBUG} log message.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @return A positive value if the message was loggable (see {@link #isLoggable}).
     */
    public static int d(@Nullable String tag, @NonNull String msg) {
        return log(DEBUG, tag, msg, null);
    }

    /**
     * Send a {@link android.util.Log#DEBUG DEBUG} log message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr An exception to log.
     * @return A positive value if the message was loggable (see {@link #isLoggable}).
     */
    public static int d(@Nullable String tag, @Nullable String msg, @Nullable Throwable tr) {
        return log(DEBUG, tag, msg, tr);
    }

    /**
     * Send an {@link android.util.Log#INFO INFO} log message.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @return A positive value if the message was loggable (see {@link #isLoggable}).
     */
    public static int i(@Nullable String tag, @NonNull String msg) {
        return log(INFO, tag, msg, null);
    }

    /**
     * Send a {@link android.util.Log#INFO INFO} log message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr An exception to log.
     */
    public static int i(@Nullable String tag, @Nullable String msg, @Nullable Throwable tr) {
        return log(INFO, tag, msg, tr);
    }

    /**
     * Send a {@link android.util.Log#WARN WARN} log message.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @return A positive value if the message was loggable (see {@link #isLoggable}).
     */
    public static int w(@Nullable String tag, @NonNull String msg) {
        return log(WARN, tag, msg, null);
    }

    /**
     * Send a {@link android.util.Log#WARN WARN} log message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr An exception to log.
     * @return A positive value if the message was loggable (see {@link #isLoggable}).
     */
    public static int w(@Nullable String tag, @Nullable String msg, @Nullable Throwable tr) {
        return log(WARN, tag, msg, tr);
    }

    /**
     * Checks to see whether or not a log for the specified tag is loggable at the specified level.
     *
     *  The default level of any tag is set to INFO. This means that any level above and including
     *  INFO will be logged. Before you make any calls to a logging method you should check to see
     *  if your tag should be logged. You can change the default level by setting a system property:
     *      'setprop log.tag.&lt;YOUR_LOG_TAG> &lt;LEVEL>'
     *  Where level is either VERBOSE, DEBUG, INFO, WARN, ERROR, or ASSERT.
     *  You can also create a local.prop file that with the following in it:
     *      'log.tag.&lt;YOUR_LOG_TAG>=&lt;LEVEL>'
     *  and place that in /data/local.prop.
     *
     * @param tag The tag to check.
     * @param level The level to check.
     * @return Whether or not that this is allowed to be logged.
     * @throws IllegalArgumentException is thrown if the tag.length() > 23
     *         for Nougat (7.0) and prior releases (API <= 25), there is no
     *         tag limit of concern after this API level.
     */
    public static boolean isLoggable(@Nullable String tag, int level) {
        ANDROID_LEVEL androidLevel = toAndroidLevel(level);
        return adapter.isLoggable(androidLevel, nonNullTag(tag));
    }

    /**
     * Send a {@link android.util.Log#WARN WARN} log message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param tr An exception to log.
     * @return A positive value if the message was loggable (see {@link #isLoggable}).
     */
    public static int w(@Nullable String tag, @Nullable Throwable tr) {
        return log(WARN, tag, null, tr);
    }

    /**
     * Send an {@link android.util.Log#ERROR ERROR} log message.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @return A positive value if the message was loggable (see {@link #isLoggable}).
     */
    public static int e(@Nullable String tag, @NonNull String msg) {
        return log(ERROR, tag, msg, null);
    }

    /**
     * Send a {@link android.util.Log#ERROR ERROR} log message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr An exception to log.
     * @return A positive value if the message was loggable (see {@link #isLoggable}).
     */
    public static int e(@Nullable String tag, @Nullable String msg, @Nullable Throwable tr) {
        return log(ERROR, tag, msg, tr);
    }

    /**
     * What a Terrible Failure: Report a condition that should never happen.
     * The error will always be logged at level ASSERT with the call stack.
     * Depending on system configuration, a report may be added to the
     * {@link android.os.DropBoxManager} and/or the process may be terminated
     * immediately with an error dialog.
     * @param tag Used to identify the source of a log message.
     * @param msg The message you would like logged.
     * @return A positive value if the message was loggable (see {@link #isLoggable}).
     */
    public static int wtf(@Nullable String tag, @Nullable String msg) {
        return wtf(tag, msg, null);
    }

    /**
     * Like {@link #wtf(String, String)}, but also writes to the log the full
     * call stack.
     */
    public static int wtfStack(@Nullable String tag, @Nullable String msg) {
        return wtf(tag, msg, new Throwable("StackTrace"));
    }

    /**
     * What a Terrible Failure: Report an exception that should never happen.
     * Similar to {@link #wtf(String, String)}, with an exception to log.
     * @param tag Used to identify the source of a log message.
     * @param tr An exception to log.
     * @return A positive value if the message was loggable (see {@link #isLoggable}).
     */
    public static int wtf(@Nullable String tag, @NonNull Throwable tr) {
        return wtf(tag, null, tr);
    }

    /**
     * What a Terrible Failure: Report an exception that should never happen.
     * Similar to {@link #wtf(String, Throwable)}, with a message as well.
     * @param tag Used to identify the source of a log message.
     * @param msg The message you would like logged.
     * @param tr An exception to log.  May be null.
     * @return A positive value if the message was loggable (see {@link #isLoggable}).
     */
    public static int wtf(@Nullable String tag, @Nullable String msg, @Nullable Throwable tr) {
        return log(WTF, tag, msg, tr);
    }

    public static <TerribleFailureHandler> TerribleFailureHandler setWtfHandler(@NonNull TerribleFailureHandler handler) {
        throw new UnsupportedOperationException();
    }

    /**
     * Handy function to get a loggable stack trace from a Throwable

     * <p>If any of the throwables in the cause chain is an <code>UnknownHostException</code>,
     * this returns an empty string.
     * @param tr An exception to log.
     */
    @NonNull
    public static String getStackTraceString(@Nullable Throwable tr) {
        return android.util.Log.getStackTraceString(tr);
    }

    public static int println(int priority, @Nullable String tag, @NonNull String msg) {
        throw new UnsupportedOperationException();
    }

    public static int logToRadioBuffer(int priority, @Nullable String tag, @Nullable String message) {
        throw new UnsupportedOperationException();
    }

    public static int printlns(int bufID, int priority, @Nullable String tag, @NonNull String msg, @Nullable Throwable tr) {
        throw new UnsupportedOperationException();
    }




    private static int log(ANDROID_LEVEL androidLevel, @Nullable String tag, @Nullable String msg, @Nullable Throwable tr) {
        return adapter.logIfLoggable(androidLevel, nonNullTag(tag), msg, tr);
    }

    @NonNull
    private static String nonNullTag(@Nullable String tag) {
        return requireNonNullElse(tag, "TAG");
    }

    private static ANDROID_LEVEL toAndroidLevel(int level) {
        return switch (level) {
            case android.util.Log.VERBOSE -> VERBOSE;
            case android.util.Log.DEBUG   -> DEBUG;
            case android.util.Log.INFO    -> INFO;
            case android.util.Log.WARN    -> WARN;
            case android.util.Log.ERROR   -> ERROR;
            case android.util.Log.ASSERT  -> ERROR;
            default -> throw new IllegalArgumentException("Unknown log level: " + level);
        };
    }
}
