package ru.university.dell.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.university.dell.model.SCE;
import ru.university.dell.services.LoadType;


class SCETest {

    @Test
    void getData() {
        SCE sce1 = new SCE(1, LoadType.CPU);
        SCE sce2 = new SCE(1, LoadType.CPU);
        Assertions.assertNotNull(sce1.getData());
        Assertions.assertEquals(sce1.toString(), sce2.toString());
    }
}