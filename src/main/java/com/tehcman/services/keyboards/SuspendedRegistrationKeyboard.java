package com.tehcman.services.keyboards;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class SuspendedRegistrationKeyboard extends ReplyKeyboardMarkup {
    private final DefaultKeyboardRows keyboardRows;
    private boolean isSuspended;

    public SuspendedRegistrationKeyboard() {
        this.keyboardRows = new DefaultKeyboardRows();
        this.isSuspended = false;
    }

    public @NonNull List<KeyboardRow> getKeyboard() {
        List<KeyboardRow> arrayOfKeyboardRows = new ArrayList<>();

        var row3 = new KeyboardRow();

        var button3 = new KeyboardButton("Continue the registration");
        row3.add(button3);

        Collections.addAll(arrayOfKeyboardRows, keyboardRows.create1stRow(), row3);
        return arrayOfKeyboardRows;
    }

    public boolean getSuspended() {
        return isSuspended;
    }

    public void setSuspended(boolean value){
        this.isSuspended = value;
    }
}
