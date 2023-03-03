package ru.university.dell.metrics.model;

import ru.university.dell.metrics.database.DbConnector;

import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class NodesInfo {
    private final Map<String, Object> nodesInfo;

    public NodesInfo() throws FileNotFoundException, SQLException {
        ResultSet resultSet = new DbConnector().getNodesInfo();
        Map<String, Object> result = new HashMap<>();
        while (resultSet.next()) {
            result.put(resultSet.getString(1), new HashMap<>() {{
                put("start_time", resultSet.getTimestamp(3));
                put("end_time", resultSet.getTimestamp(4));
            }});
        }
        this.nodesInfo = result;
    }

    public Map<String, Object> getNodesInfo() {
        return nodesInfo;
    }
}
