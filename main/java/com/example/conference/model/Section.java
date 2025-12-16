package com.example.conference.model;

public class Section {
    private long id;
    private long conferenceId;
    private String name;
    private String description;

    private Section(Builder builder) {
        this.id = builder.id;
        this.conferenceId = builder.conferenceId;
        this.name = builder.name;
        this.description = builder.description;
    }

    public Section() {}

    public long getId() { return id; }
    public long getConferenceId() { return conferenceId; }
    public String getName() { return name; }
    public String getDescription() { return description; }

    public void setId(long id) { this.id = id; }
    public void setConferenceId(long conferenceId) { this.conferenceId = conferenceId; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private long id;
        private long conferenceId;
        private String name;
        private String description;

        public Builder id(long id) { this.id = id; return this; }
        public Builder conferenceId(long conferenceId) { this.conferenceId = conferenceId; return this; }
        public Builder name(String name) { this.name = name; return this; }
        public Builder description(String description) { this.description = description; return this; }

        public Section build() { return new Section(this); }
    }
}