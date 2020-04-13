package com.itb.kitcheneaten.model;

public class Restaurant {

    private String name;
    private String address;
    private String tel;
    private int nTables;

    public Restaurant() {
    }

    public Restaurant(String name, String address, String tel, int nTables) {
        this.name = name;
        this.address = address;
        this.tel = tel;
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

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public int getnTables() {
        return nTables;
    }

    public void setnTables(int nTables) {
        this.nTables = nTables;
    }
}
