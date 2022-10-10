package com.tehcman.printers;

import com.tehcman.cahce.UserCache;
import com.tehcman.entities.Status;
import com.tehcman.entities.User;
import com.tehcman.sendmessage.MessageSender;
import com.tehcman.services.FetchRandomUniqueUserService;
import com.tehcman.services.IBuildSendMessageService;
import com.tehcman.services.ParsingJSONtoListService;
import com.tehcman.services.keyboards.profile_search.*;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
//@Scope("prototype")
public class HostProfile implements IPrintUserProfile {
    private List<User> hosts;
    private final UserCache userCache;
    private final MessageSender messageSender;
    private final IBuildSendMessageService iBuildSendMessageService;

    //keyboards
    private final InlineNewProfilesNotification inlineNewProfilesNotification;
    private final InlineNoProfiles inlineNoProfiles;
    private final InlineProfileNavigation inlineProfileNavigation;
    private FetchRandomUniqueUserService fetchRandomUniqueUserService;

    @Autowired
    @Lazy
    public HostProfile(UserCache userCache, MessageSender messageSender, IBuildSendMessageService iBuildSendMessageService, InlineNewProfilesNotification inlineNewProfilesNotification, InlineNoProfiles inlineNoProfiles, InlineProfileNavigation inlineProfileNavigation) {
        this.userCache = userCache;
        this.hosts = new ArrayList<>();
//        setHostsFromCache();
        //fetching data from json
        settingDataCacheAndHosts();

        this.messageSender = messageSender;
        this.iBuildSendMessageService = iBuildSendMessageService;
        this.inlineNewProfilesNotification = inlineNewProfilesNotification;
        this.inlineNoProfiles = inlineNoProfiles;
        this.inlineProfileNavigation = inlineProfileNavigation;
    }

    @Override
    public void addUsersFromCache() {
        if ((userCache != null) && (userCache.getAll().size() > 0)) {
            this.hosts.addAll(userCache.getAll().stream()
                    .filter(x -> x.getStatus().equals(Status.HOST))
                    .collect(Collectors.toList()));
        }
    }

    @Override
    public void addSingleUserFromCache(User user) {
        if ((userCache != null) && (userCache.getAll().size() > 0)) {
            this.hosts.add(user);
        }
    }

    @Override
    public void viewedAllUsers(Message msg) {
        //if boolean equals the size of the array, it has been viewed by a user

    }

    @Override
    public void notifyNewProfiles(Message msg) {
        //if tracked number is less than the size of 10, notify the user

    }

    @Override
    public List<User> filterUsers(List<User> userList, Status whoToLookFor) {
        return userList.stream()
                .filter(x -> x.getStatus().equals(whoToLookFor))
                .collect(Collectors.toList());
    }

    public List<User> getHosts() {
        Predicate<User> byStatus = user -> user.getStatus().equals(Status.HOST);
        return userCache.getAll().stream().filter(byStatus).collect(Collectors.toList());
    }

    @Override
    public void printUserRandomDefault(Message msg) {
        User user = fetchRandomUniqueUserService.fetchRandomUniqueUser(Status.HOST);
        if (user != null) {
            SendMessage newMessage = iBuildSendMessageService.createHTMLMessage(msg.getChatId().toString(),
                    user.toString(),
                    inlineProfileNavigation.getMainMarkup());
            fetchRandomUniqueUserService.setIsViewed(user.getId(), Status.HOST);
            messageSender.messageSend(newMessage);
        }
    }

    public void printInline(Message msg, User user) {
        if (user != null) {
            SendMessage newMessage = iBuildSendMessageService.createHTMLMessage(msg.getChatId().toString(),
                    user.toString(),
                    inlineProfileNavigation.getMainMarkup());
            fetchRandomUniqueUserService.setIsViewed(user.getChatId(), Status.HOST);
            messageSender.messageSend(newMessage);
        }
    }

    @Override
    public String beautify(Long id) {
        return null;
    }

    private void settingDataCacheAndHosts() {
        ParsingJSONtoListService parsingJSONtoListService = new ParsingJSONtoListService();

        ArrayList<User> hosts = (ArrayList<User>) filterUsers(parsingJSONtoListService.parse(), Status.HOST);
        this.hosts.addAll(hosts);

//        hosts.forEach(System.out::println);
        System.out.println("printing cache out");
        hosts.forEach(this.userCache::add);
        userCache.getAll().forEach(System.out::println);
//
    }

    private void setIsViewed(int positionInArray) {
//        this.userCache.findBy((long) positionInArray).setViewed(true);
//        this.getHosts().get(positionInArray).setViewed(true);
    }

    @Autowired
    @Lazy
    public void setFetchRandomUniqueUserService(FetchRandomUniqueUserService fetchRandomUniqueUserService) {
        this.fetchRandomUniqueUserService = fetchRandomUniqueUserService;
    }
}
