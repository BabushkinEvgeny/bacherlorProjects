package com.example.beerfactory.Entity;

public class Beer {
    private String beer_id;
    private String name;
    private String description;
    private String type_name;
    private String value;

    public Beer(){}

    public Beer(String beer_id, String name, String description, String type_name, String value) {
        this.beer_id = beer_id;
        this.name = name;
        this.description = description;
        this.type_name = type_name;
        this.value = value;
    }



    public String getBeer_id() {
        return beer_id;
    }

    public void setBeer_id(String beer_id) {
        this.beer_id = beer_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
