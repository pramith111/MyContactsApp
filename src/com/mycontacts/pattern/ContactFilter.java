package com.mycontacts.pattern;

import com.mycontacts.model.Contact;
import java.util.List;

public interface ContactFilter {
    List<Contact> filter(List<Contact> contacts);
}