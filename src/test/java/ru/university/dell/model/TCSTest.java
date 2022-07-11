package ru.university.dell.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class TCSTest {

    @Test
    void getData() {
        TCS tcs = new TCS(1);
        System.out.println(Arrays.toString(tcs.getData().get(2)));
    }
}