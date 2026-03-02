package com.mycontacts.pattern;

import com.mycontacts.model.Contact;
import java.util.List;
import java.util.stream.Collectors;

public class ContactTypeFilter implements ContactFilter {
    private String type;

    public ContactTypeFilter(String type) {
        this.type = type;
    }

    @Override
    public List<Contact> filter(List<Contact> contacts) {
        return contacts.stream()
                .filter(c -> c.getContactType().equalsIgnoreCase(type))
                .collect(Collectors.toList());
    }
}