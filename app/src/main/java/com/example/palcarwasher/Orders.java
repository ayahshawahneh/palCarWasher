package com.example.palcarwasher;

import java.util.List;

public class Orders {
    private String providerId;
    private String orderId;
    private String customerId;
    private String visaId;
    private String orderType;
    private String cleanAddress;
    private String paymentType;
    private String fullTime;
    private String status;
    private String totalPrice;
    private String vehicleSize;
    List<String> offerIds;



    public Orders() {
    }

    public Orders(String providerId, String orderId, String customerId, String visaId, String orderType, String cleanAddress, String paymentType, String fullTime, String status, String totalPrice, String vehicleSize, List<String> offerIds) {
        this.providerId = providerId;
        this.orderId = orderId;
        this.customerId = customerId;
        this.visaId = visaId;
        this.orderType = orderType;
        this.cleanAddress = cleanAddress;
        this.paymentType = paymentType;
        this.fullTime = fullTime;
        this.status = status;
        this.totalPrice = totalPrice;
        this.vehicleSize = vehicleSize;
        this.offerIds = offerIds;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
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

    public String getFullTime() {
        return fullTime;
    }

    public void setFullTime(String fullTime) {
        this.fullTime = fullTime;
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

    public List<String> getOfferIds() {
        return offerIds;
    }

    public void setOfferIds(List<String> offerIds) {
        this.offerIds = offerIds;
    }

    public String getVehicleSize() {
        return vehicleSize;
    }

    public void setVehicleSize(String vehicleSize) {
        this.vehicleSize = vehicleSize;
    }
}
