package com.decimelli;

import java.text.MessageFormat;

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
        System.out.println(MessageFormat.format("[INFO] {0}", message));
    }
}
