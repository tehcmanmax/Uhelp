package com.tehcman.services.build_markup;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;


@Component
public class MainMarkup implements IMarkup{
    private final ReplyKeyboardMarkup mainMarkup;


    public MainMarkup() {
        this.mainMarkup = new ReplyKeyboardMarkup();
        this.mainMarkup.setResizeKeyboard(true);
    }


    @Override
    public ReplyKeyboardMarkup getMarkup() {
        return this.mainMarkup;
    }
}
