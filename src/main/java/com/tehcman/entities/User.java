package com.tehcman.entities;

public class User {
    private static boolean isActiveUserRegistration;

    //fill the following fields from the user's message
    private Long id;
    private String tgUsername;
    private String name;
    private Position position;

    private String phoneNumber;
    private String age;


    public User(Long id, String tgUsername, String name, Position position){
        this.id = id;
        this.tgUsername = tgUsername;
        this.name = name;
        this.position = position;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTgUsername() {
        return tgUsername;
    }

    public void setTgUsername(String tgUsername) {
        this.tgUsername = tgUsername;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public static boolean isActiveUserRegistration() {
        return isActiveUserRegistration;
    }

    public static void setActiveUserRegistration(boolean activeUserRegistration) {
        isActiveUserRegistration = activeUserRegistration;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", tgUsername='" + tgUsername + '\'' +
                ", name='" + name + '\'' +
                ", position=" + position +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", age='" + age + '\'' +
                '}';
    }
}
