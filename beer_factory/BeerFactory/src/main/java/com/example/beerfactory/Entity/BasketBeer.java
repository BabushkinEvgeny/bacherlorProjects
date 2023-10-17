package com.example.beerfactory.Entity;

public class BasketBeer {
    String beer_bottle_id;
    String name;

    String amount;
    String total_price;

    public BasketBeer(){}

    public BasketBeer(String beer_bottle_id, String name, String amount, String total_price) {
        this.beer_bottle_id = beer_bottle_id;
        this.name = name;
        this.amount = amount;
        this.total_price = total_price;
    }

    public String getBeer_bottle_id() {
        return beer_bottle_id;
    }

    public void setBeer_bottle_id(String beer_bottle_id) {
        this.beer_bottle_id = beer_bottle_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }
}
