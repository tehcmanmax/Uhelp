package com.tehcman.input_final_destination.factories;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface ICreate2SendMessagesFactory {
    SendMessage create1stSendMessage(Message message);
    SendMessage create2ndSendMessage(Message message);

}
