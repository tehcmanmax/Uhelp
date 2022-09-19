package com.tehcman.resources;

public enum Command {
    START("/start"),
    HOME("/home");

    private final String command;

    Command(String str) {
        this.command = str;
    }

    @Override
    public String toString() {
        return command;
    }
}
