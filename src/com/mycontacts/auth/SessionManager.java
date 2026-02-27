package com.mycontacts.auth;

import com.mycontacts.model.User;

public class SessionManager {
    private static SessionManager instance;
    private User currentUser;

    private SessionManager() {}

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    public void clearSession() {
        currentUser = null;
    }

    @Override
    public String toString() {
        if (currentUser == null) return "No active session.";
        return "Active session: " + currentUser.getName() + " (" + currentUser.getEmail() + ")";
    }
}