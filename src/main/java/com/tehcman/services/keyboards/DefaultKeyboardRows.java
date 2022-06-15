package com.tehcman.services.keyboards;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

@Service
public class DefaultKeyboardRows {
    public KeyboardRow create1stRow() {
        var row1 = new KeyboardRow();
        row1.add("What's going on in Ukraine");
        row1.add("List of news channels about Ukraine (ENG)");
        return row1;
    }

    public KeyboardRow create2ndRow() {
        var row2 = new KeyboardRow();
        row2.add("Donation (links)");
        row2.add("Change language");
        return row2;
    }
}
