package com.example.otdel_kadrov.entity;

public class ApplicationExamine {
    private String id;
    private String status;
    private String description;
    private String surname;
    private String purpose;

    public ApplicationExamine(String id, String status, String description, String surname, String purpose) {
        this.id = id;
        this.status = status;
        this.description = description;
        this.surname = surname;
        this.purpose = purpose;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
}
