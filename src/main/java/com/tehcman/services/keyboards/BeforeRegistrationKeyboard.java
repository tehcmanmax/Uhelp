package com.tehcman.services.keyboards;

import lombok.NonNull;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//dont use use autowire with spring annotation @Service. it will break the code! maybe use @Beans??
public class BeforeRegistrationKeyboard extends ReplyKeyboardMarkup {
    private final DefaultKeyboardRows keyboardRows;

    public BeforeRegistrationKeyboard() {
        this.keyboardRows = new DefaultKeyboardRows();
    }

    public @NonNull List<KeyboardRow> getKeyboard() {
        List<KeyboardRow> arrayOfKeyboardRows = new ArrayList<>();

        var row3 = new KeyboardRow();

        //TODO implement the behavior after pressing on the buttons
//        var button3 = new KeyboardButton("Temporary save my info into the cache");
        var button3 = new KeyboardButton("Accommodation search/hosting");
        row3.add(button3);

        Collections.addAll(arrayOfKeyboardRows, keyboardRows.create1stRow(),/* keyboardRows.create2ndRow(),*/ row3);
        return arrayOfKeyboardRows;
    }

}
