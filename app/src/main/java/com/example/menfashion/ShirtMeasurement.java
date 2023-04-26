package com.example.menfashion;

public class ShirtMeasurement {
    String id,clothType,currentCustomerID;
    String chest, waist, bottomHem, frontLen, sleeveLen, sleeveWid, centerBack, shoulderWid, cuff, collarSize;


    ShirtMeasurement(){

    }

    public ShirtMeasurement(String clothType, String currentCustomerID, String chest, String waist, String bottomHem, String frontLen, String sleeveLen, String sleeveWid, String centerBack, String shoulderWid, String cuff, String collarSize) {
        this.clothType = clothType;
        this.currentCustomerID = currentCustomerID;
        this.chest = chest;
        this.waist = waist;
        this.bottomHem = bottomHem;
        this.frontLen = frontLen;
        this.sleeveLen = sleeveLen;
        this.sleeveWid = sleeveWid;
        this.centerBack = centerBack;
        this.shoulderWid = shoulderWid;
        this.cuff = cuff;
        this.collarSize = collarSize;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChest() {
        return chest;
    }

    public void setChest(String chest) {
        this.chest = chest;
    }

    public String getWaist() {
        return waist;
    }

    public void setWaist(String waist) {
        this.waist = waist;
    }

    public String getBottomHem() {
        return bottomHem;
    }

    public void setBottomHem(String bottomHem) {
        this.bottomHem = bottomHem;
    }

    public String getFrontLen() {
        return frontLen;
    }

    public void setFrontLen(String frontLen) {
        this.frontLen = frontLen;
    }

    public String getSleeveLen() {
        return sleeveLen;
    }

    public void setSleeveLen(String sleeveLen) {
        this.sleeveLen = sleeveLen;
    }

    public String getSleeveWid() {
        return sleeveWid;
    }

    public void setSleeveWid(String sleeveWid) {
        this.sleeveWid = sleeveWid;
    }

    public String getCenterBack() {
        return centerBack;
    }

    public void setCenterBack(String centerBack) {
        this.centerBack = centerBack;
    }

    public String getShoulderWid() {
        return shoulderWid;
    }

    public void setShoulderWid(String shoulderWid) {
        this.shoulderWid = shoulderWid;
    }

    public String getCuff() {
        return cuff;
    }

    public void setCuff(String cuff) {
        this.cuff = cuff;
    }

    public String getCollarSize() {
        return collarSize;
    }

    public void setCollarSize(String collarSize) {
        this.collarSize = collarSize;
    }

    public String getClothType() {
        return clothType;
    }

    public void setClothType(String clothType) {
        this.clothType = clothType;
    }

    public String getCurrentCustomerID() {
        return currentCustomerID;
    }

    public void setCurrentCustomerID(String currentCustomerID) {
        this.currentCustomerID = currentCustomerID;
    }
}
