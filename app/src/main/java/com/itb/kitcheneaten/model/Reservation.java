package com.itb.kitcheneaten.model;

import java.util.Date;

public class Reservation {
    private Date date;
    private Integer nDinners;
    private String name;
    private String telf;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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
