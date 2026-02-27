package com.mycontacts.pattern;

public interface ProfileUpdateCommand {
    void execute();
    void undo();
}