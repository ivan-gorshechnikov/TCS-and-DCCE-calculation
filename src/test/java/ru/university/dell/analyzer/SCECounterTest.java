package ru.university.dell.analyzer;

import ru.university.dell.model.SCE;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.university.dell.services.LoadType;
import ru.university.dell.services.SCECounter;

class SCECounterTest {
    @Test
    void getMetricValue() {
        SCE sce = new SCE(1, LoadType.CPU);
        Assertions.assertTrue(new SCECounter().getMetricValue(sce) > 0);
    }
}