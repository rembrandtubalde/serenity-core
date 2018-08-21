package net.serenitybdd.core.time;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;

import static java.lang.System.currentTimeMillis;

public class Stopwatch {

    private static final Logger LOGGER = LoggerFactory.getLogger(Stopwatch.class);

    long startTime = 0;

    public static Stopwatch started() {
        Stopwatch stopwatch = new Stopwatch();
        stopwatch.start();
        return stopwatch;
    }

    public void start() {
        startTime = currentTimeMillis();
    }

    public long stop() {
        validateStarted();
        long result = currentTimeMillis() - startTime;
        startTime = 0;
        return result;
    }

    private void validateStarted() {
        if (startTime == 0) {
            throw new IllegalStateException("stopwatch is already stopped");
        }
    }

    public String lapTimeFormatted() {
        validateStarted();
        return lapTimeFormatted(currentTimeMillis() - startTime);
    }

    public String executionTimeFormatted() {
        return lapTimeFormatted(stop());
    }

    public String lapTimeFormatted(Long executionTimeInMilliseconds) {
        return (executionTimeInMilliseconds < 1000) ? (executionTimeInMilliseconds + " ms") : (new DecimalFormat("#,###.#").format(executionTimeInMilliseconds / 1000.0) + " secs");
    }

    public long lapTime() {
        validateStarted();
        return currentTimeMillis() - startTime;
    }

    public long stop(String message) {
        long result = stop();
        LOGGER.debug("{} in {}", message, lapTimeFormatted(result));
        return result;
    }
}
