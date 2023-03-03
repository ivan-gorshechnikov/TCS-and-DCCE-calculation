package ru.university.dell.metrics.controller.model;

import ru.university.dell.metrics.services.LoadType;

public class SCEBody {
    private int id = -1;
    private LoadType type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LoadType getType() {
        return type;
    }

    public void setType(LoadType type) {
        this.type = type;
    }
}
