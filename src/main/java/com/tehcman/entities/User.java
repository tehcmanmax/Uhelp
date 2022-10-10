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
import org.hibernate.annotations.NaturalId;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@JsonIgnoreProperties({"phase"})
public class User {
    public User() {
    }

    //fill the following fields from the user's message
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private /*final*/ Long id;

    @Getter
    @Setter
    private /*final*/ String tgUsername;

    @Getter
    @Setter
    @JsonProperty("isViewed")
    private boolean isViewed;

    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
//    @JsonIgnore
    private Phase phase;

    @Setter
    @Getter
    private String phoneNumber;
    @Setter
    @Getter
    private String email;
    @Setter
    @Getter
    private String social;

    @Setter
    @Getter
    private String age;


    //sprint 2:
    @Getter
    @Setter
    private Status status;
    @Setter
    @Getter
    private Character sex;
    @Setter
    @Getter
    private String city;
    @Setter
    @Getter
    private String country;
    @Setter
    @Getter
    private Integer amountOfPeople;
    @Setter
    @Getter
    private String date;
    @Setter
    @Getter
    private String additional;

    @Getter
    @Setter
    private Long chatId;



    public User(Long id, String tgUsername, String name, Phase phase) {
        this.chatId = id;
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
            str.append("Name: ").append(name).append('\n');
        }
        if (phoneNumber != null) {
            str.append("Phone number: ").append(phoneNumber).append('\n');
        }
        if (email != null) {
            str.append("Email").append(email);
        }
        if (social != null) {
            str.append("Social: ").append(social).append('\n');
        }
        if (phoneNumber != null) {
            str.append("Phone number: ").append(phoneNumber).append('\n');
        }
        if (age != null) {
            str.append("Age: ").append(age).append('\n');
        }
        if (sex != null) {
            str.append("Sex: ").append(sex).append('\n');
        }
        if (city != null) {
            str.append("City: ").append(city).append('\n');
        }
        if (country != null) {
            str.append("Country: ").append(country).append('\n');
        }
        if (amountOfPeople != null) {
            str.append("Amount of people: ").append(amountOfPeople).append('\n');
        }
        if (date != null) {
            str.append("Date: ").append(date).append('\n');
        }
        if (additional != null) {
            str.append("Additional information: ").append(additional).append('\n');
        }
        return str.toString();
    }
}