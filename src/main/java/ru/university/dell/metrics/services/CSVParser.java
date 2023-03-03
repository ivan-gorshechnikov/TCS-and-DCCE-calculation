package ru.university.dell.metrics.services;

import au.com.bytecode.opencsv.CSVReader;
import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

public class CSVParser {
    private static final Logger logger = Logger.getLogger(CSVParser.class);
    private final String filePath;

    public CSVParser(String filePath) {
        this.filePath = filePath;
    }

    public List<String[]> getData()
    {
        try {
            Reader reader = new FileReader(filePath);
            CSVReader csvReader = new CSVReader(reader);
            List<String[]> list = csvReader.readAll();
            reader.close();
            csvReader.close();
            return list;
        } catch (FileNotFoundException e) {
            logger.error("CSV file not found", e);
        } catch (IOException e) {
            logger.error("Error when closing or reading files", e);
        }
        return null;
    }
}
