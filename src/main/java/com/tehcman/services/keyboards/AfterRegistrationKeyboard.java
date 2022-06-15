package com.tehcman.services.keyboards;

import lombok.NonNull;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AfterRegistrationKeyboard extends ReplyKeyboardMarkup {
    private final DefaultKeyboardRows keyboardRows;

    public AfterRegistrationKeyboard() {
        this.keyboardRows = new DefaultKeyboardRows();

    }

    public @NonNull List<KeyboardRow> getKeyboard() {
        List<KeyboardRow> arrayOfKeyboardRows = new ArrayList<>();

        var row3 = new KeyboardRow();
        var button3 = new KeyboardButton("View my data");
        var button4 = new KeyboardButton("Remove my data");
        row3.add(button3);
        row3.add(button4);

        Collections.addAll(arrayOfKeyboardRows, keyboardRows.create1stRow(), /*keyboardRows.create2ndRow(),*/ row3);
        return arrayOfKeyboardRows;
    }
}
