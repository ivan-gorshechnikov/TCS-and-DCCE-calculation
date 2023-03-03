package ru.university.dell.metrics.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.university.dell.metrics.model.Pair;

import java.sql.Timestamp;

class PairTest {

    @Test
    void getValue() {
        Pair pair = new Pair(4, new Timestamp(5623));
        Assertions.assertEquals(pair.getValue(), 4);
    }

    @Test
    void getTime() {
        Pair pair = new Pair(4, new Timestamp(5623));
        System.out.println(pair.getTime());
        Assertions.assertEquals(pair.getTime(), new Timestamp(5623));
    }

    @Test
    void testToString() {
        Pair pair = new Pair(4, new Timestamp(5623));
        Assertions.assertEquals(pair.toString(), "value=" + pair.getValue() +
                ", timestamp=" + pair.getTime());
    }
}