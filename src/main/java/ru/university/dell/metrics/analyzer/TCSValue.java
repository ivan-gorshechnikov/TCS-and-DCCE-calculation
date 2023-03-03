package ru.university.dell.metrics.analyzer;

import ru.university.dell.model.TCS;
import ru.university.dell.services.TCSCounter;

import java.util.Map;

public class TCSValue implements MetricValue {
    private final double result;
    private final Map<Integer, Object> categories;
    private final TCS tcs;

    public TCSValue(TCS tcs) {
        this.tcs = tcs;
        this.categories = new TCSCounter().getMetricValue(tcs);
        double result1 = 0;
        for (int i = 1; i < this.categories.size() + 1; i++) {
            result1 += Double.parseDouble(String.valueOf(categories.get(i)));
        }
        this.result = result1;
    }

    public double getResult() {
        return result;
    }

    public Map<Integer, Object> getCategories() {
        return categories;
    }

    public TCS getTcs() {
        return tcs;
    }
}
