package com.mycontacts.pattern;

import com.mycontacts.model.Contact;
import com.mycontacts.model.Email;

public class MaskedEmailContactDisplay implements ContactDisplay {
    private ContactDisplay wrapped;

    public MaskedEmailContactDisplay(ContactDisplay wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public String display(Contact contact) {
        String result = wrapped.display(contact);
        for (Email email : contact.getEmails()) {
            String address = email.getAddress();
            String masked = maskEmail(address);
            result = result.replace(address, masked);
        }
        return result;
    }

    private String maskEmail(String email) {
        int atIndex = email.indexOf('@');
        if (atIndex <= 1) return email;
        return email.charAt(0) + "****" + email.substring(atIndex);
    }
}