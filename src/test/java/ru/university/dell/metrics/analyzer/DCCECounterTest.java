package ru.university.dell.metrics.analyzer;

import ru.university.dell.metrics.model.DCCE;
import ru.university.dell.metrics.model.SCE;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.university.dell.metrics.services.DCCECounter;
import ru.university.dell.metrics.services.LoadType;
import ru.university.dell.metrics.services.SCECounter;

import java.util.ArrayList;
import java.util.Arrays;

class DCCECounterTest {

    @Test
    void getMetricValue() {
        DCCE dcce = new DCCE(new ArrayList<>(Arrays.asList(1, 2)), LoadType.CPU);
        Assertions.assertTrue(new DCCECounter().getMetricValue(dcce) > 0);
        Assertions.assertTrue(new DCCECounter().getMetricValue(dcce) - (new SCECounter().getMetricValue(new
                SCE(1, LoadType.CPU)) + new SCECounter().getMetricValue(new SCE(2, LoadType.CPU))) / 2 < 1e-5);
    }
}