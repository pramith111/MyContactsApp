package com.mycontacts.model;

public class PhoneNumber {
    private String number;
    private String type; // Mobile, Home, Work

    public PhoneNumber(String number, String type) {
        this.number = number;
        this.type = type;
    }

    public String getNumber() { return number; }
    public String getType() { return type; }
    public void setNumber(String number) { this.number = number; }
    public void setType(String type) { this.type = type; }

    @Override
    public String toString() {
        return type + ": " + number;
    }
}