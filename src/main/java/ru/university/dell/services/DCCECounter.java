package ru.university.dell.services;

import ru.university.dell.model.DCCE;
import ru.university.dell.model.SCE;
import org.apache.log4j.Logger;


public class DCCECounter {
    private static final Logger logger = Logger.getLogger(DCCECounter.class);

    public double getMetricValue(DCCE dcce) {
        double summary = 0;
        logger.info("Starting counting DCCE");
        for (SCE sce : dcce.getData()) {
            summary += new SCECounter().getMetricValue(sce);
        }
        logger.info("DCCE metric is " + summary / dcce.getData().size());
        return summary / dcce.getData().size();
    }
}
