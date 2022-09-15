package com.tehcman.services.keyboards.profile_search;

import lombok.Getter;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Service
public class InlineNoProfiles {
    @Getter
    private final InlineKeyboardMarkup mainMarkup;

    public InlineNoProfiles() {
        this.mainMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton backAction = InlineKeyboardButton.builder()
                .text("View again").callbackData("view_again_action").build();

        InlineKeyboardButton notifyAction = InlineKeyboardButton.builder()
                .text("Notify me").callbackData("notification_action").build();

        List<List<InlineKeyboardButton>> listOfInlineButtons = new ArrayList<>();
        ArrayList<InlineKeyboardButton> row1 = new ArrayList<>();

        row1.add(backAction);
        row1.add(notifyAction);
        listOfInlineButtons.add(row1);

        this.mainMarkup.setKeyboard(listOfInlineButtons);
    }

    public InlineKeyboardMarkup getMainMarkup() {
        return mainMarkup;
    }
}
