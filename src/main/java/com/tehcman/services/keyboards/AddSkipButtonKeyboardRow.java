package com.tehcman.services.keyboards;

import com.tehcman.services.Emoji;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

public class AddSkipButtonKeyboardRow extends ReplyKeyboardMarkup {


    public KeyboardRow create2ndSkipButtonRow() {
        var row1 = new KeyboardRow();
        row1.add("SKIP " + Emoji.BLACK_RIGHTWARDS_ARROW);
        return row1;
    }
}
