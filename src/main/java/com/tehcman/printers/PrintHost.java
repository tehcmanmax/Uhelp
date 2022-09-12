package com.tehcman.printers;

import com.tehcman.cahce.UserCache;
import com.tehcman.entities.Status;
import com.tehcman.entities.User;
import com.tehcman.sendmessage.MessageSender;
import com.tehcman.services.IBuildSendMessageService;
import com.tehcman.services.ParsingJSONtoListService;
import com.tehcman.services.keyboards.profile_search.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PrintHost {
    private List<User> hosts;
    private int prevNumber = -1;
    private final UserCache userCache;
    private final MessageSender messageSender;
    private final IBuildSendMessageService iBuildSendMessageService;

    //keyboards
    private final InlineNewProfilesNotification inlineNewProfilesNotification;
    private final InlineNoProfiles inlineNoProfiles;
    private final InlineProfileNavigation inlineProfileNavigation;

    @Autowired
    public PrintHost(UserCache userCache, MessageSender messageSender, IBuildSendMessageService iBuildSendMessageService, InlineNewProfilesNotification inlineNewProfilesNotification, InlineNoProfiles inlineNoProfiles, InlineProfileNavigation inlineProfileNavigation) {
        this.hosts = new ArrayList<>();
//        setHostsFromCache();

        //fetching data from json
        ParsingJSONtoListService parsingJSONtoListService = new ParsingJSONtoListService();
        this.hosts.addAll(parsingJSONtoListService.parse());

        this.userCache = userCache;
        this.messageSender = messageSender;
        this.iBuildSendMessageService = iBuildSendMessageService;
        this.inlineNewProfilesNotification = inlineNewProfilesNotification;
        this.inlineNoProfiles = inlineNoProfiles;
        this.inlineProfileNavigation = inlineProfileNavigation;
    }

    private void setHostsFromCache() {
        this.hosts = userCache.getAll().stream()
                .filter(x -> x.getStatus().equals(Status.HOST))
                .collect(Collectors.toList());
    }

    public void printHostRandom(Message msg) {
        int randNumb = (int) (Math.random() * this.hosts.size());
        while (randNumb == prevNumber) {
            randNumb = (int) (Math.random() * this.hosts.size());
        }
        prevNumber = randNumb;

        SendMessage newMessage = iBuildSendMessageService.createHTMLMessage(msg.getChatId().toString(),
                this.hosts.get(prevNumber).toString(), inlineProfileNavigation.getMainMarkup());
        messageSender.messageSend(newMessage);
    }

    private String beautify(String host) {
        return null;
    }
}
