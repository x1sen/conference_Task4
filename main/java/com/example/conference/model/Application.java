package com.example.conference.model;

public class Application {
    private long id;
    private long userId;
    private long sectionId;
    private String topic;
    private Status status;
    private String question;

    public enum Status {
        PENDING, APPROVED, REJECTED, WITHDRAWN
    }

    private Application(Builder builder) {
        this.id = builder.id;
        this.userId = builder.userId;
        this.sectionId = builder.sectionId;
        this.topic = builder.topic;
        this.status = builder.status;
        this.question = builder.question;
    }

    public Application() {}

    public long getId() { return id; }
    public long getUserId() { return userId; }
    public long getSectionId() { return sectionId; }
    public String getTopic() { return topic; }
    public Status getStatus() { return status; }
    public String getQuestion() { return question; }

    public void setId(long id) { this.id = id; }
    public void setUserId(long userId) { this.userId = userId; }
    public void setSectionId(long sectionId) { this.sectionId = sectionId; }
    public void setTopic(String topic) { this.topic = topic; }
    public void setStatus(Status status) { this.status = status; }
    public void setQuestion(String question) { this.question = question; }


    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private long id;
        private long userId;
        private long sectionId;
        private String topic;
        private Status status;
        private String question;

        public Builder id(long id) { this.id = id; return this; }
        public Builder userId(long userId) { this.userId = userId; return this; }
        public Builder sectionId(long sectionId) { this.sectionId = sectionId; return this; }
        public Builder topic(String topic) { this.topic = topic; return this; }
        public Builder status(Status status) { this.status = status; return this; }
        public Builder question(String question) { this.question = question; return this; }

        public Application build() { return new Application(this); }
    }
}