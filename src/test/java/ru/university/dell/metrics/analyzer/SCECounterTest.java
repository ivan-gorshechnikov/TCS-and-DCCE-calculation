package ru.university.dell.metrics.analyzer;

import ru.university.dell.metrics.model.SCE;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.university.dell.metrics.services.LoadType;
import ru.university.dell.metrics.services.SCECounter;

class SCECounterTest {
    @Test
    void getMetricValue() {
        SCE sce = new SCE(1, LoadType.CPU);
        Assertions.assertTrue(new SCECounter().getMetricValue(sce) > 0);
    }
}