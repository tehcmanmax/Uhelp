package com.tehcman.services.build_keyboards.client;

import com.tehcman.services.build_keyboards.bahaviors.InitialKeyboardBehavior;
import org.springframework.stereotype.Service;

@Service
public class KeyboardBeforeRegistrationClient extends BuildKeyboardClientService{

    public KeyboardBeforeRegistrationClient() {
        setiBuildKeyboardBehavior(new InitialKeyboardBehavior());
    }
}
