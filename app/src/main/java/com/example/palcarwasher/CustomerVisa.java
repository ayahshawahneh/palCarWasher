package com.example.palcarwasher;

public class CustomerVisa {

    private String visaId;
    private String customerId;
    private String CVC;
    private String MM_yy;

    public CustomerVisa() {
    }

    public CustomerVisa(String visaId, String customerId, String CVC, String MM_yy) {
        this.visaId = visaId;
        this.customerId = customerId;
        this.CVC = CVC;
        this.MM_yy = MM_yy;
    }

    public String getVisaId() {
        return visaId;
    }

    public void setVisaId(String visaId) {
        this.visaId = visaId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCVC() {
        return CVC;
    }

    public void setCVC(String CVC) {
        this.CVC = CVC;
    }

    public String getMM_yy() {
        return MM_yy;
    }

    public void setMM_yy(String MM_yy) {
        this.MM_yy = MM_yy;
    }
}
