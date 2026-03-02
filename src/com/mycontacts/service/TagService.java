package com.mycontacts.service;

import com.mycontacts.model.Contact;
import com.mycontacts.model.Tag;
import com.mycontacts.pattern.TagObserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TagService {
    private Map<String, Set<Tag>> contactTags = new HashMap<>();
    private List<TagObserver> observers = new ArrayList<>();

    public void addObserver(TagObserver observer) {
        observers.add(observer);
    }

    private void notifyTagAdded(Contact contact, Tag tag) {
        observers.forEach(o -> o.onTagAdded(contact, tag));
    }

    private void notifyTagRemoved(Contact contact, Tag tag) {
        observers.forEach(o -> o.onTagRemoved(contact, tag));
    }

    public void addTagToContact(Contact contact, Tag tag) {
        String id = contact.getContactId();
        contactTags.putIfAbsent(id, new HashSet<>());
        contactTags.get(id).add(tag);
        notifyTagAdded(contact, tag);
    }

    public void removeTagFromContact(Contact contact, Tag tag) {
        String id = contact.getContactId();
        if (contactTags.containsKey(id)) {
            contactTags.get(id).remove(tag);
            notifyTagRemoved(contact, tag);
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

    public List<Contact> getContactsByTag(List<Contact> contacts, String tagName) {
        List<Contact> result = new ArrayList<>();
        for (Contact contact : contacts) {
            Set<Tag> tags = getTagsForContact(contact);
            boolean hasTag = tags.stream()
                    .anyMatch(t -> t.getName().equalsIgnoreCase(tagName));
            if (hasTag) result.add(contact);
        }
        return result;
    }
}