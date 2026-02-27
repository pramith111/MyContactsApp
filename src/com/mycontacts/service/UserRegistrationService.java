package com.mycontacts.service;

import com.mycontacts.model.User;
import com.mycontacts.pattern.UserBuilder;
import com.mycontacts.util.PasswordUtil;
import com.mycontacts.util.UserValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRegistrationService {
    private List<User> users = new ArrayList<>();

    public User registerUser(String name, String email, String password, String userType) {
        // Validate inputs
        if (!UserValidator.isValidName(name)) {
            throw new IllegalArgumentException("Invalid name. Must be at least 2 characters.");
        }
        if (!UserValidator.isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format.");
        }
        if (!UserValidator.isValidPassword(password)) {
            throw new IllegalArgumentException("Invalid password. Must be 8+ characters with uppercase and digit.");
        }
        // Check if email already exists
        if (findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email already registered: " + email);
        }

        // Hash password and build user
        String passwordHash = PasswordUtil.hashPassword(password);
        User user = new UserBuilder()
                .setName(name)
                .setEmail(email)
                .setPasswordHash(passwordHash)
                .setUserType(userType)
                .build();

        users.add(user);
        return user;
    }

    public Optional<User> findByEmail(String email) {
        return users.stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }
}