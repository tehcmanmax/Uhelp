package com.tehcman.services.keyboards.profile_registration;

import lombok.NonNull;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AddSexKeyboard extends ReplyKeyboardMarkup {
    private final AddSkipButtonKeyboardRow addSkipButtonKeyboardRow;

    public AddSexKeyboard() {
        addSkipButtonKeyboardRow = new AddSkipButtonKeyboardRow();
    }

    public @NonNull List<KeyboardRow> getKeyboard() {
        List<KeyboardRow> arrayOfKeyboardRows = new ArrayList<>();

        var male = KeyboardButton.builder().text("Male").build();
        var female = KeyboardButton.builder().text("Female").build();
        var row1 = new KeyboardRow();
        Collections.addAll(row1, male, female);

        arrayOfKeyboardRows.add(row1);
        arrayOfKeyboardRows.add(this.addSkipButtonKeyboardRow.getKeyboard().get(0));
        return arrayOfKeyboardRows;
    }
}
