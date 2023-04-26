package com.example.menfashion;

public class User {
    String email, name, number, password, role, ShopID, id, measurementShirtID, measurementTrouserID;

    User(){

    }

    public User(String email, String name, String number, String password, String role) {
        this.email = email;
        this.name = name;
        this.number = number;
        this.password = password;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getShopID() {
        return ShopID;
    }

    public void setShopID(String shopID) {
        ShopID = shopID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMeasurementShirtID() {
        return measurementShirtID;
    }

    public void setMeasurementShirtID(String measurementShirtID) {
        this.measurementShirtID = measurementShirtID;
    }

    public String getMeasurementTrouserID() {
        return measurementTrouserID;
    }

    public void setMeasurementTrouserID(String measurementTrouserID) {
        this.measurementTrouserID = measurementTrouserID;
    }
}

