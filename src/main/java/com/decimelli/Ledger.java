package com.decimelli;

import com.decimelli.utils.Console;
import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;

public class Ledger {

    private Ledger() {
        // Utils class
    }

    public static synchronized void recordDeath(Individual person) {
        try (CSVWriter writer = new CSVWriter(new FileWriter("death_record.csv", true))) {
            String[] entry = new String[5];
            entry[0] = person.getFirstname() + " " + person.getLastname();
            entry[1] = person.getAssignedGenderAtBirth().toString();
            entry[2] = String.valueOf(person.getAge());
            entry[3] = String.valueOf(Console.getPrinter().prettyDate(person.getDeathDate()));
            entry[4] = person.getCauseOfDeath();
            writer.writeNext(entry, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
