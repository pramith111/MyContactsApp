package com.mycontacts.pattern;

import java.util.List;
import com.mycontacts.model.Contact;

public interface ContactComponent {
    String getName();
    List<Contact> getContacts();
    void display();
}