package com.tehcman.printers;

import com.tehcman.cahce.UserCache;
import com.tehcman.entities.Status;
import com.tehcman.entities.User;
import com.tehcman.sendmessage.MessageSender;
import com.tehcman.services.FetchRandomUniqueUserService;
import com.tehcman.services.IBuildSendMessageService;
import com.tehcman.services.ParsingJSONtoListService;
import com.tehcman.services.keyboards.profile_search.InlineNewProfilesNotification;
import com.tehcman.services.keyboards.profile_search.InlineNoProfiles;
import com.tehcman.services.keyboards.profile_search.InlineProfileNavigation;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//FIXME copy functionality from the hostprofile class
@Component
public class RefugeeProfile implements IPrintUserProfile {
    @Getter
    private List<User> refugees;
    private int prevNumber = -1;
    private final UserCache userCache;
    private final MessageSender messageSender;
    private final IBuildSendMessageService iBuildSendMessageService;
    private FetchRandomUniqueUserService fetchRandomUniqueUserService;

    //keyboards
    private final InlineNewProfilesNotification inlineNewProfilesNotification;
    private final InlineNoProfiles inlineNoProfiles;
    private final InlineProfileNavigation inlineProfileNavigation;

    public RefugeeProfile(UserCache userCache, MessageSender messageSender, IBuildSendMessageService iBuildSendMessageService, InlineNewProfilesNotification inlineNewProfilesNotification, InlineNoProfiles inlineNoProfiles, InlineProfileNavigation inlineProfileNavigation) {
        this.userCache = userCache;
        this.refugees = new ArrayList<>();
/*//        setHostsFromCache();

        //fetching data from json
        ParsingJSONtoListService parsingJSONtoListService = new ParsingJSONtoListService();

        ArrayList<User> refugees = (ArrayList<User>) filterUsers(parsingJSONtoListService.parse(), Status.REFUGEE);
        this.refugees.addAll(refugees);
//        this.refugees.forEach(System.out::println);*/
        settingDataCacheAndHosts();

        this.messageSender = messageSender;
        this.iBuildSendMessageService = iBuildSendMessageService;
        this.inlineNewProfilesNotification = inlineNewProfilesNotification;
        this.inlineNoProfiles = inlineNoProfiles;
        this.inlineProfileNavigation = inlineProfileNavigation;
    }

    @Override
    public String beautify(Long id) {
        return null;
    }

    @Override
    public void printUserRandomDefault(Message msg) {
        SendMessage newMessage = iBuildSendMessageService.createHTMLMessage(msg.getChatId().toString(),
                fetchRandomUniqueUserService.fetchRandomUniqueUser(Status.REFUGEE).toString(), inlineProfileNavigation.getMainMarkup());
        messageSender.messageSend(newMessage);
    }

    @Override
    public void setUsersFromCache() {
        if ((userCache != null) && (userCache.getAll().size() > 0)) {
            this.refugees.addAll(userCache.getAll().stream()
                    .filter(x -> x.getStatus().equals(Status.REFUGEE))
                    .collect(Collectors.toList()));
        }
    }

    @Override
    public void viewedAllUsers(Message msg) {

    }

    @Override
    public void notifyNewProfiles(Message msg) {

    }

    @Override
    public List<User> filterUsers(List<User> userList, Status whoToLookFor) {
        return userList.stream()
                .filter(x -> x.getStatus().equals(whoToLookFor))
                .collect(Collectors.toList());
    }

    private void settingDataCacheAndHosts() {
        ParsingJSONtoListService parsingJSONtoListService = new ParsingJSONtoListService();

        ArrayList<User> refugees = (ArrayList<User>) filterUsers(parsingJSONtoListService.parse(), Status.REFUGEE);
        this.refugees.addAll(refugees);

//        hosts.forEach(System.out::println);
        System.out.println("printing cache out");
        refugees.forEach(this.userCache::add);
        userCache.getAll().forEach(System.out::println);
    }

    private void setIsViewed(int positionInArray) {
        this.userCache.findBy((long) positionInArray).setViewed(true);
        this.getRefugees().get(positionInArray).setViewed(true);
    }

    @Autowired
    public void setFetchRandomUniqueUserService(FetchRandomUniqueUserService fetchRandomUniqueUserService) {
        this.fetchRandomUniqueUserService = fetchRandomUniqueUserService;
    }
}
