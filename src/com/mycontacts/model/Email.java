package com.mycontacts.model;

public class Email {
    private String address;
    private String type; // Personal, Work

    public Email(String address, String type) {
        this.address = address;
        this.type = type;
    }

    public String getAddress() { return address; }
    public String getType() { return type; }
    public void setAddress(String address) { this.address = address; }
    public void setType(String type) { this.type = type; }

    @Override
    public String toString() {
        return type + ": " + address;
    }
}