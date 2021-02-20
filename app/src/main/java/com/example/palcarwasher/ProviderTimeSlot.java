package com.example.palcarwasher;

public class ProviderTimeSlot {

    ServiceProvider serviceProvider;
    String time;


    public ProviderTimeSlot() {
    }

    public ProviderTimeSlot(ServiceProvider serviceProvider, String time) {
        this.serviceProvider = serviceProvider;
        this.time = time;
    }


    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
