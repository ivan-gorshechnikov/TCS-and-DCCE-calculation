package ru.university.dell.metrics.analyzer;

import ru.university.dell.model.SCE;
import ru.university.dell.services.SCECounter;

public class SCEValue implements MetricValue {
    private final double result;
    private final SCE sce;

    public SCEValue(SCE sce) {
        this.sce = sce;
        this.result = new SCECounter().getMetricValue(sce);
    }

    public double getResult() {
        return result;
    }

    public SCE getSce() {
        return sce;
    }
}
