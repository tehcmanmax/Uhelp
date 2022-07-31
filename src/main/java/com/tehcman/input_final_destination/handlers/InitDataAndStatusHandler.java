package com.tehcman.input_final_destination.handlers;

import com.tehcman.input_final_destination.SendMessage_factories.ISendMessageFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class InitDataAndStatusHandler implements ISendMessageFactory {

    @Override
    public SendMessage createSendMessage(Message message) {
        return null;
    }
}
