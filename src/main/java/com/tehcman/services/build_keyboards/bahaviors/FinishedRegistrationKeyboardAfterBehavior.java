package com.tehcman.services.build_keyboards.bahaviors;

import com.tehcman.services.build_keyboards.bahaviors.keyboard_row.IStandardKeyboardRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class FinishedRegistrationKeyboardAfterBehavior implements IBuildKeyboardBehavior{
    private IStandardKeyboardRow firstRow;
    private IStandardKeyboardRow secondRow;

    public FinishedRegistrationKeyboardAfterBehavior() {
    }

    @Override
    public List<KeyboardRow> build() {
        List<KeyboardRow> arrayOfKeyboardRows = new ArrayList<>();

        var row3 = new KeyboardRow();
        var button3 = new KeyboardButton("View my data");
        var button4 = new KeyboardButton("Remove my data");
        row3.add(button3);
        row3.add(button4);

        Collections.addAll(arrayOfKeyboardRows, firstRow.getKeyboardRow(), secondRow.getKeyboardRow(), row3);

        return arrayOfKeyboardRows;
    }

    @Autowired
    public void setFirstRow(IStandardKeyboardRow firstRow) {
        this.firstRow = firstRow;
    }

    @Autowired
    public void setSecondRow(IStandardKeyboardRow secondRow) {
        this.secondRow = secondRow;
    }
}
