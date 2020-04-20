package com.itb.kitcheneaten.model;

public class Reservation {
    private String date;
    private Integer nDinners;
    private String name;
    private String telf;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getnDinners() {
        return nDinners;
    }

    public void setnDinners(Integer nDinners) {
        this.nDinners = nDinners;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelf() {
        return telf;
    }

    public void setTelf(String telf) {
        this.telf = telf;
    }
}
