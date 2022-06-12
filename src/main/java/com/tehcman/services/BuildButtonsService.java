package com.tehcman.services;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

public class BuildButtonsService {
    private final ReplyKeyboardMarkup mainMarkup;
    ReplyKeyboardMarkup replyKeyboardMarkup; //removes the phone number keyboard


    public ReplyKeyboardMarkup getMainMarkup() {
        return mainMarkup;
    }

    public BuildButtonsService(ReplyKeyboardMarkup replyKeyboardMarkup) {
        List<KeyboardRow> arrayOfKeyboardRows = replyKeyboardMarkup.getKeyboard();
        this.mainMarkup = new ReplyKeyboardMarkup();

        this.replyKeyboardMarkup = replyKeyboardMarkup;

        //prettifies the buttons
        this.mainMarkup.setKeyboard(arrayOfKeyboardRows);
        this.mainMarkup.setResizeKeyboard(true);
    }
}