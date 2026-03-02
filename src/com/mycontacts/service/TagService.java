package com.mycontacts.service;

import com.mycontacts.model.Contact;
import com.mycontacts.model.Tag;
import com.mycontacts.pattern.TagFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TagService {
    private Map<String, Set<Tag>> contactTags = new HashMap<>();

    public void addTagToContact(Contact contact, Tag tag) {
        String id = contact.getContactId();
        contactTags.putIfAbsent(id, new HashSet<>());
        contactTags.get(id).add(tag);
        System.out.println("✓ Tag '" + tag.getName() + "' added to " + contact.getDisplayName());
    }

    public void removeTagFromContact(Contact contact, Tag tag) {
        String id = contact.getContactId();
        if (contactTags.containsKey(id)) {
            contactTags.get(id).remove(tag);
            System.out.println("✓ Tag '" + tag.getName() + "' removed from " + contact.getDisplayName());
        }
    }

    public Set<Tag> getTagsForContact(Contact contact) {
        return contactTags.getOrDefault(contact.getContactId(), new HashSet<>());
    }

    public void displayTagsForContact(Contact contact) {
        Set<Tag> tags = getTagsForContact(contact);
        if (tags.isEmpty()) {
            System.out.println("No tags for: " + contact.getDisplayName());
        } else {
            System.out.println("Tags for " + contact.getDisplayName() + ": " + tags);
        }
    }
}