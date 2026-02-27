package com.mycontacts.model;

public class Person extends Contact {
    private String firstName;
    private String lastName;
    private String address;

    public Person(String firstName, String lastName) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getAddress() { return address; }

    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setAddress(String address) { this.address = address; }

    @Override
    public String getContactType() { return "Person"; }

    @Override
    public String getDisplayName() { return firstName + " " + lastName; }

    @Override
    public String toString() {
        return super.toString()
                + "\n  Phones: " + getPhoneNumbers()
                + "\n  Emails: " + getEmails()
                + "\n  Address: " + (address != null ? address : "N/A")
                + "\n  Created: " + getCreatedAt();
    }
}