package com.tehcman.services.build_keyboards.client;

import com.tehcman.services.build_keyboards.bahaviors.FinishedRegistrationKeyboardAfterBehavior;
import org.springframework.stereotype.Service;

@Service
public class  KeyboardAfterRegistrationClient extends BuildKeyboardClientService{
    public KeyboardAfterRegistrationClient() {
        setiBuildKeyboardBehavior(new FinishedRegistrationKeyboardAfterBehavior());
    }
}
