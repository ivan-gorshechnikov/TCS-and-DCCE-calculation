package ru.university.dell.services;

import org.apache.log4j.Logger;
import ru.university.dell.model.TCS;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TCSCounter {
    private static final Logger logger = Logger.getLogger(TCSCounter.class);

    public Map<Integer, Object> getMetricValue(TCS tcs) {
        List<String[]> data = tcs.getData();
        logger.info("Start counting TCS metric for " + data.get(1)[0] + " month");
        Map<Integer, Object> result = new HashMap<>();
        for (int i = 1; i < data.get(0).length; i++) {
            int category = Integer.parseInt(data.get(0)[i]) / 100;
            result.putIfAbsent(category, 0.);
            String type = data.get(2)[i];
            if (type == null) {
                logger.error("Type is null");
                throw new NullPointerException("Type is null");
            } else if (type.equals("multiply")) {
                if (i + 1 == data.get(0).length) {
                    logger.error("Wrong config data");
                    throw new NullPointerException("Wrong config data");
                }
                result.put(category, Double.parseDouble(String.valueOf(result.get(category))) + Double.parseDouble(data.get(1)[i]) *
                        Double.parseDouble(data.get(1)[++i]));
            } else if (type.equals("sum")) {
                result.put(category, Double.parseDouble(String.valueOf(result.get(category))) + Double.parseDouble(data.get(1)[i]));
            }
        }
        logger.info("TCS metric was counted: " + result);
        return result;
    }
}
