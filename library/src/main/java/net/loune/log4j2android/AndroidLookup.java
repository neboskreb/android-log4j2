package net.loune.log4j2android;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.lookup.StrLookup;

import java.io.File;

/**
 * Created by loune on 16/05/2015.
 */
@Plugin(name = "android", category = "Lookup")
public class AndroidLookup implements StrLookup {
    /**
     * Lookup the value for the key.
     * @param key  the key to be looked up, may be null
     * @return The value for the key.
     */
    public String lookup(String key) {
        File filesDir = AndroidLog4jHelper.getFilesDir();
        if ("filesdir".equals(key)) {
            return filesDir.getAbsolutePath();
        }

        File externalFilesDir = AndroidLog4jHelper.getExternalFilesDir(null);
        if ("externalfilesdir".equals(key)) {
            if (externalFilesDir == null) {
                return null;
            }
            return externalFilesDir.getAbsolutePath();
        }

        if ("logfilesdir".equals(key)) {
            File baseDir = externalFilesDir == null ? filesDir : externalFilesDir;
            return new File(baseDir, "logs").getAbsolutePath();
        }

        return null;
    }

    /**
     * Lookup the value for the key using the data in the LogEvent.
     * @param event The current LogEvent.
     * @param key  the key to be looked up, may be null
     * @return The value associated with the key.
     */
    public String lookup(LogEvent event, String key) {
        return lookup(key);
    }
}
