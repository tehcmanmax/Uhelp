package com.tehcman.services.keyboards.profile_registration;

import com.tehcman.resources.Emoji;
import lombok.NonNull;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class AddSkipButtonKeyboardRow extends ReplyKeyboardMarkup {


    public @NonNull List<KeyboardRow> getKeyboard() {
        List<KeyboardRow> arrayOfKeyboardRows = new ArrayList<>();

        var row1 = new KeyboardRow();
        row1.add("SKIP " + Emoji.BLACK_RIGHTWARDS_ARROW);
        arrayOfKeyboardRows.add(row1);

        return arrayOfKeyboardRows;
    }
}
