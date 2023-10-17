package com.example.beerfactory.Entity;

public class Customer {
    private Integer customer_id;
    private String customer_name;
    private String customer_surname;
    private String customer_login;
    private String customer_password;

    public Customer(){}
    public Customer(Integer customer_id, String customer_name, String customer_surname, String customer_login, String customer_password) {
        this.customer_id = customer_id;
        this.customer_name = customer_name;
        this.customer_surname = customer_surname;
        this.customer_login = customer_login;
        this.customer_password = customer_password;
    }

    public Integer getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Integer customer_id) {
        this.customer_id = customer_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_surname() {
        return customer_surname;
    }

    public void setCustomer_surname(String customer_surname) {
        this.customer_surname = customer_surname;
    }

    public String getCustomer_login() {
        return customer_login;
    }

    public void setCustomer_login(String customer_login) {
        this.customer_login = customer_login;
    }

    public String getCustomer_password() {
        return customer_password;
    }

    public void setCustomer_password(String customer_password) {
        this.customer_password = customer_password;
    }
}
