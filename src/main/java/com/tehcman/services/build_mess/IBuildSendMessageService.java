package com.tehcman.services.build_mess;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

public interface IBuildSendMessageService {
    SendMessage getSendMessage(String chatID, String text, ReplyKeyboard replyKeyboard);
}
