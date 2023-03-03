package ru.university.dell.metrics.database;

import org.apache.log4j.Logger;
import org.yaml.snakeyaml.Yaml;
import ru.university.dell.services.GeneralProperties;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

import static ru.university.dell.database.DbConfig.*;

public class DbSettings {
    private static final Logger logger = Logger.getLogger(DbSettings.class);

    private final String configPath = GeneralProperties.getConfigPath();
    private final String url;
    private final String user;
    private final String password;
    private final Boolean ssl;

    public DbSettings() throws FileNotFoundException, IllegalStateException {
        Map<String, Object> config = readConfig();
        if (config == null) {
            logger.error("No database in config file");
            throw new IllegalStateException("No database in config file");
        }
        if (config.get(host.toString()) == null) {
            logger.error("No host settings in config file");
            throw new IllegalStateException("No host settings in config file");
        }
        if (config.get(port.toString()) == null) {
            logger.error("No port settings in config file");
            throw new IllegalStateException("No port settings in config file");
        }
        if (config.get(name.toString()) == null) {
            logger.error("No db name settings in config file");
            throw new IllegalStateException("No db name settings in config file");
        }
        if (config.get(DbConfig.user.toString()) == null) {
            logger.error("No user settings in config file");
            throw new IllegalStateException("No user settings in config file");
        }
        if (config.get(DbConfig.password.toString()) == null) {
            logger.error("No password settings in config file");
            throw new IllegalStateException("No password settings in config file");
        }
        if (config.get(DbConfig.ssl.toString()) == null) {
            logger.error("No ssl settings in config file");
            throw new IllegalStateException("No ssl settings in config file");
        }
        this.url = "jdbc:postgresql://" + config.get(host.toString()) + ":" + config.get(port.toString()) + "/" + config.get(name.toString());
        this.user = (String) config.get(DbConfig.user.toString());
        this.password = (String) config.get(DbConfig.password.toString());
        this.ssl = (Boolean) config.get(DbConfig.ssl.toString());
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public Boolean getSsl() {
        return ssl;
    }

    private Map<String, Object> readConfig() throws FileNotFoundException {
        logger.debug("Read config");
        InputStream inputStream = new FileInputStream(configPath);
        Map<String, Map<String, Object>> config = new Yaml().load(inputStream);
        if (config == null) {
            logger.error("Config file not found");
            throw new IllegalStateException("Config file not found");
        }
        return config.get("database");
    }
}
