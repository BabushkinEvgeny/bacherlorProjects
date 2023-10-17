package com.example.otdel_kadrov.entity;

public class Employee {

    private String name;
    private String surname;
    private String login;
    private String password;
    private int id_role;
    private String snils;
    private Integer salary;
    private int id_status;
    private Integer working_hours;
    private int id_position;

    public Employee(){};

    public Employee(String name, String surname, String login, String password, int id_role, String snils, Integer salary, int id_status, Integer working_hours, int id_position) {
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.id_role = id_role;
        this.snils = snils;
        this.salary = salary;
        this.id_status = id_status;
        this.working_hours = working_hours;
        this.id_position = id_position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId_role() {
        return id_role;
    }

    public void setId_role(int id_role) {
        this.id_role = id_role;
    }

    public String getSnils() {
        return snils;
    }

    public void setSnils(String snils) {
        this.snils = snils;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public int getId_status() {
        return id_status;
    }

    public void setId_status(int id_status) {
        this.id_status = id_status;
    }

    public Integer getWorking_hours() {
        return working_hours;
    }

    public void setWorking_hours(Integer working_hours) {
        this.working_hours = working_hours;
    }

    public int getId_position() {
        return id_position;
    }

    public void setId_position(int id_position) {
        this.id_position = id_position;
    }
}
