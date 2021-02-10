package com.example.palcarwasher;

public class ServiceProviderAndAvgPrice {

    private ServiceProvider sp;
    private double avg;


    public ServiceProviderAndAvgPrice() {
    }


    public ServiceProviderAndAvgPrice(ServiceProvider sp, double avg) {
        this.sp = sp;
        this.avg = avg;
    }


    public ServiceProvider getSp() {
        return sp;
    }

    public void setSp(ServiceProvider sp) {
        this.sp = sp;
    }

    public double getAvg() {
        return avg;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }
}
