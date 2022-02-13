package com.decimelli;

import com.decimelli.exception.LifeException;
import com.decimelli.utils.Console;
import com.decimelli.utils.CsvParser;
import com.decimelli.utils.RandomGenerator;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static java.util.concurrent.TimeUnit.*;

// Singleton
@SuppressWarnings("unused")
public class Individual implements Runnable {

    public void waitForDeath() throws InterruptedException {
        this.thread.join(0);
    }

    public enum BirthGender {
        MALE, FEMALE
    }

    public static final long DEFAULT_MULTIPLIER = 60;
    public static final long DEFAULT_LOG_INTERVAL_IN_SECONDS = 1;

    private final Calendar birthday;
    private int age;
    private Long currentDateInMillis;
    private final String firstname;
    private final String lastname;
    private final BirthGender assignedGenderAtBirth;
    private long multiplier;
    private Long deathDate;
    private String causeOfDeath;
    private long logInterval;
    private boolean dead = false;
    private Thread thread;

    private Individual(Builder builder) {
        this.birthday = Calendar.getInstance();
        this.currentDateInMillis = this.birthday.getTimeInMillis();
        this.firstname = builder.firstname;
        this.lastname = builder.lastname;
        this.assignedGenderAtBirth = builder.assignedGenderAtBirth;
    }

    public void beginLife(long multiplier, long logInterval) throws LifeException {
        this.multiplier = multiplier;
        this.logInterval = logInterval;
        if (multiplier > 3155695200L) {
            throw new LifeException("Cannot live more than 100 years at a time");
        }
        this.thread = new Thread(this);
        this.thread.start();
    }

    public void beginLife(long multiplier) throws LifeException {
        this.beginLife(multiplier, DEFAULT_LOG_INTERVAL_IN_SECONDS);
    }

    public void beginLife() throws LifeException {
        this.beginLife(DEFAULT_MULTIPLIER, DEFAULT_LOG_INTERVAL_IN_SECONDS);
    }

    @Override
    public void run() {
        Console.getPrinter().info("Congratulations! {0} {1} was born on {2}",
                this.firstname, this.lastname, Console.getPrinter().prettyDate(this.birthday.getTimeInMillis())
        );
        while (!dead) {
            waitOneSecond();
            this.currentDateInMillis = this.currentDateInMillis + 1000L * multiplier;
            Map<String, Long> indexes = calculateIndexes(this.currentDateInMillis - this.birthday.getTimeInMillis());
            this.age = Math.toIntExact(indexes.get("year"));
            Console.getPrinter().info("{0} has now been alive for {1}", this.firstname, getTimeString(indexes));
            float chanceOfDeath = CsvParser.getInstance().getDeathProbability(this);
            this.dead = RandomGenerator.testChance(chanceOfDeath);
        }
        this.deathDate = this.currentDateInMillis;
        this.causeOfDeath = CsvParser.getInstance().getDeathCause(this);
        Console.getPrinter().info(
                "Oh no! {0} {1} has has died on {2} at the age of {3} due to {4}.",
                this.firstname, this.lastname, Console.getPrinter().prettyDate(this.currentDateInMillis),
                this.age, this.causeOfDeath
        );
    }

    private Map<String, Long> calculateIndexes(long milliseconds) {
        Map<String, Long> indexes = new HashMap<>();
        indexes.put("day", MILLISECONDS.toDays(milliseconds));
        indexes.put("year", indexes.get("day") / 365);
        indexes.put("day", indexes.get("day") % 365);
        indexes.put("month", indexes.get("day") / 30);
        indexes.put("day", indexes.get("day") % 30);
        indexes.put("week", indexes.get("day") / 7);
        indexes.put("day", indexes.get("day") % 7);
        indexes.put("hour", MILLISECONDS.toHours(milliseconds) - DAYS.toHours(MILLISECONDS.toDays(milliseconds)));
        indexes.put("minute",
                MILLISECONDS.toMinutes(milliseconds) - HOURS.toMinutes(MILLISECONDS.toHours(milliseconds)));
        indexes.put("second",
                MILLISECONDS.toSeconds(milliseconds) - MINUTES.toSeconds(MILLISECONDS.toMinutes(milliseconds)));
        return indexes;
    }

    private void waitOneSecond() {
        try {
            Thread.sleep(logInterval * 1000L + RandomGenerator.random(1000));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println(e.getMessage());
        }
    }

    public int getAge() {
        return this.age;
    }

    public Calendar getBirthday() {
        return birthday;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public Long getDeathDate() {
        return deathDate;
    }

    public String getCauseOfDeath() {
        return causeOfDeath;
    }

    public BirthGender getAssignedGenderAtBirth() {
        return assignedGenderAtBirth;
    }

    private String getTimeString(Map<String, Long> indexes) {
        StringBuilder sb = new StringBuilder();
        for (String unit : Arrays.asList("year", "month", "week", "day", "hour", "minute", "second")) {
            long value = indexes.get(unit);
            if (value > 0) {
                sb.append(value).append(" ").append(unit);
                if (value > 1) {
                    sb.append("s");
                }
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    public static class Builder {

        private final String firstname;
        private final String lastname;
        private BirthGender assignedGenderAtBirth = BirthGender.MALE;

        public Builder(String firstname, String lastname) {
            this.firstname = firstname;
            this.lastname = lastname;
        }

        public Individual build() {
            return new Individual(this);
        }

        public Builder assignedGenderAtBirth(BirthGender assignedGenderAtBirth) {
            this.assignedGenderAtBirth = assignedGenderAtBirth;
            return this;
        }
    }
}
