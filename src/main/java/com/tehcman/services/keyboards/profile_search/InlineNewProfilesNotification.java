package com.tehcman.services.keyboards.profile_search;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.util.ArrayList;
import java.util.List;

@Service
public class InlineNewProfilesNotification {
    private final InlineKeyboardMarkup mainMarkup;

    public InlineNewProfilesNotification() {
        this.mainMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton yesAction = InlineKeyboardButton.builder()
                .text("Yes").callbackData("yes_show_new_profiles_action").build();

        InlineKeyboardButton noAction = InlineKeyboardButton.builder()
                .text("No, open a random profile").callbackData("view_again_action").build();

        List<List<InlineKeyboardButton>> listOfInlineButtons = new ArrayList<>();
        ArrayList<InlineKeyboardButton> row1 = new ArrayList<>();

        row1.add(yesAction);
        row1.add(noAction);
        listOfInlineButtons.add(row1);

        this.mainMarkup.setKeyboard(listOfInlineButtons);
    }

    public InlineKeyboardMarkup getMainMarkup() {
        return mainMarkup;
    }
}
