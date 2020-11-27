package com.example.palcarwasher;

public class DiscountInOffers {

    private String discountId;
    private String startDate;
    private String endDate;
    private String dicountPercentage;

    public DiscountInOffers() {
    }


    public DiscountInOffers(String discountId, String startDate, String endDate, String dicountPercentage) {
        this.discountId = discountId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.dicountPercentage = dicountPercentage;
    }

    public String getDiscountId() {
        return discountId;
    }

    public void setDiscountId(String discountId) {
        this.discountId = discountId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDicountPercentage() {
        return dicountPercentage;
    }

    public void setDicountPercentage(String dicountPercentage) {
        this.dicountPercentage = dicountPercentage;
    }



}
