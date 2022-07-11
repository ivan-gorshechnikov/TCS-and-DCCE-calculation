package ru.university.dell.controller;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.Yaml;
import ru.university.dell.services.CSVParser;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;


@RestController
public class TCSController {
    private static final Logger logger = Logger.getLogger(TCSController.class);

    private final String configPath = "./config/config.yaml";
    private final String filePath = "./dataset/expenditures.csv";

    @GetMapping("/metric/tcs/categories")
    public Map<String, Object> getCategoriesName() {
        Map<String, Object> result = new HashMap<>();
        Yaml yaml = new Yaml();
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(configPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Map<String, Object> map = yaml.load(inputStream);
        int i = 1;
        while (map.get("category_" + i) != null) {
            logger.info("Put category_" + i);
            result.put("category_" + i, map.get("category_" + i));
            i++;
        }
        return result;
    }

    @GetMapping("/metric/tcs/mock")
    public Map<String, Object> getMockData() {
        Map<String, Object> result = new HashMap<>();
        List<String[]> data = new CSVParser(filePath).getData();
        result.put("categories", data.get(0));
        for (int i = 1; i < data.size(); i++) {
            result.put(data.get(i)[0], data.get(i));
        }
        return result;
    }
}