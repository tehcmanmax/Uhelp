package com.tehcman.input_final_destination.handlers;

import com.tehcman.input_final_destination.factories.ICreate1SendMessageFactory;
import com.tehcman.input_final_destination.factories.ICreate2SendMessagesFactory;
import com.tehcman.sendmessage.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class TextHandler implements IHandler<Message> {
    private final MessageSender messageSender;
    private final ICreate1SendMessageFactory create1SendMessageFactory;
    private final ICreate2SendMessagesFactory create2SendMessagesFactory;


    @Autowired
    public TextHandler(@Lazy MessageSender messageSender, ICreate1SendMessageFactory create1SendMessageFactory, ICreate2SendMessagesFactory create2SendMessagesFactory) {
        this.messageSender = messageSender;
        this.create1SendMessageFactory = create1SendMessageFactory;
        this.create2SendMessagesFactory = create2SendMessagesFactory;
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
            SendMessage newMessageToUser = create1SendMessageFactory.createSendMessage(message);

            messageSender.messageSend(newMessageToUser);
        }
    }
}

/*Resources
 * inserting link into a text https://over.wiki/ask/how-to-make-a-hyperlink-in-a-word-for-a-telegram-bot-in-python/
 * iterating through map(dictionary) https://stackoverflow.com/questions/46898/how-do-i-efficiently-iterate-over-each-entry-in-a-java-map
 */