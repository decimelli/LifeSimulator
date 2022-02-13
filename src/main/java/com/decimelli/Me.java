package com.decimelli;

import com.decimelli.exception.LifeException;
import com.decimelli.utils.RandomGenerator;

import java.util.Arrays;

import static com.decimelli.Individual.BirthGender.MALE;

public class Me {

    public static void main(String[] args) {
        Arrays.asList(
                new Individual.Builder("Stefan", "Decimelli").assignedGenderAtBirth(MALE).build()
        ).parallelStream().forEach(p -> {
            try {
                p.beginLife(36566767);
                p.waitForDeath();
                Ledger.recordDeath(p);
            } catch (LifeException | InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
