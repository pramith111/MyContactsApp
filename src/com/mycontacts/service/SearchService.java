package com.mycontacts.service;

import com.mycontacts.model.Contact;
import com.mycontacts.pattern.SearchSpecification;

import java.util.List;
import java.util.stream.Collectors;

public class SearchService {

    public List<Contact> search(List<Contact> contacts, SearchSpecification specification) {
        return contacts.stream()
                .filter(specification::isSatisfiedBy)
                .collect(Collectors.toList());
    }

    public void displayResults(List<Contact> results) {
        if (results.isEmpty()) {
            System.out.println("No contacts found matching your search.");
        } else {
            System.out.println("\n--- Search Results (" + results.size() + ") ---");
            results.forEach(c -> System.out.println("  - " + c.getDisplayName()
                    + " (" + c.getContactType() + ")"));
        }
    }
}