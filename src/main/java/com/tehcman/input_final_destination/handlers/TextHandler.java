package com.tehcman.input_final_destination.handlers;

import com.tehcman.cahce.Cache;
import com.tehcman.cahce.UserCache;
import com.tehcman.entities.Position;
import com.tehcman.entities.User;
import com.tehcman.informational_portal.IListOfNewsChannels;
import com.tehcman.informational_portal.ListOfNewsChannels;
import com.tehcman.input_final_destination.factories.TextFactory;
import com.tehcman.sendmessage.MessageSender;
import com.tehcman.services.BuildButtonsService;
import com.tehcman.services.BuildSendMessageService;
import com.tehcman.services.keyboards.AfterRegistrationKeyboard;
import com.tehcman.services.keyboards.BeforeRegistrationKeyboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class TextHandler implements IHandler<Message> {
    private final MessageSender messageSender;
    private final TextFactory textFactory;
    private final Cache<User> userCache;
    private final BuildSendMessageService buildSendMessageService;
    private final IListOfNewsChannels iListOfNewsChannels;


    @Autowired
    public TextHandler(@Lazy MessageSender messageSender, TextFactory textFactory, UserCache userCache, BuildSendMessageService buildSendMessageService) {
        this.messageSender = messageSender;
        this.textFactory = textFactory;
        this.userCache = userCache;
        this.buildSendMessageService = buildSendMessageService;
        iListOfNewsChannels = new ListOfNewsChannels();
    }

    @Override
    public void handle(Message message) {
        //handling possible prior message to a user
        if(message.getText().equals("List of news channels about Ukraine (ENG)")){
            User userFromCache = userCache.findBy(message.getChatId());
            BuildButtonsService buildButtonsService;
            if ((userFromCache != null) && !userFromCache.getPosition().equals(Position.NONE)) {
                buildButtonsService = new BuildButtonsService(new AfterRegistrationKeyboard());
            }
            else buildButtonsService = new BuildButtonsService(new BeforeRegistrationKeyboard());
            messageSender.messageSend(buildSendMessageService.createHTMLMessage(message.getChatId().toString(), iListOfNewsChannels.getMapDescription(), buildButtonsService.getMainMarkup()));
        }

        //handling a message to a user
        SendMessage newMessageToUser = textFactory.createSendMessage(message);
        messageSender.messageSend(newMessageToUser);
    }
}

/*Resources
 * inserting link into a text https://over.wiki/ask/how-to-make-a-hyperlink-in-a-word-for-a-telegram-bot-in-python/
 * iterating through map(dictionary) https://stackoverflow.com/questions/46898/how-do-i-efficiently-iterate-over-each-entry-in-a-java-map
 */