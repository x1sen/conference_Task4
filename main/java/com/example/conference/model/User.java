package com.example.conference.model;

public class User {
    private long id;
    private String email;
    private String passwordHash;
    private String firstName;
    private String lastName;
    private Role role;

    public enum Role {
        ADMIN, PARTICIPANT
    }


    private User(Builder builder) {
        this.id = builder.id;
        this.email = builder.email;
        this.passwordHash = builder.passwordHash;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.role = builder.role;
    }


    public User() {}

    public long getId() { return id; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public Role getRole() { return role; }


    public void setId(long id) { this.id = id; }
    public void setEmail(String email) { this.email = email; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setRole(Role role) { this.role = role; }


    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private long id;
        private String email;
        private String passwordHash;
        private String firstName;
        private String lastName;
        private Role role;

        public Builder id(long id) { this.id = id; return this; }
        public Builder email(String email) { this.email = email; return this; }
        public Builder passwordHash(String passwordHash) { this.passwordHash = passwordHash; return this; }
        public Builder firstName(String firstName) { this.firstName = firstName; return this; }
        public Builder lastName(String lastName) { this.lastName = lastName; return this; }
        public Builder role(Role role) { this.role = role; return this; }

        public User build() {
            return new User(this);
        }
    }
}