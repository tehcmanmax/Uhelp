package com.tehcman.services.keyboards.profile_search;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

@Service
public class InlineNewProfilesNotification implements ReplyKeyboard {
    @Override
    public void validate() throws TelegramApiValidationException {

    }
}
