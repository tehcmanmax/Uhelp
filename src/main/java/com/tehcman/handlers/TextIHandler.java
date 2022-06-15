package com.tehcman.handlers;

import com.tehcman.cahce.Cache;
import com.tehcman.cahce.UserCache;
import com.tehcman.entities.Position;
import com.tehcman.entities.User;
import com.tehcman.informational_portal.GeneralInformation;
import com.tehcman.informational_portal.IListOfNewsChannels;
import com.tehcman.informational_portal.ListOfNewsChannels;
import com.tehcman.sendmessage.MessageSender;
import com.tehcman.services.BuildButtonsService;
import com.tehcman.services.BuildInlineButtonsService;
import com.tehcman.services.BuildSendMessageService;
import com.tehcman.services.keyboards.AfterRegistrationKeyboard;
import com.tehcman.services.keyboards.BeforeRegistrationKeyboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Map;

@Component
public class TextIHandler implements IHandler<Message> {
    private final MessageSender messageSender;
    private final BuildSendMessageService buildSendMessageService;
    private final BuildInlineButtonsService buildInlineButtonsService; //testing the inline buttons
    private BuildButtonsService buildButtonsService;
    private final IListOfNewsChannels iListOfNewsChannels;
    private final GeneralInformation generalInformation;
    private Cache<User> userCache;

    @Autowired
    public void setUserCache(Cache<User> userCache) {
        this.userCache = userCache;
    }

    public String getBotResponseForTesting() {
        return botResponseForTesting;
    }

    private String botResponseForTesting;

    @Autowired
    public TextIHandler(@Lazy MessageSender messageSender, BuildSendMessageService buildSendMessageService, BuildInlineButtonsService buildInlineButtonsService, UserCache userCache) {
        this.messageSender = messageSender;
        this.buildSendMessageService = buildSendMessageService;
        this.buildInlineButtonsService = buildInlineButtonsService;
        this.userCache = userCache;
        this.iListOfNewsChannels = new ListOfNewsChannels();
        this.generalInformation = new GeneralInformation();
    }

    @Override
    public void handle(Message message) {
        if (message.getText().equals("/start")) {
//            buildButtonsService.beforeRegistrationButtons();
            this.buildButtonsService = new BuildButtonsService(new BeforeRegistrationKeyboard());

            this.botResponseForTesting = "Yay! You've just launched this bot!";
            messageSender.messageSend(buildSendMessageService.createHTMLMessage(message.getChatId().toString(), botResponseForTesting, buildButtonsService.getMainMarkup()));
        } else if (message.getText().equals("I want a joke")) {
            this.botResponseForTesting = "Are you ready for my collection of the most hilarious jokes??\nIf so, press the button below!";
            var sendMessage = SendMessage.builder()
                    .text(botResponseForTesting)
                    .chatId(message.getChatId().toString())
                    .build();
            sendMessage.setReplyMarkup(buildInlineButtonsService.build());
            messageSender.messageSend(sendMessage);
        } else if (message.getText().equals("You're dumb")) {
            messageSender.messageSend(buildSendMessageService.createHTMLMessage(message.getChatId().toString(), "no, you're dumb!", buildButtonsService.getMainMarkup()));
        } else if (message.getText().equals("View my data")) {
            User userFromCache = userCache.findBy(message.getChatId());
            this.buildButtonsService = new BuildButtonsService(new AfterRegistrationKeyboard());
            messageSender.messageSend(buildSendMessageService.createHTMLMessage(message.getChatId().toString(), userFromCache.toString(), buildButtonsService.getMainMarkup()));
        } else if (message.getText().equals("Remove my data")) {
//            buildButtonsService.beforeRegistrationButtons();
            this.buildButtonsService = new BuildButtonsService(new BeforeRegistrationKeyboard());
            userCache.remove(message.getChatId());
            messageSender.messageSend(buildSendMessageService.createHTMLMessage(message.getChatId().toString(), "All data about you has been removed", buildButtonsService.getMainMarkup()));
        } else if (message.getText().equals("List of news channels about Ukraine (ENG)")) {
            //TODO: POSSIBLE REFACTORING. apply decorator pattern to build message sender

            this.botResponseForTesting = iListOfNewsChannels.getMapDescription();
            User userFromCache = userCache.findBy(message.getChatId());
            if ((userFromCache != null) && !userFromCache.getPosition().equals(Position.NONE)) {
                this.buildButtonsService = new BuildButtonsService(new AfterRegistrationKeyboard());
            }

            messageSender.messageSend(buildSendMessageService.createHTMLMessage(message.getChatId().toString(), iListOfNewsChannels.getMapDescription(), buildButtonsService.getMainMarkup()));
            String formattedString = "";
            Map<String, String> map = iListOfNewsChannels.getMapOfChannelsAndLinks();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                formattedString += "[" + (entry.getKey()) + "]" + "(" + entry.getValue() + ")\n";
            }
            var newMsg = SendMessage.builder()
                    .text(formattedString)
                    .chatId(message.getChatId().toString())
                    .replyMarkup(buildButtonsService.getMainMarkup())
                    .disableWebPagePreview(Boolean.TRUE)
                    .parseMode("MarkdownV2")
                    .build();
            messageSender.messageSend(newMsg);
        } else if (message.getText().equals("What's going on in Ukraine")) {
            messageSender.messageSend(new SendMessage(message.getChatId().toString(), generalInformation.getGeneralInformation()));
        } else {
            var sendMsg = new SendMessage(message.getChatId().toString(), "I did not understand you. Try to press/text something else");
            messageSender.messageSend(sendMsg);
        }
    }
}

/*Resources
 * inserting link into a text https://over.wiki/ask/how-to-make-a-hyperlink-in-a-word-for-a-telegram-bot-in-python/
 * iterating through map(dictionary) https://stackoverflow.com/questions/46898/how-do-i-efficiently-iterate-over-each-entry-in-a-java-map
 */