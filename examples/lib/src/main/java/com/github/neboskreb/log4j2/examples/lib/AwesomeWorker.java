package com.github.neboskreb.log4j2.examples.lib;

import lombok.extern.slf4j.XSlf4j;

@XSlf4j
public class AwesomeWorker {
    public String doGreatJob(String param1, String param2) {
        log.entry(param1, param2);

        String result = param1 + param2;

        return log.exit(result);
    }

    public void doNotSoGreatJob() {
        try {
            epicFail();

        } catch (Exception e) {
            log.warn("Arhhhh, the job would be great if not this epic failure...", e);
        }
    }

    public void epicFail() throws Exception {
        throw log.throwing(new Exception("Boo!"));
    }
}
