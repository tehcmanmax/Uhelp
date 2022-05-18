package com.tehcman.entities;

public class User {

    //fill the following fields from the user's message
    private final Long id;
    private final String tgUsername;
    private final String name;
    private Position position;

    private String phoneNumber;
    private String age;


    public User(Long id, String tgUsername, String name, Position position) {
        this.id = id;
        this.tgUsername = tgUsername;
        this.name = name;
        this.position = position;
    }

    public Long getId() {
        return id;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setAge(String age) {
        this.age = age;
    }


    @Override
    public String toString() {
        return "Your data:" +
                "\nid = " + id +
                "\ntgUsername = '" + tgUsername + '\'' +
                "\nname = '" + name + '\'' +
                "\nphoneNumber = '" + phoneNumber + '\'' +
                "\nage = '" + age + '\'';
    }
}
