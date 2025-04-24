package java.lang.management;

import java.util.List;

// FIXME Remove this once 2.24.0 is released
// https://github.com/apache/logging-log4j2/issues/2774#issuecomment-2254418643
public interface RuntimeMXBean {
    public List<String> getInputArguments();
}
