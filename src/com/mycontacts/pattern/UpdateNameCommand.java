package com.mycontacts.pattern;

import com.mycontacts.model.User;

public class UpdateNameCommand implements ProfileUpdateCommand {
    private User user;
    private String newName;
    private String oldName;

    public UpdateNameCommand(User user, String newName) {
        this.user = user;
        this.newName = newName;
        this.oldName = user.getName();
    }

    @Override
    public void execute() {
        user.setName(newName);
        System.out.println("✓ Name updated to: " + newName);
    }

    @Override
    public void undo() {
        user.setName(oldName);
        System.out.println("✓ Name reverted to: " + oldName);
    }
}