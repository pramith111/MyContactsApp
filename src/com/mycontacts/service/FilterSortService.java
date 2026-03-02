package com.mycontacts.service;

import com.mycontacts.model.Contact;
import com.mycontacts.pattern.ContactFilter;
import com.mycontacts.pattern.ContactSortStrategy;

import java.util.List;

public class FilterSortService {

    public List<Contact> filter(List<Contact> contacts, ContactFilter filter) {
        return filter.filter(contacts);
    }

    public List<Contact> sort(List<Contact> contacts, ContactSortStrategy strategy) {
        return strategy.sort(contacts);
    }

    public List<Contact> filterAndSort(List<Contact> contacts, ContactFilter filter, ContactSortStrategy strategy) {
        List<Contact> filtered = filter.filter(contacts);
        return strategy.sort(filtered);
    }

    public void displayResults(List<Contact> results) {
        if (results.isEmpty()) {
            System.out.println("No contacts found.");
        } else {
            System.out.println("\n--- Results (" + results.size() + ") ---");
            results.forEach(c -> System.out.println("  - " + c.getDisplayName()
                    + " (" + c.getContactType() + ")"
                    + " Added: " + c.getCreatedAt().toLocalDate()));
        }
    }
}