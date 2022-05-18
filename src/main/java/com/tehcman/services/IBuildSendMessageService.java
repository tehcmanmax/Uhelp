package com.tehcman.services;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

public interface IBuildSendMessageService {
    SendMessage createHTMLMessage(String chatID, String text, ReplyKeyboard mainMarkup);
}
