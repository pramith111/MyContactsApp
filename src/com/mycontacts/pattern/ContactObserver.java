package com.mycontacts.pattern;

import com.mycontacts.model.Contact;

public interface ContactObserver {
    void onContactDeleted(Contact contact);
}