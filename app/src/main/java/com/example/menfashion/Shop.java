package com.example.menfashion;

public class Shop {
    String sname, address, logo, shirtPrice, trouserPrice, sID;

    Shop(){

    }

    public Shop(String sname,String address, String logo, String shirtPrice, String trouserPrice){
        this.sname=sname;
        this.address=address;
        this.logo=logo;
        this.shirtPrice=shirtPrice;
        this.trouserPrice=trouserPrice;
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
