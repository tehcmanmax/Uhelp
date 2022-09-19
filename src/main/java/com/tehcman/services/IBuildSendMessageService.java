package com.tehcman.services;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

public interface IBuildSendMessageService {
    SendMessage createHTMLMessage(String chatID, String text, ReplyKeyboard mainMarkup);

    EditMessageText createHTMLEditMessage(String text, CallbackQuery inlineButtonPressed, InlineKeyboardMarkup mainMarkup);

}
