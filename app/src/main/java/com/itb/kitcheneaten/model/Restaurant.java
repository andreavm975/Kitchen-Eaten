package com.itb.kitcheneaten.model;

public class Restaurant {

    private String name;
    private String address;
    private String telf;
    private Integer nTables;

    public Restaurant() {
    }

    public Restaurant(String name, String address, String telf, int nTables) {
        this.name = name;
        this.address = address;
        this.telf = telf;
        this.nTables = nTables;
    }

    public String getName() {
        return name;
    }

    public void setName(String nombre) {
        this.name = nombre;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelf() {
        return telf;
    }

    public void setTelf(String telf) {
        this.telf = telf;
    }

    public int getnTables() {
        return nTables;
    }

    public void setnTables(int nTables) {
        this.nTables = nTables;
    }
}
