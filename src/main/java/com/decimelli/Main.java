package com.decimelli;

import com.decimelli.utils.RandomGenerator;

import static com.decimelli.Individual.BirthGender.MALE;

public class Main {

    public static void main(String[] args) {
        Individual person = new Individual.Builder("Stefan", "Decimelli").assignedGenderAtBirth(MALE).build();
        long randomSpeedOfLife = RandomGenerator.random(100000000, 1000000);
        person.beginLife(randomSpeedOfLife);
    }
}