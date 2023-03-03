package ru.university.dell.metrics.model;

import ru.university.dell.database.DbConnector;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import ru.university.dell.services.LoadType;

import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;

public class SCE implements Metric {

    private static final Logger logger = Logger.getLogger(SCE.class);

    private final ArrayList<Pair> data;
    private final int id_node;
    private final LoadType type;

    public SCE(int id_node, LoadType type) {
        this.id_node = id_node;
        this.type = type;
        this.data = collectData();
    }

    public ArrayList<Pair> getData() {
        return data;
    }

    private ArrayList<Pair> collectData() {
        ArrayList<Pair> data = new ArrayList<>();
        try {
            DbConnector db = new DbConnector();
            Connection connection = db.openConnection();
            ResultSet resultSet = getResultSetData(connection);
            assert resultSet != null;
            logger.info("Data received");
            while (resultSet.next()) {
                data.add(new Pair(resultSet.getDouble(1), resultSet.getTimestamp(2)));
            }
            logger.info("Data entered");
            db.closeConnection();
            return data;
        } catch (FileNotFoundException e) {
            logger.error("Cannot find config file to database" + e);
        } catch (SQLException e) {
            logger.error("Cannot open connection to database" + e);
        }
        return null;
    }

    private @NotNull String prepareQuery() {
        logger.info("Choose " + type.name());
        switch (type) {
            case CPU: {
                return "Select \"CPU_usage\", timestamp from node_load_dump where id_node = " + id_node;
            }
            case RAM: {
                return "Select \"RAM_usage\", timestamp from node_load_dump where id_node = " + id_node;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
    }

    private ResultSet getResultSetData(Connection connection) {
        String sql = prepareQuery();
        try {
            logger.info("Start get data");
            Statement stmt = connection.createStatement();
            try {
                return stmt.executeQuery(sql);
            } catch (SQLException e) {
                logger.error("Cannot execute query" + e);
            }
        } catch (SQLException e) {
            logger.error("Cannot make statement" + e);
        }
        return null;
    }

    @Override
    public String toString() {
        return "SCE" +
                "{id_node=" + id_node +
                ", loadType=" + type +
                ", data=" + data.toString() +
                "}";
    }
}
