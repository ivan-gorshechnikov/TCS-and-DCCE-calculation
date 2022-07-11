package ru.university.dell.database;

import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Properties;

public class DbConnector {

    private static final Logger logger = Logger.getLogger(DbConnector.class);

    private final DbSettings dbSettings;
    private Connection connection;

    public DbConnector() throws FileNotFoundException {
        logger.debug("Init database");
        dbSettings = new DbSettings();
    }

    public Connection getConnection() {
        return connection;
    }

    public Connection openConnection() throws SQLException {
        logger.info("Starting open connection");
        Properties props = new Properties();
        props.setProperty("user", dbSettings.getUser());
        props.setProperty("password", dbSettings.getPassword());
        props.setProperty("ssl", String.valueOf(dbSettings.getSsl()));
        connection = DriverManager.getConnection(dbSettings.getUrl(), props);
        logger.info("Connection open");
        return connection;
    }

    public ResultSet getNodesInfo() throws SQLException, FileNotFoundException {
        logger.info("Start get nodes");
        String sql = "select * from nodes";
        if (connection == null || connection.isClosed()) {
            logger.info("Connection was closed");
            openConnection();
        }
        Statement stmt = connection.createStatement();
        logger.info("Get data of nodes");
        return stmt.executeQuery(sql);
    }

    public void closeConnection() throws SQLException {
        logger.info("Start closing connection");
        connection.close();
        logger.info("Connection close");
    }
}
