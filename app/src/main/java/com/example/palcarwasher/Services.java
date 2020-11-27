package com.example.palcarwasher;

public class Services {

    private String serviceId;
    private String name;


    public Services() {
    }


    public Services(String serviceId, String name) {
        this.serviceId = serviceId;
        this.name = name;
    }


    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
