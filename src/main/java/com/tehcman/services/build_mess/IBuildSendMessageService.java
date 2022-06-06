package com.tehcman.services.build_mess;

import com.tehcman.services.build_markup.MainMarkup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

public interface IBuildSendMessageService {
    SendMessage createHTMLMessage(String chatID, String text, ReplyKeyboard replyKeyboard);
}
