package com.tehcman.input_final_destination.factories;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface ICreateSendMessageFactory {
    SendMessage createSendMessage(Message message);
}
