package com.mycontacts.pattern;

import com.mycontacts.model.Contact;
import com.mycontacts.model.Email;

public class EmailSearchSpecification implements SearchSpecification {
    private String keyword;

    public EmailSearchSpecification(String keyword) {
        this.keyword = keyword.toLowerCase();
    }

    @Override
    public boolean isSatisfiedBy(Contact contact) {
        return contact.getEmails().stream()
                .map(Email::getAddress)
                .anyMatch(e -> e.toLowerCase().contains(keyword));
    }
}