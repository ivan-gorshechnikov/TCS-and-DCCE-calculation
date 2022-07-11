package ru.university.dell.model;

import ru.university.dell.services.LoadType;

import java.util.ArrayList;

public class DCCE implements Metric {
    private final ArrayList<SCE> data;
    private final LoadType type;

    public DCCE(ArrayList<Integer> id_nodes, LoadType type) {
        this.type = type;
        this.data = new ArrayList<>();
        for (Integer id_node : id_nodes) {
            this.data.add(new SCE(id_node, type));
        }
    }

    public ArrayList<SCE> getData() {
        return data;
    }

    public LoadType getType() {
        return type;
    }

}
