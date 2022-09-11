package com.tehcman.input_final_destination.resources;

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
