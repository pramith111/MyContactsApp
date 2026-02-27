package com.mycontacts.pattern;

import com.mycontacts.model.Contact;
import com.mycontacts.model.Organization;
import com.mycontacts.model.Person;

public class ContactFactory {

    public static Contact createContact(String type, String... args) {
        switch (type.toLowerCase()) {
            case "person":
                if (args.length < 2) throw new IllegalArgumentException("Person requires first and last name.");
                return new Person(args[0], args[1]);
            case "organization":
                if (args.length < 1) throw new IllegalArgumentException("Organization requires a company name.");
                return new Organization(args[0]);
            default:
                throw new IllegalArgumentException("Unknown contact type: " + type);
        }
    }
}