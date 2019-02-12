package ru.javawebinar.topjava.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class Dates {
    private Dates() {
    }

    public static String formatLocalDate(LocalDateTime localDate, String pattern) {
        try {
            return localDate.format(DateTimeFormatter.ofPattern(pattern));
        } catch (Exception e) {
            return "00:00 01.01.1900";
        }
    }
}