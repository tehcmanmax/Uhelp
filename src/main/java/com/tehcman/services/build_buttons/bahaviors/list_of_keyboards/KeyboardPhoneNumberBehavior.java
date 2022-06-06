package com.tehcman.services.build_buttons.bahaviors.list_of_keyboards;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class KeyboardPhoneNumberBehavior implements IBuildKeyboardBehavior {
    @Override
    public List<KeyboardRow> build() {
        List<KeyboardRow> arrayOfKeyboardRows = new ArrayList<>();

        var phoneNumberButton = KeyboardButton.builder().text("Phone number").requestContact(Boolean.TRUE).build();
        var declineSharingPhoneNumber = KeyboardButton.builder().text("I don't want to disclose the phone number").build();
        var row1 = new KeyboardRow();
        Collections.addAll(row1, phoneNumberButton, declineSharingPhoneNumber);

        arrayOfKeyboardRows.add(row1);

        return arrayOfKeyboardRows;
    }
}
