package ru.university.dell.metrics.controller;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.*;
import ru.university.dell.analyzer.DCCEValue;
import ru.university.dell.analyzer.SCEValue;
import ru.university.dell.analyzer.TCSValue;
import ru.university.dell.controller.model.TCSBody;
import ru.university.dell.database.DbConnector;
import ru.university.dell.model.*;
import ru.university.dell.controller.model.DCCEBody;
import ru.university.dell.controller.model.SCEBody;
import ru.university.dell.services.LoadType;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


@RestController
public class MetricController {
    private static final Logger logger = Logger.getLogger(MetricController.class);

    @GetMapping(value = "/metric/tcs", produces = "application/json")
    public Map<String, Object> getTcs(@RequestParam(name = "ids") int[] months, HttpServletResponse response) {
        if (months == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
        Map<String, Object> result = new HashMap<>();
        for (int month : months) {
            try {
                TCSValue tcsValue = new TCSValue(new TCS(month));
                Map<String, Object> tcsData = new HashMap<>();
                tcsData.put("result", tcsValue.getResult());
                tcsData.put("categories", tcsValue.getCategories());
                result.put(String.valueOf(month), tcsData);
            } catch (IllegalArgumentException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return null;
            } catch (IllegalStateException e) {
                response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
                return null;
            }
        }
        return result;
    }

    @GetMapping("/metric/sce")
    public double getSce(@RequestParam(name = "id") int id, @RequestParam(name = "type") LoadType type, HttpServletResponse response) {
        if (type == null || id == -1) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return -1;
        }
        logger.info(type);
        try {
            return new SCEValue(new SCE(id, type)).getResult();
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return -1;
        } catch (IllegalStateException e) {
            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            return -1;
        }
    }

    @GetMapping("/metric/dcce")
    @ResponseBody
    public double getDcce(@RequestParam(name = "ids") int[] ids, @RequestParam(name = "type") LoadType type,
                          HttpServletResponse response) {
        if (type == null || ids == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return -1;
        }
        ArrayList<Integer> arrayId = new ArrayList<>(ids.length);
        for (int i : ids) {
            arrayId.add(i);
        }
        try {
            return new DCCEValue(new DCCE(arrayId, type)).getResult();
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (IllegalStateException e) {
            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
        }
        return -1;
    }

    @GetMapping("/metric")
    public Map<String, Object> getMetricsName() {
        logger.info("get metrics name");
        return Collections.singletonMap("metrics", new String[]{MetricType.SCE.toString(), MetricType.TCS.toString(),
                MetricType.DCCE.toString()});
    }

    @GetMapping(value="/metric/nodes", produces = "application/json")
    public NodesInfo getNodes() {
        logger.info("Get nodes");
        try {
            return new NodesInfo();
        } catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
