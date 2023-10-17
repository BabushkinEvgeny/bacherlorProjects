package com.example.otdel_kadrov.entity;

public class ApplicationList {
    String id;
    String id_examiner;
    String description;
    String status;

    public ApplicationList(){}

    public ApplicationList(String id, String id_examiner, String description, String status) {
        this.id = id;
        this.id_examiner = id_examiner;
        this.description = description;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_examiner() {
        return id_examiner;
    }

    public void setId_examiner(String id_examiner) {
        this.id_examiner = id_examiner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
