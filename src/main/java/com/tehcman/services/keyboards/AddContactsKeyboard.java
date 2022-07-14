package com.tehcman.services.keyboards;

import lombok.NonNull;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AddContactsKeyboard extends ReplyKeyboardMarkup {
    private final AddSkipButtonKeyboardRow addSkipButtonKeyboardRow;

    public AddContactsKeyboard() {
        addSkipButtonKeyboardRow = new AddSkipButtonKeyboardRow();
    }

    public @NonNull List<KeyboardRow> getKeyboard() {
        List<KeyboardRow> arrayOfKeyboardRows = new ArrayList<>();

        var phoneNumber = KeyboardButton.builder().text("Phone number").requestContact(Boolean.TRUE).build();
        var email = KeyboardButton.builder().text("Email").build();
        var row1 = new KeyboardRow();

        var row2 = new KeyboardRow();
        var social = KeyboardButton.builder().text("Social media").build();

        Collections.addAll(row1, phoneNumber, email);
        Collections.addAll(row2, social, this.addSkipButtonKeyboardRow.getKeyboard().get(0).get(0));

        Collections.addAll(arrayOfKeyboardRows, row1, row2);
        return arrayOfKeyboardRows;
    }
}
