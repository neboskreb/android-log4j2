package net.loune.log4j2android;

import android.app.Instrumentation;
import android.content.Context;

import androidx.annotation.NonNull;
import java.io.File;

import static net.loune.log4j2android.PluginProcessorHelper.injectPlugins;

/**
 * Created by loune on 9/04/2016.
 */
public class AndroidLog4jHelper {
    public static final String FILENAME = "log4j2.properties";

    private static ContextProxy savedContext;

    static {
        // Temporary init with a NO-OP configuration.
        // If the user wants, they will call initialize later; otherwise we will log nothing
        savedContext = new ContextProxy();

        System.setProperty("Log4jContextSelector", "net.loune.log4j2android.AndroidContextSelector");
// FIXME Remove this once 2.24.0 is released
// https://github.com/apache/logging-log4j2/issues/2774#issuecomment-2254418643
        System.setProperty("log4j2.disable.jmx", "true");

        injectPlugins("net.loune.log4j2android", AndroidLookup.class, LogcatAppender.class);
    }

    public static void initialize(Context context) {
        initialize(new ContextProxy(context));
    }

    public static void initialize(Instrumentation instrumentation) {
        Context appContext  = instrumentation.getTargetContext();
        Context testContext = instrumentation.getContext();
        // Test context goes first so it overrides the app context
        initialize(new ContextProxy(testContext, appContext));
    }

    private static void initialize(ContextProxy proxy) {
        savedContext = proxy;
    }

    public static File getFilesDir() {
        return savedContext.getFilesDir();
    }

    public static File getExternalFilesDir(String type) {
        return savedContext.getExternalFilesDir(type);
    }

    @NonNull
    public static AutoCloseConfigDescriptor getConfig() {
        return savedContext.getConfig(FILENAME);
    }


}
