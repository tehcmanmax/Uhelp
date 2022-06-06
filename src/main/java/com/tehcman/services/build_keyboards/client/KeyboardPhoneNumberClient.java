package com.tehcman.services.build_keyboards.client;

import com.tehcman.services.build_keyboards.bahaviors.AddingPhoneNumberBehavior;
import org.springframework.stereotype.Service;

@Service
public class KeyboardPhoneNumberClient extends BuildKeyboardClientService{
    public KeyboardPhoneNumberClient() {
        setiBuildKeyboardBehavior(new AddingPhoneNumberBehavior());
    }
}
