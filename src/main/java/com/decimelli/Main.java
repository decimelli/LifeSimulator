package com.decimelli;

import static com.decimelli.Individual.BirthGender.MALE;

public class Main {

    public static void main(String[] args) {
        Individual person = new Individual.Builder("Stefan", "Decimelli").assignedGenderAtBirth(MALE).build();
        person.beginLife();
    }
}