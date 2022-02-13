package com.decimelli;

import java.util.Random;

public class RandomGenerator {

    private static final Random r = new Random();

    private RandomGenerator() {
        // singleton
    }

    public static boolean testChance(float chance) {
        return r.nextFloat() < chance;
    }

    public static long randomInt(int maxValue) {
        return r.nextInt(maxValue);
    }
}
