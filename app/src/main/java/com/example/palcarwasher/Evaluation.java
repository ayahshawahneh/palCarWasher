package com.example.palcarwasher;

public class Evaluation {

    private String orderId;
    private String evaluationLevel;
    private String comment;


    public Evaluation() {
    }


    public Evaluation(String orderId, String evaluationLevel, String comment) {
        this.orderId = orderId;
        this.evaluationLevel = evaluationLevel;
        this.comment = comment;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getEvaluationLevel() {
        return evaluationLevel;
    }

    public void setEvaluationLevel(String evaluationLevel) {
        this.evaluationLevel = evaluationLevel;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
