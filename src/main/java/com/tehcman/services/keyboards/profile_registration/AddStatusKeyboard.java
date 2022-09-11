package com.tehcman.services.keyboards.profile_registration;

import lombok.NonNull;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AddStatusKeyboard extends ReplyKeyboardMarkup {
    public @NonNull List<KeyboardRow> getKeyboard() {
        List<KeyboardRow> arrayOfKeyboardRows = new ArrayList<>();

        var searching = KeyboardButton.builder().text("Searching Accommodation").build();
        var providing = KeyboardButton.builder().text("Providing Accommodation").build();
        var row1 = new KeyboardRow();
        Collections.addAll(row1, searching, providing);

        arrayOfKeyboardRows.add(row1);
        return arrayOfKeyboardRows;
    }
}
