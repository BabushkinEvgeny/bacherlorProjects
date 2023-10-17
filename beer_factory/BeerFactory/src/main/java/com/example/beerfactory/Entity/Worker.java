package com.example.beerfactory.Entity;

public class Worker {
    private Integer worker_id;
    private String worker_name;
    private String worker_surname;
    private String worker_login;
    private String worker_password;
    private Integer worker_salary;
    private Integer worker_role;

    public Worker(){}

    public Worker(Integer worker_id, String worker_name, String worker_surname, String worker_login, String worker_password, Integer worker_salary, Integer worker_role) {
        this.worker_id = worker_id;
        this.worker_name = worker_name;
        this.worker_surname = worker_surname;
        this.worker_login = worker_login;
        this.worker_password = worker_password;
        this.worker_salary = worker_salary;
        this.worker_role = worker_role;
    }

    public Integer getWorker_id() {
        return worker_id;
    }

    public void setWorker_id(Integer worker_id) {
        this.worker_id = worker_id;
    }

    public String getWorker_name() {
        return worker_name;
    }

    public void setWorker_name(String worker_name) {
        this.worker_name = worker_name;
    }

    public String getWorker_surname() {
        return worker_surname;
    }

    public void setWorker_surname(String worker_surname) {
        this.worker_surname = worker_surname;
    }

    public String getWorker_login() {
        return worker_login;
    }

    public void setWorker_login(String worker_login) {
        this.worker_login = worker_login;
    }

    public String getWorker_password() {
        return worker_password;
    }

    public void setWorker_password(String worker_password) {
        this.worker_password = worker_password;
    }

    public Integer getWorker_salary() {
        return worker_salary;
    }

    public void setWorker_salary(Integer worker_salary) {
        this.worker_salary = worker_salary;
    }

    public Integer getWorker_role() {
        return worker_role;
    }

    public void setWorker_role(Integer worker_role) {
        this.worker_role = worker_role;
    }
}
