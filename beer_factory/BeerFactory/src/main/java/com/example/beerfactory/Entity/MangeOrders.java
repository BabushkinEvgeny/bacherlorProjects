package com.example.beerfactory.Entity;

public class MangeOrders {
    String id;
    String customer;
    String worker;
    String status;
    public MangeOrders(){}

    public MangeOrders(String id, String customer, String worker, String status) {
        this.id = id;
        this.customer = customer;
        this.worker = worker;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
