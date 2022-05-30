package com.tehcman.handlers;

import com.tehcman.cahce.Cache;
import com.tehcman.cahce.UserCache;
import com.tehcman.entities.User;
import com.tehcman.informational.portal.IListOfNewsChannels;
import com.tehcman.informational.portal.ListOfNewsChannels;
import com.tehcman.sendmessage.MessageSender;
import com.tehcman.services.BuildButtonsService;
import com.tehcman.services.BuildInlineButtonsService;
import com.tehcman.services.BuildSendMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Map;

@Component
public class TextHandler implements Handler<Message> {
    private final MessageSender messageSender;
    private final BuildSendMessageService buildSendMessageService;
    private final BuildInlineButtonsService buildInlineButtonsService; //testing the inline buttons
    private final BuildButtonsService buildButtonsService;
    private IListOfNewsChannels iListOfNewsChannels;

    private final Cache<User> userCache;

    @Autowired
    public TextHandler(@Lazy MessageSender messageSender, BuildSendMessageService buildSendMessageService, BuildInlineButtonsService buildInlineButtonsService, @Lazy BuildButtonsService buildButtonsService, UserCache userCache) {
        this.messageSender = messageSender;
        this.buildSendMessageService = buildSendMessageService;
        this.buildInlineButtonsService = buildInlineButtonsService;
        this.buildButtonsService = buildButtonsService;
        this.userCache = userCache;
        this.iListOfNewsChannels = new ListOfNewsChannels();
    }

    @Override
    public void handle(Message message) {
        if (message.getText().equals("/start")) {
            buildButtonsService.beforeRegistrationButtons();
            messageSender.messageSend(buildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Yay! You've just launched this bot!", buildButtonsService.getMainMarkup()));
        } else if (message.getText().equals("I want a joke")) {
            var sendMessage = SendMessage.builder()
                    .text("Are you ready for my collection of the most hilarious jokes??\nIf so, press the button below!")
                    .chatId(message.getChatId().toString())
                    .build();
            sendMessage.setReplyMarkup(buildInlineButtonsService.build());
            messageSender.messageSend(sendMessage);
        } else if (message.getText().equals("You're dumb")) {
            messageSender.messageSend(buildSendMessageService.createHTMLMessage(message.getChatId().toString(), "no, you're dumb!", buildButtonsService.getMainMarkup()));
        } else if (message.getText().equals("View my data")) {
            User userFromCache = userCache.findBy(message.getChatId());
            messageSender.messageSend(buildSendMessageService.createHTMLMessage(message.getChatId().toString(), userFromCache.toString(), buildButtonsService.getMainMarkup()));
        } else if (message.getText().equals("Remove my data")) {
            buildButtonsService.beforeRegistrationButtons();
            userCache.remove(message.getChatId());
            messageSender.messageSend(buildSendMessageService.createHTMLMessage(message.getChatId().toString(), "All data about you has been removed", buildButtonsService.getMainMarkup()));
        } else if (message.getText().equals("List of TG news channels on Ukraine (ENG)")) {
            //TODO: POSSIBLE REFACTORING. apply decorator pattern to build message sender
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