package com.example.palcarwasher;

public class ServicesOfferedByServiceProviders {

    private String offerId;
    private String providerId;
    private String serviceName;
    private String vehicleName;
    private String description;
    private String price;
    private boolean discountOnOff;
    private String discountId;


    public ServicesOfferedByServiceProviders() {
    }
    public ServicesOfferedByServiceProviders(String offerId, String providerId, String serviceName, String vehicleName, String description, String price, boolean discountOnOff, String discountId) {
        this.offerId = offerId;
        this.providerId = providerId;
        this.serviceName = serviceName;
        this.vehicleName = vehicleName;
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

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
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

    public boolean getDiscountOnOff() {
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
