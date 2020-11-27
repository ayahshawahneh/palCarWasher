package com.example.palcarwasher;

public class DiscountInOrders {

    private String providerId;
    private String customerId;
    private String discountPercentage;
    private String freeOfferId;
    private boolean flagDoneOrNot;

    public DiscountInOrders() {
    }

    public DiscountInOrders(String providerId, String customerId, String discountPercentage, String freeOfferId, boolean flagDoneOrNot) {
        this.providerId = providerId;
        this.customerId = customerId;
        this.discountPercentage = discountPercentage;
        this.freeOfferId = freeOfferId;
        this.flagDoneOrNot = flagDoneOrNot;
    }


    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(String discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public String getFreeOfferId() {
        return freeOfferId;
    }

    public void setFreeOfferId(String freeOfferId) {
        this.freeOfferId = freeOfferId;
    }

    public boolean isFlagDoneOrNot() {
        return flagDoneOrNot;
    }

    public void setFlagDoneOrNot(boolean flagDoneOrNot) {
        this.flagDoneOrNot = flagDoneOrNot;
    }
}
