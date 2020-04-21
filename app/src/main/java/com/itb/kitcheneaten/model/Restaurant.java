package com.itb.kitcheneaten.model;

import java.util.ArrayList;

public class Restaurant {

    private String name;
    private String address;
    private String telf;
    private Integer capacity;
    private String image;
    private ArrayList<Integer> schedule;
    private ArrayList<Reservation> reservations;

    public Restaurant() {
    }

    public Restaurant(String name, String address, String telf, Integer capacity, String image) {
        this.name = name;
        this.address = address;
        this.telf = telf;
        this.capacity = capacity;
        this.image = image;
    }

    public ArrayList<Integer> getSchedule() {
        return schedule;
    }

    public void setSchedule(ArrayList<Integer> schedule) {
        this.schedule = schedule;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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


    public ArrayList<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(ArrayList<Reservation> reservations) {
        this.reservations = reservations;
    }
}
