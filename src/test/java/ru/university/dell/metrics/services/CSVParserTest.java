package ru.university.dell.metrics.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CSVParserTest {

    @Test
    void getData() {
        CSVParser CSVParser = new CSVParser("./dataset/expenditures.csv");
        Assertions.assertNotNull(CSVParser.getData());
        Assertions.assertNull(new CSVParser("").getData());
    }
}