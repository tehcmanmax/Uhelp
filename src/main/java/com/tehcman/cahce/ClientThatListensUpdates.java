package com.tehcman.cahce;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ClientThatListensUpdates {
    private Long userThatListensId; //default 0L

    public ClientThatListensUpdates(Long userThatListensId) {
        this.userThatListensId = userThatListensId;
    }
}
