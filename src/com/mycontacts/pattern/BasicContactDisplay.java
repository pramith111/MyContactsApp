package com.mycontacts.pattern;

import com.mycontacts.model.Contact;

public class BasicContactDisplay implements ContactDisplay {

    @Override
    public String display(Contact contact) {
        return contact.toString();
    }
}