package com.tehcman.input_final_destination.handlers;

import com.tehcman.input_final_destination.SendMessage_factories.CacheFactory;
import com.tehcman.input_final_destination.SendMessage_factories.ISendMessageFactory;
import com.tehcman.sendmessage.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class SaveToCacheIHandler implements IHandler<Message> {

    private final MessageSender messageSender;
    private final ISendMessageFactory cacheFactory;

    @Autowired
    public SaveToCacheIHandler(MessageSender messageSender, CacheFactory cacheFactory) {
        this.messageSender = messageSender;
        this.cacheFactory = cacheFactory;
    }



    @Override
    public void handle(Message message) {
        SendMessage newMsg = cacheFactory.createSendMessage(message);
        messageSender.messageSend(newMsg);
    }
}
