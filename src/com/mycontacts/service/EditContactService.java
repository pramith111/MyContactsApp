package com.mycontacts.service;

import com.mycontacts.pattern.EditContactCommand;

import java.util.Stack;

public class EditContactService {
    private Stack<EditContactCommand> undoStack = new Stack<>();
    private Stack<EditContactCommand> redoStack = new Stack<>();

    public void executeEdit(EditContactCommand command) {
        command.execute();
        undoStack.push(command);
        redoStack.clear();
    }

    public void undo() {
        if (!undoStack.isEmpty()) {
            EditContactCommand command = undoStack.pop();
            command.undo();
            redoStack.push(command);
        } else {
            System.out.println("✗ Nothing to undo.");
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            EditContactCommand command = redoStack.pop();
            command.redo();
            undoStack.push(command);
        } else {
            System.out.println("✗ Nothing to redo.");
        }
    }
}