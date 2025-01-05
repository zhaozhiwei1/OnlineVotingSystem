package org.example.onlinevotingsystem.model;

import java.sql.Timestamp;

public class Feedback {
    private int id;
    private String feedbackType;
    private String feedbackContent;
    private String contactPhone;
    private String feedbackUser;
    private Timestamp feedbackTime;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFeedbackType() {
        return feedbackType;
    }

    public void setFeedbackType(String feedbackType) {
        this.feedbackType = feedbackType;
    }

    public String getFeedbackContent() {
        return feedbackContent;
    }

    public void setFeedbackContent(String feedbackContent) {
        this.feedbackContent = feedbackContent;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getFeedbackUser() {
        return feedbackUser;
    }

    public void setFeedbackUser(String feedbackUser) {
        this.feedbackUser = feedbackUser;
    }

    public Timestamp getFeedbackTime() {
        return feedbackTime;
    }

    public void setFeedbackTime(Timestamp feedbackTime) {
        this.feedbackTime = feedbackTime;
    }
}