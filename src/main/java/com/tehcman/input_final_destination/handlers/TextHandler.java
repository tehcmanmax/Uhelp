package com.tehcman.input_final_destination.handlers;

import com.tehcman.cahce.UserCache;
import com.tehcman.entities.User;
import com.tehcman.input_final_destination.SendMessage_factories.Text1SendMessageFactory;
import com.tehcman.input_final_destination.SendMessage_factories.ISendMessageAbstractFactory;
import com.tehcman.input_final_destination.SendMessage_factories.Text2SendMessageAbstractFactory;
import com.tehcman.printers.HostProfile;
import com.tehcman.printers.RefugeeProfile;
import com.tehcman.sendmessage.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;

@Component
public class TextHandler implements IHandler<Message> {
    private final MessageSender messageSender;
    private final HostProfile hostProfile;
    private final RefugeeProfile refugeeProfile;
    private final Text1SendMessageFactory text1SendMessageFactory;
    private final ISendMessageAbstractFactory create2SendMessagesFactory;
    private final UserCache userCache;


    @Autowired
    public TextHandler(@Lazy MessageSender messageSender, HostProfile hostProfile, RefugeeProfile refugeeProfile, Text1SendMessageFactory text1SendMessageFactory, Text2SendMessageAbstractFactory create2SendMessagesFactory, UserCache userCache) {
        this.messageSender = messageSender;
        this.hostProfile = hostProfile;
        this.refugeeProfile = refugeeProfile;
        this.text1SendMessageFactory = text1SendMessageFactory;
        this.create2SendMessagesFactory = create2SendMessagesFactory;
        this.userCache = userCache;
    }

    @Override
    public void handle(Message message) {
        //handling possible prior message to a user
        if (message.getText().equals("List of news channels about Ukraine (ENG)")) {
            SendMessage msg1 = create2SendMessagesFactory.create1stSendMessage(message);
            SendMessage msg2 = create2SendMessagesFactory.create2ndSendMessage(message);

            messageSender.messageSend(msg1);
            messageSender.messageSend(msg2);

            //TODO behavior: passing the list based on the filter to the inline button message
        } else if ((message.getText().equals("Show me shelter seeking people"))) {
            User user = userCache.findBy(message.getChatId());
            if (user == null) {
                throw new NullPointerException("Complete the registration first!");
            }

            SendMessage msg = SendMessage.builder()
                    .chatId(String.valueOf(message.getChatId()))
                    .replyMarkup(new ReplyKeyboardRemove(true))
                    .text("Showing you people who seek a shelter")
                    .build();
            messageSender.messageSend(msg);

            refugeeProfile.printUserRandomDefault(message);

        } else if ((message.getText().equals("Show me shelter providing people"))) {
            User user = userCache.findBy(message.getChatId());
            if (user == null) {
                throw new NullPointerException("Complete the registration first!");
            }
            SendMessage msg = SendMessage.builder()
                    .chatId(String.valueOf(message.getChatId()))
                    .replyMarkup(new ReplyKeyboardRemove(true))
                    .text("Showing you available hosts")
                    .build();
            messageSender.messageSend(msg);

            hostProfile.printUserRandomDefault(message);

        } else {
            SendMessage newMessageToUser = text1SendMessageFactory.createSendMessage(message);

            messageSender.messageSend(newMessageToUser);
        }
    }
}