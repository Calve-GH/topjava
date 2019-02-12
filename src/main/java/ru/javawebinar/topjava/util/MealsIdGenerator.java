package ru.javawebinar.topjava.util;

import java.util.concurrent.atomic.AtomicLong;

public class MealsIdGenerator {
    private final static AtomicLong counter = new AtomicLong(0);

    public static synchronized long getId() {
        return counter.incrementAndGet();
    }
}