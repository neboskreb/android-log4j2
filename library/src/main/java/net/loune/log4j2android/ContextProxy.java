package net.loune.log4j2android;

import android.content.Context;
import android.content.res.AssetManager;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import static java.util.Arrays.asList;

class ContextProxy {
    private final Context[] contexts;

    public ContextProxy(Context... contexts) {
        this.contexts = contexts;
    }

    public AutoCloseConfigDescriptor getConfig(String... filenames) {
        for (Context context : contexts) {
            for (String filename : filenames) {
                AutoCloseConfigDescriptor result = getConfig(context, filename);
                if (result.config.isPresent()) {
                    return result;
                }
            }
        }

        return AutoCloseConfigDescriptor.empty();
    }

    public AutoCloseConfigDescriptor getConfig(Context context, String filename) {
        try {
            AssetManager assets = context.getAssets();
            String[] list = assets.list("");
            if (list == null || !asList(list).contains(filename)) {
                return AutoCloseConfigDescriptor.empty();
            } else {
                return AutoCloseConfigDescriptor.config(assets.open(filename), new URL("file:///" + filename));
            }

        } catch (IOException e) {
            throw new IllegalStateException("Error reading the configuration file");
        }
    }

    public File getFilesDir() {
        return contexts[0].getFilesDir();
    }

    public File getExternalFilesDir(String type) {
        return contexts[0].getExternalFilesDir(type);
    }
}
