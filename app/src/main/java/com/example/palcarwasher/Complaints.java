package com.example.palcarwasher;

public class Complaints {

    private String complaintId;
    private boolean complaintDone;
    private String madeBy;
    private String orderId;


    public Complaints() {
    }


    public Complaints(String complaintId, boolean complaintDone, String madeBy, String orderId) {
        this.complaintId = complaintId;
        this.complaintDone = complaintDone;
        this.madeBy = madeBy;
        this.orderId = orderId;
    }


    public String getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(String complaintId) {
        this.complaintId = complaintId;
    }

    public boolean isComplaintDone() {
        return complaintDone;
    }

    public void setComplaintDone(boolean complaintDone) {
        this.complaintDone = complaintDone;
    }

    public String getMadeBy() {
        return madeBy;
    }

    public void setMadeBy(String madeBy) {
        this.madeBy = madeBy;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
