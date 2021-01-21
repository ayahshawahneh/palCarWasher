package com.example.palcarwasher;

import java.util.List;

public class ServicesInEachOrder {

    private String orderId;
    private List<String> offerId;


    public ServicesInEachOrder() {
    }

    public ServicesInEachOrder(String orderId, List<String> offerId) {
        this.orderId = orderId;
        this.offerId = offerId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public List<String> getOfferId() {
        return offerId;
    }

    public void setOfferId(List<String> offerId) {
        this.offerId = offerId;
    }
}
