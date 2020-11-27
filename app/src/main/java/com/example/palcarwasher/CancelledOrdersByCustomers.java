package com.example.palcarwasher;

public class CancelledOrdersByCustomers {

    private String orderId;
    private String customerId;
    private String cutAmount;
    private String dateOfCancel;
    private String timeOfCancel;


    public CancelledOrdersByCustomers() {
    }

    public CancelledOrdersByCustomers(String orderId, String customerId, String cutAmount, String dateOfCancel, String timeOfCancel) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.cutAmount = cutAmount;
        this.dateOfCancel = dateOfCancel;
        this.timeOfCancel = timeOfCancel;
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

    public String getCutAmount() {
        return cutAmount;
    }

    public void setCutAmount(String cutAmount) {
        this.cutAmount = cutAmount;
    }

    public String getDateOfCancel() {
        return dateOfCancel;
    }

    public void setDateOfCancel(String dateOfCancel) {
        this.dateOfCancel = dateOfCancel;
    }

    public String getTimeOfCancel() {
        return timeOfCancel;
    }

    public void setTimeOfCancel(String timeOfCancel) {
        this.timeOfCancel = timeOfCancel;
    }
}
