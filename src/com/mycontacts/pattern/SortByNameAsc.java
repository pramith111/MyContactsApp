package com.mycontacts.pattern;

import com.mycontacts.model.Contact;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SortByNameAsc implements ContactSortStrategy {

    @Override
    public List<Contact> sort(List<Contact> contacts) {
        return contacts.stream()
                .sorted(Comparator.comparing(Contact::getDisplayName))
                .collect(Collectors.toList());
    }
}