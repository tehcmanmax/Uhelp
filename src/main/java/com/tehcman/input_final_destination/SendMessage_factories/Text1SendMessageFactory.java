package com.tehcman.input_final_destination.SendMessage_factories;
//FACTORY METHOD PATTERN

import com.tehcman.cahce.Cache;
import com.tehcman.cahce.UserCache;
import com.tehcman.entities.Status;
import com.tehcman.entities.User;
import com.tehcman.informational_portal.GeneralInformation;
import com.tehcman.informational_portal.IListOfNewsChannels;
import com.tehcman.informational_portal.ListOfNewsChannels;
import com.tehcman.services.BuildButtonsService;
import com.tehcman.services.BuildSendMessageService;
import com.tehcman.services.keyboards.profile_registration.AddContactsKeyboard;
import com.tehcman.services.keyboards.AfterRegistrationKeyboard;
import com.tehcman.services.keyboards.BeforeRegistrationKeyboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
public class Text1SendMessageFactory implements ISendMessageFactory {
    private final BuildSendMessageService buildSendMessageService;
    private BuildButtonsService buildButtonsService;
    private final IListOfNewsChannels iListOfNewsChannels;
    private GeneralInformation generalInformation;
    private final Cache<User> userCache;
    private final CacheFactoryRefugee cacheFactoryRefugee;
    private final CacheFactoryHost cacheFactoryHost;


    @Autowired
    Text1SendMessageFactory(BuildSendMessageService buildSendMessageService, UserCache userCache, CacheFactoryRefugee cacheFactoryRefugee, CacheFactoryHost cacheFactoryHost) {
        this.buildSendMessageService = buildSendMessageService;

        this.userCache = userCache;
        this.cacheFactoryRefugee = cacheFactoryRefugee;
        this.cacheFactoryHost = cacheFactoryHost;
        this.iListOfNewsChannels = new ListOfNewsChannels();
        this.generalInformation = new GeneralInformation();
    }
/*
    @Autowired
    public void setUserCache(Cache<User> userCache) {
        this.userCache = userCache;
    }*/


    @Override
    public SendMessage createSendMessage(Message message) {
        if (message.getText().equals("View my data")) {
            User userFromCache = userCache.findBy(message.getChatId());
            this.buildButtonsService = new BuildButtonsService(new AfterRegistrationKeyboard(message, userCache));
            return buildSendMessageService.createHTMLMessage(message.getChatId().toString(), userFromCache.toString(), buildButtonsService.getMainMarkup());
        } else if (message.getText().equals("Remove my data")) {
//            buildButtonsService.beforeRegistrationButtons();
            this.cacheFactoryRefugee.setAddContactsKeyboard(new AddContactsKeyboard()); //recreates deleted contacts keyboard
            this.cacheFactoryHost.setAddContactsKeyboard(new AddContactsKeyboard());

            this.buildButtonsService = new BuildButtonsService(new BeforeRegistrationKeyboard());
            userCache.remove(message.getChatId());
            return buildSendMessageService.createHTMLMessage(message.getChatId().toString(), "All data about you has been removed", buildButtonsService.getMainMarkup());
        } else if (message.getText().equals("List of news channels about Ukraine (ENG)")) {
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
            return newMsg;
        } else if (message.getText().equals("What's going on in Ukraine")) {
            generalInformation = new GeneralInformation();
            return new SendMessage(message.getChatId().toString(), generalInformation.getGeneralInformation());
        } else {
            return new SendMessage(message.getChatId().toString(), "I did not understand you. Try to press/text something else");
        }
    }

    public SendMessage sendRefugeeProfiles(Message message) {
        List<User> refugeeTable;
//        refugeeTable = userCache.getAll().forEach(user -> user.getStatus().equals(Status.REFUGEE));
        Predicate<User> byStatus = user -> user.getStatus().equals(Status.REFUGEE);
        refugeeTable = userCache.getAll().stream().filter(byStatus).collect(Collectors.toList());

        var newMsg = SendMessage.builder()
                .text(refugeeTable.toString())
                .chatId(message.getChatId().toString())
                .replyMarkup(buildButtonsService.getMainMarkup())
                .disableWebPagePreview(Boolean.TRUE)
                .parseMode("MarkdownV2")
                .build();
        return newMsg;
    }

    public SendMessage sendHostProfiles(Message message) {
        return null;
    }
}