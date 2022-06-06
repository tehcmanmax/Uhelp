package com.tehcman.handlers;

import com.tehcman.cahce.Cache;
import com.tehcman.cahce.UserCache;
import com.tehcman.entities.User;
import com.tehcman.informational_portal.GeneralInformation;
import com.tehcman.informational_portal.IListOfNewsChannels;
import com.tehcman.informational_portal.ListOfNewsChannels;
import com.tehcman.sendmessage.MessageSender;
import com.tehcman.services.build_buttons.BuildButtonsService;
import com.tehcman.services.build_buttons.BuildInlineButtonsService;
import com.tehcman.services.build_markup.IMarkup;
import com.tehcman.services.build_markup.MainMarkup;
import com.tehcman.services.build_mess.BuildSendMessageService;
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
    private final GeneralInformation generalInformation;
    private final IMarkup iMarkup;

    private final Cache<User> userCache;

    public String getBotResponse() {
        return botResponse;
    }

    private String botResponse; //used to use for Testing

    @Autowired
    public TextHandler(@Lazy MessageSender messageSender, BuildSendMessageService buildSendMessageService, BuildInlineButtonsService buildInlineButtonsService, @Lazy BuildButtonsService buildButtonsService,
                       MainMarkup mainMarkup, UserCache userCache) {
        this.messageSender = messageSender;
        this.buildSendMessageService = buildSendMessageService;
        this.buildInlineButtonsService = buildInlineButtonsService;
        this.buildButtonsService = buildButtonsService;
        this.iMarkup = mainMarkup;
        this.userCache = userCache;
        this.iListOfNewsChannels = new ListOfNewsChannels();
        this.generalInformation = new GeneralInformation();
    }

    @Override
    public void handle(Message message) {
        if (message.getText().equals("/start")) {
            buildButtonsService.beforeRegistrationButtons();
            this.botResponse = "Yay! You've just launched this bot!";
            messageSender.messageSend(buildSendMessageService.createHTMLMessage(message.getChatId().toString(), botResponse, iMarkup.getMarkup()));
        } else if (message.getText().equals("I want a joke")) {
            this.botResponse = "Are you ready for my collection of the most hilarious jokes??\nIf so, press the button below!";
            var sendMessage = SendMessage.builder()
                    .text(botResponse)
                    .chatId(message.getChatId().toString())
                    .build();
            sendMessage.setReplyMarkup(buildInlineButtonsService.build());
            messageSender.messageSend(sendMessage);
        } else if (message.getText().equals("You're dumb")) {
            messageSender.messageSend(buildSendMessageService.createHTMLMessage(message.getChatId().toString(), "no, you're dumb!", iMarkup.getMarkup()));
        } else if (message.getText().equals("View my data")) {
            User userFromCache = userCache.findBy(message.getChatId());
            messageSender.messageSend(buildSendMessageService.createHTMLMessage(message.getChatId().toString(), userFromCache.toString(), iMarkup.getMarkup()));
        } else if (message.getText().equals("Remove my data")) {
            buildButtonsService.beforeRegistrationButtons();
            userCache.remove(message.getChatId());
            messageSender.messageSend(buildSendMessageService.createHTMLMessage(message.getChatId().toString(), "All data about you has been removed", iMarkup.getMarkup()));
        } else if (message.getText().equals("List of TG news channels on Ukraine (ENG)")) {
            //TODO: POSSIBLE REFACTORING. apply decorator pattern to build message sender
            this.botResponse = iListOfNewsChannels.getMapDescription();

            messageSender.messageSend(buildSendMessageService.createHTMLMessage(message.getChatId().toString(), iListOfNewsChannels.getMapDescription(), iMarkup.getMarkup()));
            String formattedString = "";
            Map<String, String> map = iListOfNewsChannels.getMapOfChannelsAndLinks();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                formattedString += "[" + (entry.getKey()) + "]" + "(" + entry.getValue() + ")\n";
            }
            var newMsg = SendMessage.builder()
                    .text(formattedString)
                    .chatId(message.getChatId().toString())
                    .replyMarkup(iMarkup.getMarkup())
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