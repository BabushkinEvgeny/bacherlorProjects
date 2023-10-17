package com.example.beerfactory.Entity;

public class Workers {
    String id;
    String surname;
    String salary;
    public Workers(){}

    public Workers(String id, String surname, String salary) {
        this.id = id;
        this.surname = surname;
        this.salary = salary;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }
}
