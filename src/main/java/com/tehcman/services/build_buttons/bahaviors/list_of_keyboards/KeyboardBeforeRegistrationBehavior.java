package com.tehcman.services.build_buttons.bahaviors.list_of_keyboards;

import com.tehcman.services.build_buttons.bahaviors.list_of_keyboards.keyboardRow.FirstRow;
import com.tehcman.services.build_buttons.bahaviors.list_of_keyboards.keyboardRow.IStandardKeyboardRow;
import com.tehcman.services.build_buttons.bahaviors.list_of_keyboards.keyboardRow.SecondRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class KeyboardBeforeRegistrationBehavior implements IBuildKeyboardBehavior {
    private final IStandardKeyboardRow firstRow;
    private final IStandardKeyboardRow secondRow;

    @Autowired
    public KeyboardBeforeRegistrationBehavior(FirstRow firstRow, SecondRow secondRow) {
        this.firstRow = firstRow;
        this.secondRow = secondRow;
    }

    @Override
    public List<KeyboardRow> build() {
        List<KeyboardRow> arrayOfKeyboardRows = new ArrayList<>();

        var row3 = new KeyboardRow();
        var button3 = new KeyboardButton("Accommodation search/hosting");
        row3.add(button3);

        Collections.addAll(arrayOfKeyboardRows, firstRow.getKeyboardRow(), secondRow.getKeyboardRow(), row3);
        return arrayOfKeyboardRows;
    }
}
