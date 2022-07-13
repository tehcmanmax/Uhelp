package com.tehcman.entities;

public class User {

    //fill the following fields from the user's message
    private final Long id;
    private final String tgUsername;


    private String name;
    private Position position;

    private String phoneNumber;
    private String age;


    //sprint 2:
    private char sex;
    private String city;
    private int amountOfPeople;
    private String date;
    private String additional;


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

    public void setName(String name) {
        this.name = name;
    }


    public void setSex(char sex) {
        this.sex = sex;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setAmountOfPeople(int amountOfPeople) {
        this.amountOfPeople = amountOfPeople;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setAdditional(String additional) {
        this.additional = additional;
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
