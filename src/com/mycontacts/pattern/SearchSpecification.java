package com.mycontacts.pattern;

import com.mycontacts.model.Contact;

public interface SearchSpecification {
    boolean isSatisfiedBy(Contact contact);
}