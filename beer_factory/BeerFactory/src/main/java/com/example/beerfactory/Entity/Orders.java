package com.example.beerfactory.Entity;

public class Orders {
    private String id;
    private String cost;
    private String status;

    public Orders(){}

    public Orders(String id, String cost, String status) {
        this.id = id;
        this.cost = cost;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
