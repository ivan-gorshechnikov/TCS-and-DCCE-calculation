package ru.university.dell.metrics.analyzer;

import ru.university.dell.model.DCCE;
import ru.university.dell.services.DCCECounter;

public class DCCEValue implements MetricValue {
    private final DCCE dcce;
    private final double result;

    public DCCEValue(DCCE dcce) {
        this.dcce = dcce;
        this.result = new DCCECounter().getMetricValue(dcce);
    }

    public DCCE getDcce() {
        return dcce;
    }

    public double getResult() {
        return result;
    }
}
