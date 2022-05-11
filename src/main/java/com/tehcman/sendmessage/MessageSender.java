package com.tehcman.sendmessage;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;

public interface MessageSender {
    void messageSend(SendMessage sendMessage);
    void editMessageSend(EditMessageText editMessageText);
}
