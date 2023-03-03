package ru.university.dell.metrics.database;

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

    public ResultSet getNodesInfo() throws SQLException {
        logger.info("Start get nodes");
        String sql = "select * from nodes";
        if (connection == null || connection.isClosed()) {
            logger.info("Connection was closed");
            openConnection();
        }
        Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        logger.info("Get data of nodes");
        return stmt.executeQuery(sql);
    }

    public void closeConnection() throws SQLException {
        logger.info("Start closing connection");
        connection.close();
        logger.info("Connection close");
    }

    public void modifyEndTime(int id) throws SQLException {
        logger.info("Start modify end time to " + id + " node");
        String sql = "select max(timestamp) from node_load_dump where id_node = " + id + ";";
        if (connection == null || connection.isClosed()) {
            logger.info("Connection was closed");
            openConnection();
        }
        Statement stmt = connection.createStatement();
        logger.info("Get data of max time work to " + id + " node");
        ResultSet resultSet = stmt.executeQuery(sql);
        if (resultSet.next()) {
            Timestamp maxTime = resultSet.getTimestamp(1);
            sql = "update nodes set end_time = \'" + maxTime + "\' where id_node = " + id + ";";
            stmt = connection.createStatement();
            logger.info("Update end time to " + id + " node");
            logger.info(sql);
            stmt.executeUpdate(sql);
            return;
        }
        logger.warn("There is not any record of " + id + " node");
    }
}
