package com.mycontacts.pattern;

import com.mycontacts.model.Contact;
import com.mycontacts.model.Organization;
import com.mycontacts.model.Person;

import java.time.LocalDateTime;

public class EditContactCommand {
    private Contact contact;
    private ContactMemento previousState;
    private ContactMemento newState;

    public EditContactCommand(Contact contact, ContactMemento newState) {
        this.contact = contact;
        this.previousState = captureState(contact);
        this.newState = newState;
    }

    public void execute() {
        applyState(contact, newState);
        System.out.println("✓ Contact updated: " + contact.getDisplayName());
    }

    public void undo() {
        applyState(contact, previousState);
        System.out.println("✓ Contact edit undone: " + contact.getDisplayName());
    }

    public void redo() {
        applyState(contact, newState);
        System.out.println("✓ Contact edit redone: " + contact.getDisplayName());
    }

    private ContactMemento captureState(Contact contact) {
        if (contact instanceof Person) {
            Person p = (Person) contact;
            return new ContactMemento(p.getFirstName(), p.getLastName(), null,
                    p.getAddress(), null, null,
                    p.getPhoneNumbers(), p.getEmails());
        } else if (contact instanceof Organization) {
            Organization o = (Organization) contact;
            return new ContactMemento(null, null, o.getCompanyName(),
                    null, o.getIndustry(), o.getWebsite(),
                    o.getPhoneNumbers(), o.getEmails());
        }
        return null;
    }

    private void applyState(Contact contact, ContactMemento state) {
        if (contact instanceof Person && state != null) {
            Person p = (Person) contact;
            if (state.getFirstName() != null) p.setFirstName(state.getFirstName());
            if (state.getLastName() != null) p.setLastName(state.getLastName());
            if (state.getAddress() != null) p.setAddress(state.getAddress());
        } else if (contact instanceof Organization && state != null) {
            Organization o = (Organization) contact;
            if (state.getCompanyName() != null) o.setCompanyName(state.getCompanyName());
            if (state.getIndustry() != null) o.setIndustry(state.getIndustry());
            if (state.getWebsite() != null) o.setWebsite(state.getWebsite());
        }
        contact.setUpdatedAt(LocalDateTime.now());
    }
}