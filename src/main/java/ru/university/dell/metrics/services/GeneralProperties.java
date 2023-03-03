package ru.university.dell.metrics.services;

public class GeneralProperties {
    static private String projectPath;
    static private String configPath;

    public static String getConfigPath() {
        return configPath;
    }

    public static String getDatasetPath() {
        return datasetPath;
    }

    static private String datasetPath;


    public static String getProjectPath() {
        return projectPath;
    }

    public static void setProjectPath(String projectPath) {
        GeneralProperties.projectPath = projectPath;
        GeneralProperties.configPath = projectPath + "/config/config.yaml";
        GeneralProperties.datasetPath = projectPath + "/dataset/expenditures.csv";
    }

}
