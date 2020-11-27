package com.example.palcarwasher;

public class ServicesOfferedByServiceProviders {

    private String offerId;
    private String serviceId;
    private String providerId;
    private String vehicleId;
    private String description;
    private String price;
    private boolean discountOnOff;
    private String discountId;


    public ServicesOfferedByServiceProviders() {
    }


    public ServicesOfferedByServiceProviders(String offerId, String serviceId, String providerId, String vehicleId, String description, String price, boolean discountOnOff, String discountId) {
        this.offerId = offerId;
        this.serviceId = serviceId;
        this.providerId = providerId;
        this.vehicleId = vehicleId;
        this.description = description;
        this.price = price;
        this.discountOnOff = discountOnOff;
        this.discountId = discountId;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public boolean isDiscountOnOff() {
        return discountOnOff;
    }

    public void setDiscountOnOff(boolean discountOnOff) {
        this.discountOnOff = discountOnOff;
    }

    public String getDiscountId() {
        return discountId;
    }

    public void setDiscountId(String discountId) {
        this.discountId = discountId;
    }
}
