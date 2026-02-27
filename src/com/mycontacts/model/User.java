package com.mycontacts.model;

import java.util.UUID;

public abstract class User {
    private String userId;
    private String name;
    private String email;
    private String passwordHash;

    public User(String name, String email, String passwordHash) {
        this.userId = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    // Getters
    public String getUserId() { return userId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public abstract String getUserType();

    @Override
    public String toString() {
        return "User[" + getUserType() + "] - Name: " + name + ", Email: " + email;
    }
}