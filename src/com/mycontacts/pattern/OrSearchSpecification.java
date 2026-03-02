package com.mycontacts.pattern;

import com.mycontacts.model.Contact;

public class OrSearchSpecification implements SearchSpecification {
    private SearchSpecification first;
    private SearchSpecification second;

    public OrSearchSpecification(SearchSpecification first, SearchSpecification second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean isSatisfiedBy(Contact contact) {
        return first.isSatisfiedBy(contact) || second.isSatisfiedBy(contact);
    }
}