package ru.university.dell.model;

import java.sql.Timestamp;

public class Pair {
    private final Double value;
    private final Timestamp time;

    public Pair(double aDouble, Timestamp timestamp) {
        this.value = aDouble;
        this.time = timestamp;
    }

    public Double getValue() {
        return value;
    }

    public Timestamp getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "value=" + value +
                ", timestamp=" + time;
    }
}
