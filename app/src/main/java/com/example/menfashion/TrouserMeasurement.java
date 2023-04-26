package com.example.menfashion;

public class TrouserMeasurement {
    String id,clothType,currentCustomerID;
    String waistBelt, frontRise, inseam, outseam, seat, thigh, knee, heap, backRise, hem;


    TrouserMeasurement(){

    }

    public TrouserMeasurement(String clothType, String currentCustomerID, String waistBelt, String frontRise, String inseam, String outseam, String seat, String thigh, String knee, String heap, String backRise, String hem) {
        this.clothType = clothType;
        this.currentCustomerID = currentCustomerID;
        this.waistBelt = waistBelt;
        this.frontRise = frontRise;
        this.inseam = inseam;
        this.outseam = outseam;
        this.seat = seat;
        this.thigh = thigh;
        this.knee = knee;
        this.heap = heap;
        this.backRise = backRise;
        this.hem = hem;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWaistBelt() {
        return waistBelt;
    }

    public void setWaistBelt(String waistBelt) {
        this.waistBelt = waistBelt;
    }

    public String getFrontRise() {
        return frontRise;
    }

    public void setFrontRise(String frontRise) {
        this.frontRise = frontRise;
    }

    public String getInseam() {
        return inseam;
    }

    public void setInseam(String inseam) {
        this.inseam = inseam;
    }

    public String getOutseam() {
        return outseam;
    }

    public void setOutseam(String outseam) {
        this.outseam = outseam;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public String getThigh() {
        return thigh;
    }

    public void setThigh(String thigh) {
        this.thigh = thigh;
    }

    public String getKnee() {
        return knee;
    }

    public void setKnee(String knee) {
        this.knee = knee;
    }

    public String getHeap() {
        return heap;
    }

    public void setHeap(String heap) {
        this.heap = heap;
    }

    public String getBackRise() {
        return backRise;
    }

    public void setBackRise(String backRise) {
        this.backRise = backRise;
    }

    public String getHem() {
        return hem;
    }

    public void setHem(String hem) {
        this.hem = hem;
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

