package com.example.palcarwasher;

public class ServicesInEachOrder {

    private String orderId;
    private String offerId;


    public ServicesInEachOrder() {
    }


    public ServicesInEachOrder(String orderId, String offerId) {
        this.orderId = orderId;
        this.offerId = offerId;
    }


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }
}
