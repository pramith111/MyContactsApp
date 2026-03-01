package com.mycontacts.pattern;

import com.mycontacts.model.Contact;

public class UpperCaseContactDisplay implements ContactDisplay {
    private ContactDisplay wrapped;

    public UpperCaseContactDisplay(ContactDisplay wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public String display(Contact contact) {
        return wrapped.display(contact).toUpperCase();
    }
}