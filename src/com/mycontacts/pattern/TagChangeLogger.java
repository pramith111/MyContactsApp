package com.mycontacts.pattern;

import com.mycontacts.model.Contact;
import com.mycontacts.model.Tag;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TagChangeLogger implements TagObserver {
    private List<String> log = new ArrayList<>();

    @Override
    public void onTagAdded(Contact contact, Tag tag) {
        String entry = "[" + LocalDateTime.now() + "] TAG ADDED: '"
                + tag.getName() + "' → " + contact.getDisplayName();
        log.add(entry);
        System.out.println("✓ " + entry);
    }

    @Override
    public void onTagRemoved(Contact contact, Tag tag) {
        String entry = "[" + LocalDateTime.now() + "] TAG REMOVED: '"
                + tag.getName() + "' → " + contact.getDisplayName();
        log.add(entry);
        System.out.println("✓ " + entry);
    }

    public void printLog() {
        if (log.isEmpty()) {
            System.out.println("No tag changes logged.");
        } else {
            System.out.println("\n--- Tag Change Log ---");
            log.forEach(System.out::println);
        }
    }
}