package com.example.palcarwasher;

public class WorkingTimeOnFriday {

    private String prviderId;
    private String from;
    private String to;
    private String partTimeFrom;
    private String partTimeTo;

    public WorkingTimeOnFriday() {
    }

    public WorkingTimeOnFriday(String prviderId, String from, String to, String partTimeFrom, String partTimeTo) {
        this.prviderId = prviderId;
        this.from = from;
        this.to = to;
        this.partTimeFrom = partTimeFrom;
        this.partTimeTo = partTimeTo;
    }

    public String getPrviderId() {
        return prviderId;
    }

    public void setPrviderId(String prviderId) {
        this.prviderId = prviderId;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getPartTimeFrom() {
        return partTimeFrom;
    }

    public void setPartTimeFrom(String partTimeFrom) {
        this.partTimeFrom = partTimeFrom;
    }

    public String getPartTimeTo() {
        return partTimeTo;
    }

    public void setPartTimeTo(String partTimeTo) {
        this.partTimeTo = partTimeTo;
    }
}
