package com.mycontacts.pattern;

import com.mycontacts.model.Contact;

public class NameSearchSpecification implements SearchSpecification {
    private String keyword;

    public NameSearchSpecification(String keyword) {
        this.keyword = keyword.toLowerCase();
    }

    @Override
    public boolean isSatisfiedBy(Contact contact) {
        return contact.getDisplayName().toLowerCase().contains(keyword);
    }
}