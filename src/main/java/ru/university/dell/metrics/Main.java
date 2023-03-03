package ru.university.dell.metrics;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.university.dell.services.GeneralProperties;
import ru.university.dell.services.SCECounter;


@SpringBootApplication
public class Main {
    private static final Logger logger = Logger.getLogger(SCECounter.class);

    public static void main(String[] args) {
        if (args.length != 1) {
            logger.error("The program must have one input argument(path to the project directory)");
            System.exit(1);
        }
        GeneralProperties.setProjectPath(args[0]);
        SpringApplication.run(Main.class, args);
    }
}
