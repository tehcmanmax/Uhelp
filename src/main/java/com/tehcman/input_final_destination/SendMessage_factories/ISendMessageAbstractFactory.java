package com.tehcman.input_final_destination.SendMessage_factories;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface ISendMessageAbstractFactory {
    SendMessage create1stSendMessage(Message message);
    SendMessage create2ndSendMessage(Message message);

}
