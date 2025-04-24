package com.github.neboskreb.log4j2.examples.app;

import android.app.Application;
import net.loune.log4j2android.AndroidLog4jHelper;

/**
 * Created by loune on 7/05/2016.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        AndroidLog4jHelper.initialize(this.getApplicationContext());
    }
}
