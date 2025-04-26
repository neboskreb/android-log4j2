# Support for Log4J2 in Android
Yet another one. But this one works.


# Features
* Seamless integration of Log4J over SLF4J into your Android application
* File-based configuration of logging for fine control - just like in proper Java
* Separate configurations for Debug, Release, JUnit tests, and Android tests 
* Extension for JUnit 5
* Rule for JUnit 4

The example section shows the usage of SLF4J facade on the lib side and the log control using Log4J2 on the app side.


# build.gradle

```groovy
dependencies {
    implementation 'io.github.neboskreb:android-log4j2:2.24'

    androidTestImplementation 'io.github.neboskreb:android-log4j2-junit5:2.24'
}
```

See full source for [app](examples/app/build.gradle) and [lib](examples/lib/build.gradle) in the [examples](examples) folder. 


# Examples
The example is found in [examples](examples) folder. It shows a classic setup of a library The Lib consumed by an application The App. The lib does some logging - which is handy for its developers but not so much for the devs of the App who are more interested in their own logs. And for the release, of course, the noise in logs must be reduced to the minimum. Library `android-log4j2` helps to achieve both points easily.

## The Lib
The library should not use any concrete logging engine but rather a logging facade, in this case SLF4J. The choice of the concrete logging implementation is on the consumer of the library.

However, if the developers want to use Log4J2 for testing/debugging they can do it by putting a configuration file for the tests. See chapter [Android test](#android-tests) below for details.

## The App
The app has to say which concrete logging engine is used, and what loglevels are. 

Library `android-log4j2` offers seamless and easy integration with Log4J2 engine. Just initialize the log context with a one-liner in your app, the rest is magic:
```kotlin
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        AndroidLog4jHelper.initialize(this.applicationContext)
    }
}
```

If you omit the initialization, the log will be silent - which might be a very good idea for a Release build.

## Android tests

Normally you don't want your tests to log anything. A properly written test provides all information via `assert`s so a developer doesn't need to consult the logs to find why a test failed. As the default loglevel in tests is OFF, you don't need to add anything.

However, if you _do_ need to change the loglevel, you can provide your configuration in `androidTest/assets/log4j2.properties` and configure your tests as follows: 

### JUnit 5
_NOTE: For instructions how to enable JUnit 5 in your Android project in the first place, please refer to [android-junit5](https://github.com/mannodermaus/android-junit5) plugin._

```groovy
dependencies {
    androidTestImplementation 'io.github.neboskreb:android-log4j2-junit5:2.24'
}
```

```kotlin
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(AndroidLog4jExtension::class)
class MyJUnit5AndroidTest {
    @Test
    fun myTest() {
        ...
    }
}
```

### JUnit 4

```groovy
dependencies {
    androidTestImplementation 'io.github.neboskreb:android-log4j2-junit4:2.24'
}
```

```kotlin
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MyJUnit4AndroidTest {
   @get:Rule
   val rule = AndroidLog4jRule()

    @Test
    fun myTest() {
        ...
    }
}
```

## JUnit tests

No changes to the code required. Put your configuration in `test/resources/log4j2.properties` and it just works.

```groovy
dependencies {
    testImplementation 'io.github.neboskreb:android-log4j2:2.24'
}
```



# File-based configuration of the logging level

## Location of config files
The Log4J2 configuration file is called `log4j2.properties` and is found in the following locations:
1) for unit tests, nothing changed. It is where we always had it - in `src/test/resources`
2) for Android tests, it is in `src/androidTest/assets`
3) for application, it depends on the build variant:
   * for Debug builds: in `src/debug/assets`
   * for Release builds: in `src/release/assets`

If you do not separate your Debug and Release source sets, you can keep it in `src/main/assets`; just don't forget to adjust the log levels before the release.
    
In Android tests, the configuration from `src/androidTest/assets` overrides the config from the app.

If no config file provided, the log will be silent.


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

# Reduce the noise from the lib:
logger.lib.name = com.github.neboskreb.lib
logger.lib.level = warn

logger.app.name = com.github.neboskreb.app
logger.app.level = all
```

## Logcat appender attributes

### stack-trace-rendering
Select which engine to use for rendering the stack trace when logging the exceptions. Some Android tools might get confused with Log4J's stack traces.

Options: `logcat` (default), `log4j2`


# Known issues
* XML configuration is broken due to bug in Android XML parser. Fix is in progress; for now use `.properties` configuration.


# Credits
This work is based on the [example app](https://github.com/loune/log4j2-android) by Loune. 


# Contribution
Pull requests are welcome! If you plan to open one, please first create an issue where you describe the problem/gap your contribution closes, and tag the keeper(s) of this repo so they could get back to you with help.
