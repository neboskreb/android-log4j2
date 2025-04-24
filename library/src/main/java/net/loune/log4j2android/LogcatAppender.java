package net.loune.log4j2android;

import android.util.Log;

import java.text.MessageFormat;
import java.util.Optional;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.ConfigurationException;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.PatternLayout;

import java.io.Serializable;
import org.apache.logging.log4j.core.Filter;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Objects.requireNonNullElseGet;

/**
 * Created by loune on 16/05/2015.
 */
@Plugin(name = "Logcat", category = "Core", elementType = "appender", printObject = true)
public final class LogcatAppender extends AbstractAppender {
    private static final String ATTRIBUTE_STACK_TRACE_RENDERING = "stack-trace-rendering";
    private static final String OPTION_LOGCAT = "logcat";
    private static final String OPTION_LOG4J = "log4j";

    @SuppressWarnings("unused") // Invoked by Log4J reflectively
    @PluginFactory
    public static LogcatAppender createAppender(@PluginAttribute("name") String name,
                                              @PluginAttribute("ignoreExceptions") boolean ignoreExceptions,
                                              @PluginElement("Layout") Layout<?> layout,
                                              @PluginElement("Filters") Filter filter,
                                              @PluginElement("Properties") Property[] properties,
                                              @PluginAttribute(value = ATTRIBUTE_STACK_TRACE_RENDERING, defaultString = OPTION_LOGCAT) String stackTraceRendering
    ) {
        name = requireNonNullElseGet(name, () -> { throw new ConfigurationException("No name provided for LogcatAppender"); });
        layout = requireNonNullElseGet(layout, PatternLayout::createDefaultLayout);
        boolean useNative = shouldUseNativeStackTraceRendering(stackTraceRendering);

        if (!useNative) {
            boolean success = WorkaroundSecurityManagerNPE.applyIfNeeded();
            if (!success) {
                // Force rendering the stack trace by Logcat
                useNative = true;
            }
        }

        return new LogcatAppender(name, filter, layout, ignoreExceptions, properties, useNative);
    }

    private static boolean shouldUseNativeStackTraceRendering(String stackTraceRendering) {
        switch (stackTraceRendering) {
            case OPTION_LOGCAT: return true;
            case OPTION_LOG4J:  return false;
            default: throw new ConfigurationException(MessageFormat.format("Invalid configuration for LogcatAppender: attribute {0}='{'{1}|{2}'}' was ''{3}''",
                        ATTRIBUTE_STACK_TRACE_RENDERING, OPTION_LOGCAT, OPTION_LOG4J, stackTraceRendering));
        }
    }


    private final boolean useNativeStackTraceRendering;

    private LogcatAppender(final String name,
                           final Filter filter,
                           final Layout<? extends Serializable> layout,
                           final boolean ignoreExceptions,
                           final Property[] properties,
                           boolean useNativeStackTraceRendering)
    {
        super(name, filter, layout, ignoreExceptions, properties);
        this.useNativeStackTraceRendering = useNativeStackTraceRendering;
    }

    @Override
    public void append(LogEvent event) {
        Optional<Throwable> withException = Optional.ofNullable(event.getThrown());
        if (withException.isPresent()) {
            if (useNativeStackTraceRendering) {
                event = new HideThrown(event);
            } else {
                withException = Optional.empty();
            }
        }

        String message = new String(getLayout().toByteArray(event), UTF_8);
        String tag = (event.getMarker() != null) ? event.getMarker().getName() : event.getLoggerName();
        String level = event.getLevel().name();

        if (withException.isPresent()) {
            logcatWithException(level, tag, message, withException.get());
        } else {
            logcat(level, tag, message);
        }
    }

    private static void logcatWithException(String level, String tag, String message, Throwable exception) {
        switch (level) {
            case "FATAL": Log.wtf(tag, message, exception); break;
            case "ERROR": Log.e(tag, message, exception);   break;
            case "WARN":  Log.w(tag, message, exception);   break;
            case "INFO":  Log.i(tag, message, exception);   break;
            case "DEBUG": Log.d(tag, message, exception);   break;
            case "TRACE": Log.v(tag, message, exception);   break;
            default: Log.v(tag, message, exception);break;
        }
    }

    private static void logcat(String level, String tag, String message) {
        switch (level) {
            case "FATAL": Log.wtf(tag, message); break;
            case "ERROR": Log.e(tag, message);   break;
            case "WARN":  Log.w(tag, message);   break;
            case "INFO":  Log.i(tag, message);   break;
            case "DEBUG": Log.d(tag, message);   break;
            case "TRACE": Log.v(tag, message);   break;
            default: Log.v(tag, message);  break;
        }
    }
}
