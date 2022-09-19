package com.tehcman.entities;
/* USEFUL LINKS
 * https://github.com/FasterXML/jackson-databind/issues/95
 * https://www.tutorialspoint.com/jackson_annotations/jackson_annotations_jsonignoreproperties.htm
 * https://www.baeldung.com/jackson-serialize-enums
 * https://stackabuse.com/converting-json-array-to-a-java-array-or-list/
 * https://www.baeldung.com/jackson-object-mapper-tutorial
 * https://www.mockaroo.com/

 * */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties({"phase"})
public class User {
    public User() {
    }

    //fill the following fields from the user's message
    @Getter
    private /*final*/ Long id;
    @Getter
    private /*final*/ String tgUsername;

    @Getter
    @Setter
    @JsonProperty("isViewed")
    private boolean isViewed;

    @Setter
    private String name;
    @Getter
    @Setter
//    @JsonIgnore
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
//    @JsonIgnore
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
        return "Telegram username: " + tgUsername + '\n' +
                returnIfNotNull();
    }

    private String returnIfNotNull() {
        StringBuilder str = new StringBuilder();
        if (name != null) {
            str.append("Name: " + name + '\n');
        }
        if (phoneNumber != null) {
            str.append("Phone number: " + phoneNumber + '\n');
        }
        if (email != null) {
            str.append("Email" + email);
        }
        if (social != null) {
            str.append("Social: " + social + '\n');
        }
        if (phoneNumber != null) {
            str.append("Phone number: " + phoneNumber + '\n');
        }
        if (age != null) {
            str.append("Age: " + age + '\n');
        }
        if (sex != null) {
            str.append("Sex: " + sex + '\n');
        }
        if (city != null) {
            str.append("City: " + city + '\n');
        }
        if (country != null) {
            str.append("Country: " + country + '\n');
        }
        if (amountOfPeople != null) {
            str.append("Amount of people: " + amountOfPeople + '\n');
        }
        if (date != null) {
            str.append("Date: " + date + '\n');
        }
        if (additional != null) {
            str.append("Additional information: " + additional + '\n');
        }
        return str.toString();
    }
}
