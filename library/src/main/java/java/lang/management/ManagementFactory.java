package java.lang.management;

import java.util.Collections;

// FIXME Remove this once 2.24.0 is released
// https://github.com/apache/logging-log4j2/issues/2774#issuecomment-2254418643
public class ManagementFactory {
    public static java.lang.management.RuntimeMXBean getRuntimeMXBean() {
        return Collections::emptyList;
    }
}
