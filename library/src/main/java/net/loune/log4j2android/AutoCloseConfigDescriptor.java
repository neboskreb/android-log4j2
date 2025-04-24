package net.loune.log4j2android;

import java.io.Closeable;
import java.io.InputStream;
import java.net.URL;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor
public class AutoCloseConfigDescriptor implements AutoCloseable {
    private static final AutoCloseConfigDescriptor EMPTY = new AutoCloseConfigDescriptor(Optional.empty());

    @Getter
    @AllArgsConstructor(access = PRIVATE)
    public static class ConfigDescriptor {
        public final InputStream inputStream;
        public final URL url;
    }

    /** @noinspection OptionalUsedAsFieldOrParameterType */
    public final Optional<ConfigDescriptor> config;

    public static AutoCloseConfigDescriptor empty() {
        return EMPTY;
    }

    public static AutoCloseConfigDescriptor config(InputStream inputStream, URL url) {
        return new AutoCloseConfigDescriptor(Optional.of(new ConfigDescriptor(inputStream, url)));
    }

    @Override
    public void close() {
        config.map(ConfigDescriptor::getInputStream)
              .ifPresent(this::closeIt);
    }

    @SneakyThrows
    private void closeIt(Closeable closeable) {
        closeable.close();
    }
}
