package com.mycontacts.pattern;

import com.mycontacts.model.Contact;
import com.mycontacts.model.Tag;

public interface TagObserver {
    void onTagAdded(Contact contact, Tag tag);
    void onTagRemoved(Contact contact, Tag tag);
}