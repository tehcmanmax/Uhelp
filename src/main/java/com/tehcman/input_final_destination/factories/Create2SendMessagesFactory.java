package com.tehcman.input_final_destination.factories;
//Abstract Factory Method Pattern

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public class Create2SendMessagesFactory implements ICreate2SendMessagesFactory {
    @Override
    public SendMessage createSendMessage(Message message) {
        return null;
    }

    @Override
    public SendMessage create2ndSendMessage(Message message) {
        return null;
    }
}
