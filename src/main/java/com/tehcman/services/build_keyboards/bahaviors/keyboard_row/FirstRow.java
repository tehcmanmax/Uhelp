package com.tehcman.services.build_keyboards.bahaviors.keyboard_row;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

@Service
public class FirstRow implements IStandardKeyboardRow{
    @Override
    public KeyboardRow getKeyboardRow() {
        var row1 = new KeyboardRow();
        row1.add("What's going on in Ukraine");
        row1.add("List of TG news channels on Ukraine (ENG)");
        return row1;
    }
}
