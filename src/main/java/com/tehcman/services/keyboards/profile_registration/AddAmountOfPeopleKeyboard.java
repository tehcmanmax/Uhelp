package com.tehcman.services.keyboards.profile_registration;

import lombok.NonNull;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AddAmountOfPeopleKeyboard extends ReplyKeyboardMarkup {
    private final AddSkipButtonKeyboardRow addSkipButtonKeyboardRow;

    public AddAmountOfPeopleKeyboard() {
        addSkipButtonKeyboardRow = new AddSkipButtonKeyboardRow();
    }

    public @NonNull List<KeyboardRow> getKeyboard() {
        List<KeyboardRow> arrayOfKeyboardRows = new ArrayList<>();

        var me = KeyboardButton.builder().text("I'm alone").build();
        var many = KeyboardButton.builder().text("A group of people").build();
        var row1 = new KeyboardRow();
        Collections.addAll(row1, me, many);

        Collections.addAll(arrayOfKeyboardRows, row1, this.addSkipButtonKeyboardRow.getKeyboard().get(0));
        return arrayOfKeyboardRows;
    }

}
