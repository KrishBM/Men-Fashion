package com.example.menfashion;

public class Product {

    String id,fabricType, fabricColor, fabricImage, fabricPrice, clothType, fabricAvailable,currentTailorID;

    Product(){

    }

    public Product(String fabricType,String fabricColor, String fabricImage, String fabricPrice, String clothType, String fabricAvailable, String currentTailorID){
        this.fabricType=fabricType;
        this.fabricColor=fabricColor;
        this.fabricImage=fabricImage;
        this.fabricPrice=fabricPrice;
        this.clothType=clothType;
        this.fabricAvailable=fabricAvailable;
        this.currentTailorID=currentTailorID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClothType() { return clothType; }

    public void setClothType(String clothType) {
        this.clothType = clothType;
    }

    public String getFabricType() {
        return fabricType;
    }

    public void setFabricType(String fabricType) {
        this.fabricType = fabricType;
    }

    public String getFabricColor() {
        return fabricColor;
    }

    public void setFabricColor(String fabricColor) {
        this.fabricColor = fabricColor;
    }

    public String getFabricImage() {
        return fabricImage;
    }

    public void setFabricImage(String fabricImage) {
        this.fabricImage = fabricImage;
    }

    public String getFabricPrice() {
        return fabricPrice;
    }

    public void setFabricPrice(String fabricPrice) {
        this.fabricPrice = fabricPrice;
    }

    public String getFabricAvailable() {
        return fabricAvailable;
    }

    public void setFabricAvailable(String fabricAvailable) {
        this.fabricAvailable = fabricAvailable;
    }

    public String getCurrentTailorID() {
        return currentTailorID;
    }

    public void setCurrentTailorID(String currentTailorID) {
        this.currentTailorID = currentTailorID;
    }
}
