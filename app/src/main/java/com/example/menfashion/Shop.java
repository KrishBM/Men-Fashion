package com.example.menfashion;

public class Shop {
    String sname, address, logo, shirtPrice, trouserPrice, sID,shopAvailability;

    Shop(){

    }

    public Shop(String sname, String address, String shirtPrice, String trouserPrice, String shopAvailability, String logo){
        this.sname=sname;
        this.address=address;
        this.shirtPrice=shirtPrice;
        this.trouserPrice=trouserPrice;
        this.shopAvailability=shopAvailability;
        this.logo=logo;
    }

    public String getShopAvailability() {
        return shopAvailability;
    }

    public void setShopAvailability(String shopAvailability) {
        this.shopAvailability = shopAvailability;
    }

    public String getsID() {
        return sID;
    }

    public void setsID(String sID) {
        this.sID = sID;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getShirtPrice() {
        return shirtPrice;
    }

    public void setShirtPrice(String shirtPrice) {
        this.shirtPrice = shirtPrice;
    }

    public String getTrouserPrice() {
        return trouserPrice;
    }

    public void setTrouserPrice(String trouserPrice) {
        this.trouserPrice = trouserPrice;
    }
}
