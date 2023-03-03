package ru.university.dell.metrics.services;

import ru.university.dell.metrics.model.Pair;
import ru.university.dell.metrics.model.SCE;
import org.apache.log4j.Logger;

public class SCECounter {
    private static final Logger logger = Logger.getLogger(SCECounter.class);

    public double getMetricValue(SCE sce) {
        if (sce.getData() == null) {
            logger.error("SCE data is null");
            throw new IllegalStateException("SCE data is null");
        }
        if (sce.getData().size() == 0) {
            logger.error("This machine has not any load dump");
            throw new IllegalArgumentException("This machine has not any load dump");
        }
        double summary = 0;
        logger.info("Start calculating SCE");
        for (Pair pair : sce.getData()) {
            summary += pair.getValue();
        }
        logger.info("SCE is " + summary / sce.getData().size());
        return summary / sce.getData().size();
    }
}
