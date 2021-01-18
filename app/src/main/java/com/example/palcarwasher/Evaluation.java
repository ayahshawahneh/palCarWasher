package com.example.palcarwasher;

public class Evaluation {

    private String ProviderId;
    private String evaluationLevel;
    private String comment;


    public Evaluation() {
    }


    public Evaluation(String providerId, String evaluationLevel, String comment) {
        ProviderId = providerId;
        this.evaluationLevel = evaluationLevel;
        this.comment = comment;
    }


    public String getProviderId() {
        return ProviderId;
    }

    public void setProviderId(String providerId) {
        ProviderId = providerId;
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
