package ru.javawebinar.topjava;

import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class MyJUnitStopWatch extends Stopwatch {
    public static final Logger LOGGER = LoggerFactory.getLogger(MyJUnitStopWatch.class);
    private static final StringBuilder stringBuilder = new StringBuilder();
    private final int maxLengthTestName = 23;
    public static final String ALL_MESSAGES = convertStringBuilderToString();

    @Override
    protected void finished(long nanos, Description description) {
        stringBuilder.append(String.format("Test %s %d ms\n",
                getTestName(description), TimeUnit.NANOSECONDS.toMillis(nanos)));
        LOGGER.info(String.format("Test %s, spent %d milliseconds",
                description.getMethodName(), TimeUnit.NANOSECONDS.toMillis(nanos)));
    }

    private String getTestName(Description description) {
        String testName = description.getMethodName();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(testName);
        if (testName.length() < maxLengthTestName) {
            for (int i = 0; i < maxLengthTestName - testName.length(); i++) {
                stringBuilder.append(" ");
            }
        }
        return stringBuilder.toString();
    }

    private static String convertStringBuilderToString() {
        return MyJUnitStopWatch.stringBuilder.toString();
    }
}
