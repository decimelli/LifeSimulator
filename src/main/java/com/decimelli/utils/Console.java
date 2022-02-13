package com.decimelli.utils;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

// Singleton
public class Console {

    private static final Console instance = new Console(); // Singleton instance

    private Console() {
        // Do not instantiate singleton
    }

    public static Console getPrinter() {
        return instance;
    }

    public void info(String messageFormat, Object... args) {
        String message = MessageFormat.format(messageFormat, args);
        System.out.println(message);
    }

    public String prettyDate(Long millis) {
        Date date = new Date(millis);
        DateFormat formatter = new SimpleDateFormat("MMMM WW, yyyy");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        return formatter.format(date);
    }
}
