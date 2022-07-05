package com.tehcman.handlers;

import com.tehcman.handlers.factories.TextFactory;
import com.tehcman.sendmessage.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class TextHandler implements IHandler<Message> {
    private final MessageSender messageSender;
    private final TextFactory textFactory;

    @Autowired
    public TextHandler(@Lazy MessageSender messageSender, TextFactory textFactory) {
        this.messageSender = messageSender;
        this.textFactory = textFactory;
    }

    @Override
    public void handle(Message message) {
        SendMessage newMessageToUser = textFactory.createSendMessage(message);
        messageSender.messageSend(newMessageToUser);
    }
}

/*Resources
 * inserting link into a text https://over.wiki/ask/how-to-make-a-hyperlink-in-a-word-for-a-telegram-bot-in-python/
 * iterating through map(dictionary) https://stackoverflow.com/questions/46898/how-do-i-efficiently-iterate-over-each-entry-in-a-java-map
 */