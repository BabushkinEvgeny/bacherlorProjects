package com.example.otdel_kadrov.entity;

public class Worker {
    private String surname;
    private String id;
    private String salary;
    public Worker(){}

    public Worker(String surname, String id, String salary) {
        this.surname = surname;
        this.id = id;
        this.salary = salary;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }
}
