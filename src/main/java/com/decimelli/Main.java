package com.decimelli;

import com.decimelli.exception.LifeException;
import com.decimelli.utils.RandomGenerator;

import java.util.Arrays;

import static com.decimelli.Individual.BirthGender.MALE;

public class Main {

    public static void main(String[] args) {
        Arrays.asList(
                new Individual.Builder("Gandalf", "the Grey").assignedGenderAtBirth(MALE).build(),
                new Individual.Builder("Frodo", "Baggins").assignedGenderAtBirth(MALE).build(),
                new Individual.Builder("Samwise", "Gamgee").assignedGenderAtBirth(MALE).build(),
                new Individual.Builder("Aragorn", "son of Arathorn").assignedGenderAtBirth(MALE).build(),
                new Individual.Builder("Legolas", "Greenleaf").assignedGenderAtBirth(MALE).build(),
                new Individual.Builder("Boromir", "son of Denethor").assignedGenderAtBirth(MALE).build(),
                new Individual.Builder("Peregrin", "Took").assignedGenderAtBirth(MALE).build(),
                new Individual.Builder("Meriadoc", "Brandybuck").assignedGenderAtBirth(MALE).build(),
                new Individual.Builder("Gimli", "son of Gloin").assignedGenderAtBirth(MALE).build()
        ).parallelStream().forEach(p -> {
            try {
                p.beginLife(RandomGenerator.random(1000000000, 10000000));
                p.waitForDeath();
                Ledger.recordDeath(p);
            } catch (LifeException | InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}