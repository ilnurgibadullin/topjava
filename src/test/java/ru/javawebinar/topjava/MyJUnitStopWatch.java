package ru.javawebinar.topjava;

import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class MyJUnitStopWatch extends Stopwatch {

    private static StringBuilder stringBuilder = new StringBuilder();
    private static final Logger LOGGER = LoggerFactory.getLogger(MyJUnitStopWatch.class);

    public static String getAllMessages() {
        return stringBuilder.toString();
    }

    @Override
    protected void finished(long nanos, Description description) {
        stringBuilder.append(String.format("Test %s, spent %d microseconds\n",
                description.getMethodName(), TimeUnit.NANOSECONDS.toMillis(nanos)));
        LOGGER.info(String.format("Test %s, spent %d microseconds",
                description.getMethodName(), TimeUnit.NANOSECONDS.toMillis(nanos)));
    }
}
