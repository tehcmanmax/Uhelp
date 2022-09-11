package com.tehcman.entities;

import lombok.Getter;
import lombok.Setter;

public class User {

    //fill the following fields from the user's message
    @Getter
    private final Long id;
    private final String tgUsername;

    @Setter
    private String name;
    @Getter
    @Setter
    private Phase phase;

    @Setter
    private String phoneNumber;
    @Setter
    private String email;
    @Setter
    private String social;

    @Setter
    private String age;


    //sprint 2:
    @Getter
    @Setter
    private Status status;
    @Setter
    private Character sex;
    @Setter
    private String city;
    @Setter
    private String country;
    @Setter
    private Integer amountOfPeople;
    @Setter
    private String date;
    @Setter
    private String additional;

    public User(Long id, String tgUsername, String name, Phase phase) {
        this.id = id;
        this.tgUsername = tgUsername;
        this.name = name;
        this.phase = phase;
    }

    @Override
    public String toString() {
        return "User{\n" +
                "id=" + id + '\n' +
                "tgUsername=" + tgUsername + '\n' +
                "name=" + name + '\n' +
                "phase=" + phase + '\n' +
                "phoneNumber=" + phoneNumber + '\n' +
                "email=" + email + '\n' +
                "social=" + social + '\n' +
                "age=" + age + '\n' +
                "status=" + status + '\n' +
                "sex=" + sex + '\n' +
                "city=" + city + '\n' +
                "country=" + country + '\n' +
                "amountOfPeople=" + amountOfPeople + '\n' +
                "date=" + date + '\n' +
                "additional=" + additional + '\n' +
                '}';
    }
}
