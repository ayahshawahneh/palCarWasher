package com.example.palcarwasher;

public class CustomerAddresses {

    private String customerId;
    private String addressId;
    private String location;
    private String description;




    public CustomerAddresses() {
    }


    public CustomerAddresses(String customerId, String addressId, String location, String description) {
        this.customerId = customerId;
        this.addressId = addressId;
        this.location = location;
        this.description = description;
    }


    public String getCustomerId() {
        return customerId;
    }

    public String getAddressId() {
        return addressId;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
