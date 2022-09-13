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
        return "User{\n" +
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
                "isViewed=" + isViewed + '\n' +
                '}';
    }
}
