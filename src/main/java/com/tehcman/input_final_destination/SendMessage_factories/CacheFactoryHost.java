package com.tehcman.input_final_destination.SendMessage_factories;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class CacheFactoryHost implements ISendMessageFactory {
    @Override
    public SendMessage createSendMessage(Message message) {
        return null;
    }
}
