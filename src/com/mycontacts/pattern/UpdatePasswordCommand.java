package com.mycontacts.pattern;

import com.mycontacts.model.User;
import com.mycontacts.util.PasswordUtil;

public class UpdatePasswordCommand implements ProfileUpdateCommand {
    private User user;
    private String newPasswordHash;
    private String oldPasswordHash;

    public UpdatePasswordCommand(User user, String newPassword) {
        this.user = user;
        this.newPasswordHash = PasswordUtil.hashPassword(newPassword);
        this.oldPasswordHash = user.getPasswordHash();
    }

    @Override
    public void execute() {
        user.setPasswordHash(newPasswordHash);
        System.out.println("✓ Password updated successfully.");
    }

    @Override
    public void undo() {
        user.setPasswordHash(oldPasswordHash);
        System.out.println("✓ Password reverted successfully.");
    }
}