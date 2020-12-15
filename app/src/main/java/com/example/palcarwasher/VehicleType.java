package com.example.palcarwasher;

public class VehicleType {

    private String vehicleId;
    private String size;


    public VehicleType() {
    }

    public VehicleType(String vehicleId, String size) {
        this.vehicleId = vehicleId;
        this.size = size;
    }


    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }





    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
