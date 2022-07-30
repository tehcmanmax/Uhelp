package com.tehcman.input_final_destination.handlers;

import com.tehcman.input_final_destination.SendMessage_factories.*;
import com.tehcman.sendmessage.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class TextHandler implements IHandler<Message> {
    private final MessageSender messageSender;
    private final ISendMessageFactory text1SendMessageFactory;
    private final ISendMessageAbstractFactory create2SendMessagesFactory;
    private final CacheFactoryRefugee cacheFactoryRefugee;


    @Autowired
    public TextHandler(@Lazy MessageSender messageSender, Text1SendMessageFactory text1SendMessageFactory, Text2SendMessageAbstractFactory create2SendMessagesFactory, CacheFactoryRefugee cacheFactoryRefugee) {
        this.messageSender = messageSender;
        this.text1SendMessageFactory = text1SendMessageFactory;
        this.create2SendMessagesFactory = create2SendMessagesFactory;
        this.cacheFactoryRefugee = cacheFactoryRefugee;
    }

    @Override
    public void handle(Message message) {
        //handling possible prior message to a user
        if (message.getText().equals("List of news channels about Ukraine (ENG)")) {
            SendMessage msg1 = create2SendMessagesFactory.create1stSendMessage(message);
            SendMessage msg2 = create2SendMessagesFactory.create2ndSendMessage(message);

            messageSender.messageSend(msg1);
            messageSender.messageSend(msg2);
        } else {
            if (message.getText().equals("Searching Accommodation")) {
                cacheFactoryRefugee.createSendMessage(message);
            }
            SendMessage newMessageToUser = text1SendMessageFactory.createSendMessage(message);

            messageSender.messageSend(newMessageToUser);
        }
    }
}