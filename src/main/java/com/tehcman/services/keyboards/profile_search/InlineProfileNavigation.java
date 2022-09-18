package com.tehcman.services.keyboards.profile_search;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class InlineProfileNavigation /*extends ReplyKeyboardRemove*/ {
    private final InlineKeyboardMarkup mainMarkup;

    public InlineProfileNavigation() {
//        super(true);
        this.mainMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton backAction = InlineKeyboardButton.builder()
                .text("Back").callbackData("back_action").build();

        InlineKeyboardButton randAction = InlineKeyboardButton.builder()
                .text("Random unique profile").callbackData("rand_action").build();

        InlineKeyboardButton nextAction = InlineKeyboardButton.builder()
                .text("Next").callbackData("next_action").build();

        List<List<InlineKeyboardButton>> listOfInlineButtons = new ArrayList<>();
        ArrayList<InlineKeyboardButton> row1 = new ArrayList<>();
        ArrayList<InlineKeyboardButton> row2 = new ArrayList<>();
//        row1.add(prevAction);
        row1.add(backAction);
        row2.add(randAction);
        row1.add(nextAction);
        Collections.addAll(listOfInlineButtons, row1, row2);

        this.mainMarkup.setKeyboard(listOfInlineButtons);
    }

    public InlineKeyboardMarkup getMainMarkup() {
        return mainMarkup;
    }
}
