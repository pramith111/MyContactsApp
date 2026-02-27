package com.mycontacts.service;

import com.mycontacts.pattern.ProfileUpdateCommand;

import java.util.Stack;

public class ProfileService {
    private Stack<ProfileUpdateCommand> commandHistory = new Stack<>();

    public void executeCommand(ProfileUpdateCommand command) {
        command.execute();
        commandHistory.push(command);
    }

    public void undo() {
        if (!commandHistory.isEmpty()) {
            ProfileUpdateCommand command = commandHistory.pop();
            command.undo();
        } else {
            System.out.println("✗ Nothing to undo.");
        }
    }

    public int getHistorySize() {
        return commandHistory.size();
    }
}