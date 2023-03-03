package ru.university.dell.metrics.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

class TCSTest {

    @Test
    void getData() {
        TCS tcs = new TCS(1);
        System.out.println(Arrays.toString(tcs.getData().get(2)));
    }
}