package com.mycontacts.pattern;

import com.mycontacts.model.FreeUser;
import com.mycontacts.model.PremiumUser;
import com.mycontacts.model.User;

public class UserFactory {

    public static User createUser(String type, String name, String email, String passwordHash) {
        switch (type.toLowerCase()) {
            case "free":
                return new FreeUser(name, email, passwordHash);
            case "premium":
                return new PremiumUser(name, email, passwordHash, "Monthly");
            default:
                throw new IllegalArgumentException("Unknown user type: " + type);
        }
    }
}