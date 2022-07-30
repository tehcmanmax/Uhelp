package com.tehcman.input_final_destination.SendMessage_factories;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface ISendMessageFactory {
    SendMessage createSendMessage(Message message);
}
