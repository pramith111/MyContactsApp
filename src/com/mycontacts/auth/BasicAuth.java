package com.mycontacts.auth;

import com.mycontacts.model.User;
import com.mycontacts.service.UserRegistrationService;
import com.mycontacts.util.PasswordUtil;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class BasicAuth implements Authentication {
    private UserRegistrationService registrationService;
    private Set<String> loggedInUsers = new HashSet<>();

    public BasicAuth(UserRegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @Override
    public Optional<User> login(String email, String password) {
        Optional<User> userOpt = registrationService.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (PasswordUtil.verifyPassword(password, user.getPasswordHash())) {
                loggedInUsers.add(user.getUserId());
                System.out.println("✓ Login successful! Welcome, " + user.getName());
                return Optional.of(user);
            }
        }
        System.out.println("✗ Invalid email or password.");
        return Optional.empty();
    }

    @Override
    public void logout(String userId) {
        if (loggedInUsers.remove(userId)) {
            System.out.println("✓ Logged out successfully.");
        } else {
            System.out.println("✗ User not logged in.");
        }
    }

    @Override
    public boolean isLoggedIn(String userId) {
        return loggedInUsers.contains(userId);
    }
}