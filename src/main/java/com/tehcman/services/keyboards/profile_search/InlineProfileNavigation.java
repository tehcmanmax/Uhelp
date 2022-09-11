package com.tehcman.services.keyboards.profile_search;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Service
public class InlineProfileNavigation {
    public InlineKeyboardMarkup build() {
        var keyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton nextAction = InlineKeyboardButton.builder()
                .text("Next").callbackData("next_action").build();

        InlineKeyboardButton backAction = InlineKeyboardButton.builder()
                .text("Back").callbackData("back_action").build();

        InlineKeyboardButton randAction = InlineKeyboardButton.builder()
                .text("Random profile").callbackData("rand_action").build();

        List<List<InlineKeyboardButton>> listOfInlineButtons = new ArrayList<>();
        ArrayList<InlineKeyboardButton> row1 = new ArrayList<>();
//        row1.add(prevAction);
        row1.add(backAction);
        row1.add(randAction);
        row1.add(nextAction);
        listOfInlineButtons.add(row1);

        keyboardMarkup.setKeyboard(listOfInlineButtons);
        return keyboardMarkup;
    }

}
