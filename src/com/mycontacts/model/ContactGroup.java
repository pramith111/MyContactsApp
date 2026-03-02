package com.mycontacts.model;

import com.mycontacts.pattern.ContactComponent;

import java.util.ArrayList;
import java.util.List;

public class ContactGroup implements ContactComponent {
    private String groupName;
    private List<Contact> contacts;
    private List<ContactComponent> components;

    public ContactGroup(String groupName) {
        this.groupName = groupName;
        this.contacts = new ArrayList<>();
        this.components = new ArrayList<>();
    }

    public void addContact(Contact contact) {
        contacts.add(contact);
        System.out.println("✓ Added " + contact.getDisplayName() + " to group: " + groupName);
    }

    public void removeContact(Contact contact) {
        contacts.remove(contact);
        System.out.println("✓ Removed " + contact.getDisplayName() + " from group: " + groupName);
    }

    public void addComponent(ContactComponent component) {
        components.add(component);
    }

    @Override
    public String getName() { return groupName; }

    @Override
    public List<Contact> getContacts() {
        List<Contact> all = new ArrayList<>(contacts);
        for (ContactComponent component : components) {
            all.addAll(component.getContacts());
        }
        return all;
    }

    @Override
    public void display() {
        System.out.println("\nGroup: " + groupName + " (" + getContacts().size() + " contacts)");
        contacts.forEach(c -> System.out.println("  - " + c.getDisplayName()
                + " (" + c.getContactType() + ")"));
        components.forEach(ContactComponent::display);
    }

    public int size() { return contacts.size(); }
}