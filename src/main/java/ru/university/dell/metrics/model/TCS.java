package ru.university.dell.metrics.model;

import org.apache.log4j.Logger;
import org.yaml.snakeyaml.Yaml;
import ru.university.dell.metrics.services.CSVParser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TCS implements Metric {

    private static final Logger logger = Logger.getLogger(SCE.class);

    private final List<String[]> data;
    private final String filePath = "./dataset/expenditures.csv";
    private final String configPath = "./config/config.yaml";
    private final int month;

    public TCS(int month) {
        this.month = month;
        this.data = collectData();
    }

    public List<String[]> getData() {
        return data;
    }

    private List<String[]> collectData() throws IllegalStateException, IllegalArgumentException {
        List<String[]> data = new CSVParser(filePath).getData();
        if (data == null) {
            logger.error("Data for TCS is empty");
            throw new IllegalStateException("Data for TCS is empty");
        }
        List<String[]> dataResult = new ArrayList<>();
        dataResult.add(data.get(0));
        for (int i = 1; i < data.size(); i++) {
            if (this.month == Integer.parseInt(data.get(i)[0])) {
                dataResult.add(data.get(i));
                break;
            }
        }
        if (dataResult.size() == 1) {
            logger.error("Wrong month");
            throw new IllegalArgumentException("Wrong month");
        }

        dataResult.add(collectTypes(dataResult.get(0)));
        return dataResult;
    }

    private String[] collectTypes(String[] categories) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(configPath);
        } catch (FileNotFoundException e) {
            logger.error("Config file not found", e);
        }
        Map<String, Map<String, Map<String, Map<String, Object>>>> yaml = new Yaml().load(inputStream);
        String[] types = new String[categories.length];
        for (int i = 1; i < categories.length; i++) {
            types[i] = String.valueOf(yaml.get("category_" + categories[i].charAt(0))
                    .get("category_" + categories[i].substring(0, 2))
                    .get("category_" + categories[i])
                    .get("type"));
        }
        return types;
    }
}
