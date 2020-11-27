package com.example.palcarwasher;

public class Judgment {

    private String complaintId;
    private String adminId;
    private String decision;
    private String date;


    public Judgment() {
    }


    public Judgment(String complaintId, String adminId, String decision, String date) {
        this.complaintId = complaintId;
        this.adminId = adminId;
        this.decision = decision;
        this.date = date;
    }

    public String getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(String complaintId) {
        this.complaintId = complaintId;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
