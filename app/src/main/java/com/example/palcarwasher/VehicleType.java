package com.example.palcarwasher;

public class VehicleType {

    private String vehicleId;
    private String name;
    private String size;

    public VehicleType() {
    }


    public VehicleType(String vehicleId, String name, String size) {
        this.vehicleId = vehicleId;
        this.name = name;
        this.size = size;
    }


    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
