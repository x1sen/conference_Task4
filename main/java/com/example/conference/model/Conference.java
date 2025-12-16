package com.example.conference.model;

import java.time.LocalDateTime;

public class Conference {
    private long id;
    private String name;
    private String description;
    private LocalDateTime date;
    private String location;

    private Conference(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.description = builder.description;
        this.date = builder.date;
        this.location = builder.location;
    }

    public Conference() {}

    public long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public LocalDateTime getDate() { return date; }
    public String getLocation() { return location; }

    public void setId(long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setDate(LocalDateTime date) { this.date = date; }
    public void setLocation(String location) { this.location = location; }


    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private long id;
        private String name;
        private String description;
        private LocalDateTime date;
        private String location;

        public Builder id(long id) { this.id = id; return this; }
        public Builder name(String name) { this.name = name; return this; }
        public Builder description(String description) { this.description = description; return this; }
        public Builder date(LocalDateTime date) { this.date = date; return this; }
        public Builder location(String location) { this.location = location; return this; }

        public Conference build() { return new Conference(this); }
    }
}