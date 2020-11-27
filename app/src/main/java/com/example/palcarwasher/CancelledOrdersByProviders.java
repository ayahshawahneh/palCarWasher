package com.example.palcarwasher;

public class CancelledOrdersByProviders {

    private String providerId;
    private String orderId;
    private String dateOfCancel;
    private String timeOfCancel;

    public CancelledOrdersByProviders() {
    }


    public CancelledOrdersByProviders(String providerId, String orderId, String dateOfCancel, String timeOfCancel) {
        this.providerId = providerId;
        this.orderId = orderId;
        this.dateOfCancel = dateOfCancel;
        this.timeOfCancel = timeOfCancel;
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
