package com.mycontacts.model;

public class PremiumUser extends User {
    private static final int MAX_CONTACTS = 10000;
    private String subscriptionPlan;

    public PremiumUser(String name, String email, String passwordHash, String subscriptionPlan) {
        super(name, email, passwordHash);
        this.subscriptionPlan = subscriptionPlan;
    }

    @Override
    public String getUserType() { return "Premium"; }

    public int getMaxContacts() { return MAX_CONTACTS; }
    public String getSubscriptionPlan() { return subscriptionPlan; }

    @Override
    public String toString() {
        return super.toString() + " [Plan: " + subscriptionPlan + ", Max Contacts: " + MAX_CONTACTS + "]";
    }
}