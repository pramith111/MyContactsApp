package com.mycontacts.pattern;

import com.mycontacts.model.Contact;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ContactDeletionLogger implements ContactObserver {
    private List<String> deletionLog = new ArrayList<>();

    @Override
    public void onContactDeleted(Contact contact) {
        String log = "[" + LocalDateTime.now() + "] Deleted: "
                + contact.getDisplayName()
                + " (" + contact.getContactType() + ")";
        deletionLog.add(log);
        System.out.println("✓ Deletion logged: " + contact.getDisplayName());
    }

    public void printLog() {
        if (deletionLog.isEmpty()) {
            System.out.println("No deletions logged.");
        } else {
            System.out.println("\n--- Deletion Log ---");
            deletionLog.forEach(System.out::println);
        }
    }
}
