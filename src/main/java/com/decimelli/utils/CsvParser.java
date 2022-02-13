package com.decimelli.utils;

import com.decimelli.Individual;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class CsvParser {

    private static final CsvParser calculator = new CsvParser();

    private List<String[]> deathProbability;
    private List<String[]> casualties;

    private CsvParser() {
        try (
                CSVReader deathProbabilityReader = new CSVReader(new FileReader(Objects.requireNonNull(
                        this.getClass().getClassLoader().getResource("death_probability.csv")).getPath()));
                CSVReader casualtiesReader = new CSVReader(new FileReader(Objects.requireNonNull(
                        this.getClass().getClassLoader().getResource("casualties.csv")).getPath()))
        ) {
            this.deathProbability = deathProbabilityReader.readAll();
            this.casualties = casualtiesReader.readAll();
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }

    public static CsvParser getInstance() {
        return calculator;
    }

    public float getDeathProbability(Individual person) {
        int rowIndex = person.getAssignedGenderAtBirth() == Individual.BirthGender.MALE ? 1 : 5;
        String[] row = deathProbability.get(Integer.min(person.getAge(), deathProbability.size() - 1));
        return Float.parseFloat(row[rowIndex]);
    }

    public String getDeathCause(Individual person) {
        for (int i = 0; i < 3; i++) {
            for (String[] row : casualties) {
                if (person.getAge() < Integer.parseInt(row[1]) || person.getAge() > Integer.parseInt(row[2])) {
                    continue;
                }
                float probability = Float.parseFloat(row[0]);
                if (RandomGenerator.testChance(probability)) {
                    return row[3];
                } else {
                    row[0] = String.valueOf(probability * 2.0);
                }
            }
        }
        return "murder";
    }
}
