package com.mycontacts.pattern;

import com.mycontacts.model.Contact;
import java.util.List;

public interface ContactSortStrategy {
    List<Contact> sort(List<Contact> contacts);
}