package com.mycontacts.util;

public class UserValidator {

    public static boolean isValidEmail(String email) {
        if (email == null) return false;
        String emailRegex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
        return email.matches(emailRegex);
    }

    public static boolean isValidPassword(String password) {
        if (password == null) return false;
        // At least 8 characters, one uppercase, one digit
        return password.length() >= 8
                && password.chars().anyMatch(Character::isUpperCase)
                && password.chars().anyMatch(Character::isDigit);
    }

    public static boolean isValidName(String name) {
        if (name == null) return false;
        return name.trim().length() >= 2;
    }
}