# Support for Log4J2 in Android
Yet another one. But this one works.

The example section shows the usage of SLF4J facade on the lib side and the log control using Log4J2 on the app side.


# Already works
* File-based configuration, except for XML format (because of defect in Android)
* Integration with SLF4J
* Configuration overriding in JUnit/Android tests

# TODO
* JUnit extension for initialization instead of @BeforeAll
* Registration of the plugins using annotation processor or PluginUtil
 
# Examples

It shows a classic example of a library The Lib consumed by an application The App. The lib does some logging, which is handy for its developers but might not be 
so for the devs of the App, who are more interested in their own logs during the development. And for the release, the noise in logs must be reduced to the minimum. 

## The Lib
The library does not use any specific logging library but rather a logging facade, in this case SLF4J. The choice (and control over) of the concrete logging 
implementation is on the consumer of the library.

However, the developers might want to use Log4J2 for the testing and debugging. They can do it by having a separate configuration file for the tests. 

## The App
The app has to say which concrete logging engine is used. Library `android-log4j2` offers seamless and easy integration with Log4J2 engine.


# File-based configuration of the logging level

## Location of config files
The Log4J2 configuration file is called `log4j2.properties` and is found in the following locations:
1) for unit tests, nothing changed. It is where we always had it - in `src/test/resources`
2) for Android tests, it is in `src/androidTest/assets`
3) for Android app, it is depending on the build variant:
   * for debug builds: in `src/debug/assets`
   * for release builds: in `src/release/assets`

If you do not separate your debug and release source sets, you can keep it in `src/main/assets`; just don't forget to adjust the log levels before the release.
    
In Android tests, the configuration from `src/androidTest/assets` overrides the config from the app.

## Example config file
Example of a typical `log4j2.properties` during the development:
```properties
# Root Logger
rootLogger = ALL, LOGCAT

# Direct log messages to LOGCAT
appender.logcat.type = Logcat
appender.logcat.name = LOGCAT
appender.logcat.layout.type = PatternLayout
appender.logcat.layout.pattern = %m%n
appender.logcat.stack-trace-rendering = logcat


logger.lib.name = com.github.neboskreb.lib
logger.lib.level = warn

logger.app.name = com.github.neboskreb.app
logger.app.level = all
```

## LogcatAppender attributes

### stack-trace-rendering
Select which engine to use for rendering the stack trace when logging the exceptions. Some Android tools might get confused with Log4J's stack traces.

Options: `logcat` (default), `log4j2`


# Credits
This work is based on the [example app](https://github.com/loune/log4j2-android) by Loune. 

# Contribution
Pull requests are welcome! If you plan to open one, please first create an issue where you describe the problem/gap your contribution closes, and tag the keeper(s) of this repo so they could get back to you with help.
