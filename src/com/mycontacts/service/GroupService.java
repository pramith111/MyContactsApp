package com.mycontacts.service;

import com.mycontacts.model.Contact;
import com.mycontacts.model.ContactGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GroupService {
    private List<ContactGroup> groups = new ArrayList<>();

    public ContactGroup createGroup(String name) {
        ContactGroup group = new ContactGroup(name);
        groups.add(group);
        System.out.println("✓ Group created: " + name);
        return group;
    }

    public Optional<ContactGroup> findGroup(String name) {
        return groups.stream()
                .filter(g -> g.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    public List<ContactGroup> getAllGroups() {
        return new ArrayList<>(groups);
    }

    public void bulkAddToGroup(ContactGroup group, List<Contact> contacts) {
        contacts.forEach(group::addContact);
        System.out.println("✓ Bulk added " + contacts.size() + " contacts to group: " + group.getName());
    }

    public void bulkRemoveFromGroup(ContactGroup group, List<Contact> contacts) {
        contacts.forEach(group::removeContact);
        System.out.println("✓ Bulk removed " + contacts.size() + " contacts from group: " + group.getName());
    }

    public void displayAllGroups() {
        if (groups.isEmpty()) {
            System.out.println("No groups found.");
            return;
        }
        groups.forEach(ContactGroup::display);
    }
}