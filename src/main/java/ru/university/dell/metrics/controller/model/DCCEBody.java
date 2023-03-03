package ru.university.dell.metrics.controller.model;

import ru.university.dell.services.LoadType;

public class DCCEBody {
    private int[] id;
    private LoadType type;

    public int[] getId() {
        return id;
    }

    public void setId(int[] id) {
        this.id = id;
    }

    public LoadType getType() {
        return type;
    }

    public void setType(LoadType type) {
        this.type = type;
    }
}
