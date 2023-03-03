package ru.university.dell.metrics.model;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;
import ru.university.dell.database.DbConnector;

import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NodesInfo {
    private static final Logger logger = Logger.getLogger(NodesInfo.class);

    private final Map<String, Object> nodesInfo;

    public NodesInfo() throws FileNotFoundException, SQLException {
        DbConnector dbConnector = new DbConnector();
        ResultSet resultSet = dbConnector.getNodesInfo();
        Map<String, Object> result = new HashMap<>();

        int counter = 0;
        while (resultSet.next()) {
            if (resultSet.getTimestamp(4) == null) {
                dbConnector.modifyEndTime(resultSet.getInt(1));
                counter++;
            }
        }
        resultSet.beforeFirst();
        if (counter != 0) {
            resultSet = dbConnector.getNodesInfo();
        }
        while (resultSet.next()) {
            ResultSet finalResultSet = resultSet;
            result.put(resultSet.getString(1), new HashMap<>() {{
                put("start_time", finalResultSet.getTimestamp(3));
                put("end_time", finalResultSet.getTimestamp(4));
            }});
        }
        this.nodesInfo = result;
    }

    public Map<String, Object> getNodesInfo() {
        return nodesInfo;
    }
}
