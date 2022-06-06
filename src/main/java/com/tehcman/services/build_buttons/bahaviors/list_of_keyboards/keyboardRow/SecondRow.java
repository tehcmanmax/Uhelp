package com.tehcman.services.build_buttons.bahaviors.list_of_keyboards.keyboardRow;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;


@Service
public class SecondRow implements IStandardKeyboardRow{
    @Override
    public KeyboardRow getKeyboardRow() {
        var row2 = new KeyboardRow();
        row2.add("Donation (links)");
        row2.add("Change language");
        return row2;
    }
}
