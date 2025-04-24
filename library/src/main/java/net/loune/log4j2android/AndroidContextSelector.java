package net.loune.log4j2android;

import net.loune.log4j2android.AutoCloseConfigDescriptor.ConfigDescriptor;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.impl.ContextAnchor;
import org.apache.logging.log4j.core.selector.ContextSelector;

import java.net.URI;
import java.util.List;

/**
 * Created by loune on 16/05/2015.
 */
public class AndroidContextSelector implements ContextSelector {

    private static LoggerContext CONTEXT = new LoggerContext("Default");

    //private static boolean isStarted = false;

    private void start(LoggerContext context) {
        try (AutoCloseConfigDescriptor descriptor = AndroidLog4jHelper.getConfig()) {
            if (descriptor.config.isPresent()) {
                ConfigDescriptor config = descriptor.config.get();
                ConfigurationSource source = new ConfigurationSource(config.inputStream, config.url);
                Configuration configuration = ConfigurationFactory.getInstance().getConfiguration(context, source);
                context.start(configuration);
            } else {
                context.start();
            }
        }
    }

    @Override
    public LoggerContext getContext(final String fqcn, final ClassLoader loader, final boolean currentContext) {
        if (!CONTEXT.isStarted()) {
            start(CONTEXT);
        }
        final LoggerContext ctx = ContextAnchor.THREAD_CONTEXT.get();
        return ctx != null ? ctx : CONTEXT;
    }


    @Override
    public LoggerContext getContext(final String fqcn, final ClassLoader loader, final boolean currentContext,
                                    final URI configLocation) {
        if (!CONTEXT.isStarted()) {
            start(CONTEXT);
        }
        final LoggerContext ctx = ContextAnchor.THREAD_CONTEXT.get();
        return ctx != null ? ctx : CONTEXT;
    }

    public LoggerContext locateContext(final String name, final String configLocation) {
        if (!CONTEXT.isStarted()) {
            start(CONTEXT);
        }
        return CONTEXT;
    }

    @Override
    public void removeContext(final LoggerContext context) {
    }

    @Override
    public List<LoggerContext> getLoggerContexts() {
        if (!CONTEXT.isStarted()) {
            start(CONTEXT);
        }

        return List.of(CONTEXT);
    }
}
