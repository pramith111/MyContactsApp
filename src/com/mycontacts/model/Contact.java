package com.mycontacts.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class Contact {
    private String contactId;
    private List<PhoneNumber> phoneNumbers;
    private List<Email> emails;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Contact() {
        this.contactId = UUID.randomUUID().toString();
        this.phoneNumbers = new ArrayList<>();
        this.emails = new ArrayList<>();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public String getContactId() { return contactId; }
    public List<PhoneNumber> getPhoneNumbers() { return new ArrayList<>(phoneNumbers); }
    public List<Email> getEmails() { return new ArrayList<>(emails); }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void addPhoneNumber(PhoneNumber phone) {
        phoneNumbers.add(phone);
        this.updatedAt = LocalDateTime.now();
    }

    public void addEmail(Email email) {
        emails.add(email);
        this.updatedAt = LocalDateTime.now();
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public abstract String getContactType();
    public abstract String getDisplayName();

    @Override
    public String toString() {
        return "Contact[" + getContactType() + "] - " + getDisplayName();
    }
}