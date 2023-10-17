package com.example.otdel_kadrov.entity;

public class JobApplicationOutput {
    private String idJobApplication;
    private String name;
    private String status;

    public JobApplicationOutput(){}

    public JobApplicationOutput(String idJobApplication, String name, String status) {
        this.idJobApplication = idJobApplication;
        this.name = name;
        this.status = status;
    }

    public String getIdJobApplication() {
        return idJobApplication;
    }

    public void setIdJobApplication(String idJobApplication) {
        this.idJobApplication = idJobApplication;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
