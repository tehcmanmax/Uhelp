package com.tehcman.input_final_destination.handlers.callbacks;

import com.tehcman.cahce.UserCache;
import com.tehcman.entities.Status;
import com.tehcman.entities.User;
import com.tehcman.input_final_destination.handlers.IHandler;
import com.tehcman.printers.RefugeeProfile;
import com.tehcman.sendmessage.MessageSender;
import com.tehcman.services.FetchRandomUniqueUserService;
import com.tehcman.services.IBuildSendMessageService;
import com.tehcman.services.keyboards.profile_search.InlineNoProfiles;
import com.tehcman.services.keyboards.profile_search.InlineProfileNavigation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.ArrayList;
import java.util.List;

@Component
public class CallBackRefugeeProfileNavigation implements IHandler<CallbackQuery> {
    private final MessageSender messageSender;
    private final InlineProfileNavigation inlineProfileNavigation; //testing the inline buttons
    private final UserCache userCache;
    private final RefugeeProfile refugeeProfile;
    private final IBuildSendMessageService iBuildSendMessageService;
    private FetchRandomUniqueUserService fetchRandomUniqueUserService;
    private final InlineNoProfiles inlineNoProfiles;

    public CallBackRefugeeProfileNavigation(MessageSender messageSender, InlineProfileNavigation inlineProfileNavigation, UserCache userCache, RefugeeProfile refugeeProfile, IBuildSendMessageService iBuildSendMessageService, InlineNoProfiles inlineNoProfiles) {
        this.messageSender = messageSender;
        this.inlineProfileNavigation = inlineProfileNavigation;
        this.userCache = userCache;
        this.refugeeProfile = refugeeProfile;
        this.iBuildSendMessageService = iBuildSendMessageService;
        this.inlineNoProfiles = inlineNoProfiles;
    }

    @Override
    public void handle(CallbackQuery inlineButtonPressed) {
        var refugees = this.refugeeProfile.getRefugees();

        if ((inlineButtonPressed.getData().equals("rand_action")) && (refugees.size() > 1)) {
            check(inlineButtonPressed);
            EditMessageText newMessage = iBuildSendMessageService.createHTMLEditMessage(randomRefugee(), inlineButtonPressed, inlineProfileNavigation.getMainMarkup());
            messageSender.editMessageSend(newMessage);
        } else if ((inlineButtonPressed.getData().equals("next_action")) && (refugees.size() > 1)) {
            check(inlineButtonPressed);

            int index = fetchRandomUniqueUserService.calculateCurrentUserArrayIndex(inlineButtonPressed, Status.REFUGEE);
            if (index > 0) {
                int poiner = index;
                List<User> newArray = new ArrayList<>(refugees.size());
                for (int i = 0; i < refugees.size(); i++) {
                    for (; index < refugees.size(); index++) {
                        newArray.add(refugees.get(index));
                    }
                    for (int index2 = 0; index2 < poiner; index2++) {
                        newArray.add(refugees.get(index2));
                    }
                }
                refugees = newArray;
            }
            for (int i = 0; i < refugees.size(); i++) {
                if (!refugees.get(i).isViewed()) {
                    EditMessageText newMessage = iBuildSendMessageService.createHTMLEditMessage(refugees.get(i).toString(), inlineButtonPressed, inlineProfileNavigation.getMainMarkup());
                    refugees.get(i).setViewed(true);
                    messageSender.editMessageSend(newMessage);
                }
            }

        } else if ((inlineButtonPressed.getData().equals("back_action")) && (refugeeProfile.getRefugees().size() > 1)) {
            check(inlineButtonPressed);
        }

    }


    private void check(CallbackQuery callbackQuery) {
        if (fetchRandomUniqueUserService.areAllUsersViewed(Status.REFUGEE)) {
            EditMessageText editMessageText = iBuildSendMessageService.createHTMLEditMessage("You've viewed all profiles. " +
                    "Show them again or we can notify you when new profiles appear", callbackQuery, inlineNoProfiles.getMainMarkup());
            messageSender.editMessageSend(editMessageText);
        }
    }


    private String randomRefugee() {
        return this.fetchRandomUniqueUserService.fetchRandomUniqueUser(Status.REFUGEE).toString();
    }

    @Autowired
    public void setFetchRandomUniqueUserService(FetchRandomUniqueUserService fetchRandomUniqueUserService) {
        this.fetchRandomUniqueUserService = fetchRandomUniqueUserService;
    }
}
