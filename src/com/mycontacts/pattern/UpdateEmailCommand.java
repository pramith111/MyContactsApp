package com.mycontacts.pattern;

import com.mycontacts.model.User;

public class UpdateEmailCommand implements ProfileUpdateCommand {
    private User user;
    private String newEmail;
    private String oldEmail;

    public UpdateEmailCommand(User user, String newEmail) {
        this.user = user;
        this.newEmail = newEmail;
        this.oldEmail = user.getEmail();
    }

    @Override
    public void execute() {
        user.setEmail(newEmail);
        System.out.println("✓ Email updated to: " + newEmail);
    }

    @Override
    public void undo() {
        user.setEmail(oldEmail);
        System.out.println("✓ Email reverted to: " + oldEmail);
    }
}