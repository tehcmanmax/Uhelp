package com.tehcman.services.keyboards;

import lombok.NonNull;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AddPhoneNumberKeyboard extends ReplyKeyboardMarkup {

    public @NonNull List<KeyboardRow> getKeyboard() {
        List<KeyboardRow> arrayOfKeyboardRows = new ArrayList<>();

        var phoneNumberButton = KeyboardButton.builder().text("Phone number").requestContact(Boolean.TRUE).build();
        var declineSharingPhoneNumber = KeyboardButton.builder().text("I don't want to disclose the phone number").build();
        var row1 = new KeyboardRow();
        Collections.addAll(row1, phoneNumberButton, declineSharingPhoneNumber);

        arrayOfKeyboardRows.add(row1);
        return arrayOfKeyboardRows;
    }
}
