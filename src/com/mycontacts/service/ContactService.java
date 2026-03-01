package com.mycontacts.service;

import com.mycontacts.model.Contact;
import com.mycontacts.pattern.ContactObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ContactService {
    private List<Contact> contacts = new ArrayList<>();
    private List<Contact> deletedContacts = new ArrayList<>();
    private List<ContactObserver> observers = new ArrayList<>();

    public void addObserver(ContactObserver observer) {
        observers.add(observer);
    }

    private void notifyObservers(Contact contact) {
        observers.forEach(o -> o.onContactDeleted(contact));
    }

    public void addContact(Contact contact) {
        contacts.add(contact);
        System.out.println("✓ Contact added: " + contact.getDisplayName());
    }

    // Soft delete - marks as deleted but keeps in memory
    public boolean softDelete(String contactId) {
        Optional<Contact> contactOpt = findById(contactId);
        if (contactOpt.isPresent()) {
            Contact contact = contactOpt.get();
            contacts.remove(contact);
            deletedContacts.add(contact);
            notifyObservers(contact);
            System.out.println("✓ Contact soft deleted: " + contact.getDisplayName());
            return true;
        }
        System.out.println("✗ Contact not found.");
        return false;
    }

    // Hard delete - permanently removes contact
    public boolean hardDelete(String contactId) {
        Optional<Contact> contactOpt = findById(contactId);
        if (contactOpt.isPresent()) {
            Contact contact = contactOpt.get();
            contacts.remove(contact);
            notifyObservers(contact);
            System.out.println("✓ Contact permanently deleted: " + contact.getDisplayName());
            return true;
        }
        System.out.println("✗ Contact not found.");
        return false;
    }

    // Restore soft deleted contact
    public boolean restore(String contactId) {
        Optional<Contact> contactOpt = deletedContacts.stream()
                .filter(c -> c.getContactId().equals(contactId))
                .findFirst();
        if (contactOpt.isPresent()) {
            Contact contact = contactOpt.get();
            deletedContacts.remove(contact);
            contacts.add(contact);
            System.out.println("✓ Contact restored: " + contact.getDisplayName());
            return true;
        }
        System.out.println("✗ Contact not found in deleted list.");
        return false;
    }

    public Optional<Contact> findById(String contactId) {
        return contacts.stream()
                .filter(c -> c.getContactId().equals(contactId))
                .findFirst();
    }

    public List<Contact> getAllContacts() {
        return new ArrayList<>(contacts);
    }

    public List<Contact> getDeletedContacts() {
        return new ArrayList<>(deletedContacts);
    }

    public int getTotalContacts() {
        return contacts.size();
    }
}