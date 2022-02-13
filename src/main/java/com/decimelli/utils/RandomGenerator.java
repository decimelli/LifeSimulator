package com.decimelli.utils;

import java.util.Random;

public class RandomGenerator {

    private static final Random r = new Random();

    private RandomGenerator() {
        // singleton
    }

    public static boolean testChance(float chance) {
        return r.nextInt(100) < chance;
    }

    public static long random(int maxValue) {
        return r.nextInt(maxValue);
    }

    public static long random(int maxValue, long minValue) {
        return r.nextInt(maxValue - 1) + minValue;
    }
}
