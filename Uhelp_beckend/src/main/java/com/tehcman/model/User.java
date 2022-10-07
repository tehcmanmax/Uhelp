package com.tehcman.model;

import javax.persistence.*;

@Entity
@Table
public class User {

    //fill the following fields from the user's message
    @Id
    @Column(name = "UserId")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String tgUsername;

    @Column
    private String name;

    @Column
    private String phoneNumber;
    @Column
    private String email;
    @Column
    private String social;

    @Column
    private String age;


    //sprint 2:
    @Column
    private String status;
    @Column
    private String sex;
    @Column
    private String city;

    @Column
    private String country;
    @Column
    private Integer amountOfPeople;
    @Column
    private String date;
    @Column
    private String additional;


    public User() {
    }

    public Long getId() {
        return id;
    }


    public String getTgUsername() {
        return tgUsername;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getSocial() {
        return social;
    }

    public String getAge() {
        return age;
    }

    public String getStatus() {
        return status;
    }

    public String getSex() {
        return sex;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public Integer getAmountOfPeople() {
        return amountOfPeople;
    }

    public String getDate() {
        return date;
    }

    public String getAdditional() {
        return additional;
    }

    public void setTgUsername(String tgUsername) {
        this.tgUsername = tgUsername;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
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


    public void setEmail(String email) {
        this.email = email;
    }

    public void setSocial(String social) {
        this.social = social;
    }

    @Override
    public String toString() {
        return "User{\n" +
                "id=" + id + '\n' +
                "tgUsername=" + tgUsername + '\n' +
                "name=" + name + '\n' +
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