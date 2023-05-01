package com.example.menfashion;

public class Order {
    String orderID,productID,fabricPrice,tailorCharge,measurementID,clothType,shopId,customerID,orderedDate,deliveryDate,orderStatus;

    Order(){

    }

    public Order(String productID, String fabricPrice, String tailorCharge, String measurementID, String clothType, String shopId, String customerID, String orderedDate, String deliveryDate, String orderStatus) {
        this.productID = productID;
        this.fabricPrice = fabricPrice;
        this.tailorCharge = tailorCharge;
        this.measurementID = measurementID;
        this.clothType = clothType;
        this.shopId = shopId;
        this.customerID = customerID;
        this.orderedDate = orderedDate;
        this.deliveryDate = deliveryDate;
        this.orderStatus = orderStatus;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getFabricPrice() {
        return fabricPrice;
    }

    public void setFabricPrice(String fabricPrice) {
        this.fabricPrice = fabricPrice;
    }

    public String getTailorCharge() {
        return tailorCharge;
    }

    public void setTailorCharge(String tailorCharge) {
        this.tailorCharge = tailorCharge;
    }

    public String getMeasurementID() {
        return measurementID;
    }

    public void setMeasurementID(String measurementID) {
        this.measurementID = measurementID;
    }

    public String getClothType() {
        return clothType;
    }

    public void setClothType(String clothType) {
        this.clothType = clothType;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getOrderedDate() {
        return orderedDate;
    }

    public void setOrderedDate(String orderedDate) {
        this.orderedDate = orderedDate;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
