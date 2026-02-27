package com.mycontacts.pattern;

import com.mycontacts.model.FreeUser;
import com.mycontacts.model.PremiumUser;
import com.mycontacts.model.User;

public class UserBuilder {
    private String name;
    private String email;
    private String passwordHash;
    private String userType = "free";
    private String subscriptionPlan = "Monthly";

    public UserBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public UserBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
        return this;
    }

    public UserBuilder setUserType(String userType) {
        this.userType = userType;
        return this;
    }

    public UserBuilder setSubscriptionPlan(String subscriptionPlan) {
        this.subscriptionPlan = subscriptionPlan;
        return this;
    }

    public User build() {
        if (name == null || email == null || passwordHash == null) {
            throw new IllegalStateException("Name, email and password are required");
        }
        if (userType.equalsIgnoreCase("premium")) {
            return new PremiumUser(name, email, passwordHash, subscriptionPlan);
        }
        return new FreeUser(name, email, passwordHash);
    }
}