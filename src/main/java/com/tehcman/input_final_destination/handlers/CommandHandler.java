package com.tehcman.input_final_destination.handlers;

import com.tehcman.cahce.Cache;
import com.tehcman.entities.Phase;
import com.tehcman.entities.User;
import com.tehcman.sendmessage.MessageSender;
import com.tehcman.services.BuildButtonsService;
import com.tehcman.services.BuildSendMessageService;
import com.tehcman.services.keyboards.BeforeRegistrationKeyboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class CommandHandler implements IHandler<Message> {

    private BuildButtonsService buildButtonsService;
    private final Cache<User> userCache;
    private final BuildSendMessageService buildSendMessageService;
    private final MessageSender messageSender;

    @Autowired
    public CommandHandler(Cache<User> userCache, BuildSendMessageService buildSendMessageService, MessageSender messageSender) {
        this.userCache = userCache;
        this.buildSendMessageService = buildSendMessageService;
        this.messageSender = messageSender;
    }

    @Override
    public void handle(Message message) {
        String userCommand = message.getText();

        switch (userCommand) {
            case "/start":
//            buildButtonsService.beforeRegistrationButtons();
                this.buildButtonsService = new BuildButtonsService(new BeforeRegistrationKeyboard());

                User userFromCache = userCache.findBy(message.getChatId());
                if ((userFromCache != null) && (userFromCache.getPhase() != Phase.NONE)) {
                    userCache.remove(userFromCache.getId());
                    SendMessage msgForUsr = buildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Yay! You've just removed all your data!", buildButtonsService.getMainMarkup());
                    messageSender.messageSend(msgForUsr);
                } else {
                    SendMessage msgForUsr = buildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Yay! You've just launched this bot!", buildButtonsService.getMainMarkup());
                    messageSender.messageSend(msgForUsr);
                }
        }

    }
}
