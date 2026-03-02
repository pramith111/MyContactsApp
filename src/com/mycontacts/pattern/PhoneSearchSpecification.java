package com.mycontacts.pattern;

import com.mycontacts.model.Contact;
import com.mycontacts.model.PhoneNumber;

public class PhoneSearchSpecification implements SearchSpecification {
    private String phoneNumber;

    public PhoneSearchSpecification(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean isSatisfiedBy(Contact contact) {
        return contact.getPhoneNumbers().stream()
                .map(PhoneNumber::getNumber)
                .anyMatch(p -> p.contains(phoneNumber));
    }
}