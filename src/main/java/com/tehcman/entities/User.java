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
    private Status status;
    private Character sex;
    private String city;
    private Integer amountOfPeople;
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

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setSex(Character sex) {
        this.sex = sex;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setAmountOfPeople(Integer amountOfPeople) {
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
        return "User{\n" +
                "id=" + id + '\n' +
                "tgUsername=" + tgUsername + '\n' +
                "name=" + name + '\n' +
                "position=" + position + '\n' +
                "phoneNumber=" + phoneNumber + '\n' +
                "age=" + age + '\n' +
                "status=" + status + '\n' +
                "sex=" + sex + '\n' +
                "city=" + city + '\n' +
                "amountOfPeople=" + amountOfPeople + '\n' +
                "date=" + date + '\n' +
                "additional=" + additional + '\n' +
                '}';
    }
}
