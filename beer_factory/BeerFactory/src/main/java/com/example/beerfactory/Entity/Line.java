package com.example.beerfactory.Entity;

public class Line {
    String id;
    String status;
    String start;
    String finish;
    public Line(){}

    public Line(String id, String status, String start, String finish) {
        this.id = id;
        this.status = status;
        this.start = start;
        this.finish = finish;
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

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getFinish() {
        return finish;
    }

    public void setFinish(String finish) {
        this.finish = finish;
    }
}
