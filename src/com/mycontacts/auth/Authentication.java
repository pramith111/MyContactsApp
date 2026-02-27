package com.mycontacts.auth;

import com.mycontacts.model.User;
import java.util.Optional;

public interface Authentication {
    Optional<User> login(String email, String password);
    void logout(String userId);
    boolean isLoggedIn(String userId);
}