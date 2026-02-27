package com.mycontacts.service;

import com.mycontacts.model.Contact;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ContactService {
    private List<Contact> contacts = new ArrayList<>();

    public void addContact(Contact contact) {
        contacts.add(contact);
        System.out.println("✓ Contact added: " + contact.getDisplayName());
    }

    public Optional<Contact> findById(String contactId) {
        return contacts.stream()
                .filter(c -> c.getContactId().equals(contactId))
                .findFirst();
    }

    public List<Contact> getAllContacts() {
        return new ArrayList<>(contacts);
    }

    public int getTotalContacts() {
        return contacts.size();
    }
}