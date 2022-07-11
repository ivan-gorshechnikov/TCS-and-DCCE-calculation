package ru.university.dell.services;

import org.junit.jupiter.api.Test;
import ru.university.dell.model.TCS;

import static org.junit.jupiter.api.Assertions.*;

class TCSCounterTest {

    @Test
    void getMetricValue() {
        TCS tcs = new TCS(1);
        System.out.println(new TCSCounter().getMetricValue(tcs));
    }
}