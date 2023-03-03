package ru.university.dell.metrics.services;

import org.junit.jupiter.api.Test;
import ru.university.dell.metrics.model.TCS;

class TCSCounterTest {

    @Test
    void getMetricValue() {
        TCS tcs = new TCS(1);
        System.out.println(new TCSCounter().getMetricValue(tcs));
    }
}