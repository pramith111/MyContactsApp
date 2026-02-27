package com.mycontacts.model;

public class FreeUser extends User {
    private static final int MAX_CONTACTS = 100;

    public FreeUser(String name, String email, String passwordHash) {
        super(name, email, passwordHash);
    }

    @Override
    public String getUserType() { return "Free"; }

    public int getMaxContacts() { return MAX_CONTACTS; }

    @Override
    public String toString() {
        return super.toString() + " [Max Contacts: " + MAX_CONTACTS + "]";
    }
}