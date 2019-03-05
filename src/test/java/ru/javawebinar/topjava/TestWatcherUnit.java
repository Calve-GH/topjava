package ru.javawebinar.topjava;

import org.junit.AssumptionViolatedException;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.slf4j.Logger;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class TestWatcherUnit extends Stopwatch {

    private Map<String, Long> stat;

    public TestWatcherUnit(Map<String, Long> stat) {
        this.stat = stat;
    }

    private static void logInfo(Description description, String status, long nanos) {
        String testName = description.getMethodName();
        System.out.println(String.format("Test %s %s, spent %d microseconds",
                testName, status, TimeUnit.NANOSECONDS.toMillis(nanos)));
    }

    public static void printStatistics(Logger log, Map<String, Long> stat) {
        StringBuilder sb = new StringBuilder();
        Long sum = 0L;
        for (Map.Entry<String, Long> e : stat.entrySet()) {
            sb.append(System.lineSeparator()).append(e.getKey()).append(" = ").append(e.getValue()).append(" ms");
            sum += e.getValue();
        }
        sb.append(System.lineSeparator()).append("Total testing time = ").append(sum).append(" ms");
        log.info(sb.toString());
    }

    @Override
    protected void succeeded(long nanos, Description description) {
        logInfo(description, "succeeded", nanos);
        stat.putIfAbsent(description.getMethodName(), convertNanosToMills(nanos));
    }

    @Override
    protected void failed(long nanos, Throwable e, Description description) {
        logInfo(description, "failed", nanos);
        stat.putIfAbsent(description.getMethodName(), convertNanosToMills(nanos));
    }

    @Override
    protected void skipped(long nanos, AssumptionViolatedException e, Description description) {
        logInfo(description, "skipped", nanos);
        stat.putIfAbsent(description.getMethodName(), convertNanosToMills(nanos));
    }

    @Override
    protected void finished(long nanos, Description description) {
        logInfo(description, "finished", nanos);
        stat.putIfAbsent(description.getMethodName(), convertNanosToMills(nanos));
    }

    private Long convertNanosToMills(Long nanos) {
        return TimeUnit.NANOSECONDS.toMillis(nanos);
    }
}