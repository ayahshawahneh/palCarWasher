package com.example.palcarwasher;

public class Orders {

    private String orderId;
    private String customerId;
    private String visaId;
    private String orderType;
    private String cleanAddress;
    private String paymentType;
    private String date;
    private String time;
    private String status;
    private String totalPrice;


    public Orders() {
    }


    public Orders(String orderId, String customerId, String visaId, String orderType, String cleanAddress, String paymentType, String date, String time, String status, String totalPrice) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.visaId = visaId;
        this.orderType = orderType;
        this.cleanAddress = cleanAddress;
        this.paymentType = paymentType;
        this.date = date;
        this.time = time;
        this.status = status;
        this.totalPrice = totalPrice;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getVisaId() {
        return visaId;
    }

    public void setVisaId(String visaId) {
        this.visaId = visaId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getCleanAddress() {
        return cleanAddress;
    }

    public void setCleanAddress(String cleanAddress) {
        this.cleanAddress = cleanAddress;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }
}
