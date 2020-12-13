package com.example.palcarwasher;

public class VehicleType {

    private String vehicleId;
    private String serviceName;
    private String size;


    public VehicleType() {
    }

    public VehicleType(String vehicleId, String serviceName, String size) {
        this.vehicleId = vehicleId;
        this.serviceName = serviceName;
        this.size = size;
    }


    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
