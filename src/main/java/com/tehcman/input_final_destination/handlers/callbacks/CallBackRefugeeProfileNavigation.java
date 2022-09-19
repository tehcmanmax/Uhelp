package com.tehcman.input_final_destination.handlers.callbacks;

import com.tehcman.cahce.UserCache;
import com.tehcman.entities.Status;
import com.tehcman.entities.User;
import com.tehcman.input_final_destination.handlers.IHandler;
import com.tehcman.printers.RefugeeProfile;
import com.tehcman.sendmessage.MessageSender;
import com.tehcman.services.BuildButtonsService;
import com.tehcman.services.FetchRandomUniqueUserService;
import com.tehcman.services.IBuildSendMessageService;
import com.tehcman.services.NewProfileClientNotifier;
import com.tehcman.services.keyboards.AfterRegistrationKeyboard;
import com.tehcman.services.keyboards.profile_search.InlineNoProfiles;
import com.tehcman.services.keyboards.profile_search.InlineProfileNavigation;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
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
    private final NewProfileClientNotifier newProfileClientNotifier;
    private BuildButtonsService buildButtonsService;
    @Setter
    private User lastViewedRefugee;


    public CallBackRefugeeProfileNavigation(MessageSender messageSender, InlineProfileNavigation inlineProfileNavigation, UserCache userCache, RefugeeProfile refugeeProfile, IBuildSendMessageService iBuildSendMessageService, FetchRandomUniqueUserService fetchRandomUniqueUserService, InlineNoProfiles inlineNoProfiles, NewProfileClientNotifier newProfileClientNotifier) {
        this.messageSender = messageSender;
        this.inlineProfileNavigation = inlineProfileNavigation;
        this.userCache = userCache;
        this.refugeeProfile = refugeeProfile;
        this.iBuildSendMessageService = iBuildSendMessageService;
        this.fetchRandomUniqueUserService = fetchRandomUniqueUserService;
        this.inlineNoProfiles = inlineNoProfiles;
        this.newProfileClientNotifier = newProfileClientNotifier;
    }

    @Override
    public void handle(CallbackQuery inlineButtonPressed) {
        var refugees = this.refugeeProfile.getRefugees();
        if ((inlineButtonPressed.getData().equals("view_again_action")) && (refugeeProfile.getRefugees().size() > 1)) {
            refugees.forEach(refugee -> refugee.setViewed(false));
            inlineButtonPressed.setData("rand_action");
            handle(inlineButtonPressed);
        } else if (inlineButtonPressed.getData().equals("yes_show_new_profiles_action")) {
            SendMessage newMessage = iBuildSendMessageService.createHTMLMessage(String.valueOf(inlineButtonPressed.getFrom().getId()),
                    newProfileClientNotifier.getNewRegisteredUser().toString(),
                    this.inlineProfileNavigation.getMainMarkup());
            messageSender.messageSend(newMessage);
        } else if (inlineButtonPressed.getData().equals("notification_action")) {
            this.buildButtonsService = new BuildButtonsService(new AfterRegistrationKeyboard(inlineButtonPressed.getMessage().getChatId(), userCache));
            inlineNoProfiles.getClientListener().setUserThatListensId(inlineButtonPressed.getFrom().getId());

            SendMessage newMessage = iBuildSendMessageService.createHTMLMessage(String.valueOf(inlineButtonPressed.getFrom().getId()),
                    "Ok. We will notify you when new profiles show up",
                    this.buildButtonsService.getMainMarkup());
            messageSender.messageSend(newMessage);
        } else if (!checkIfAllProfilesViewed(inlineButtonPressed)) {
            if ((inlineButtonPressed.getData().equals("rand_action")) && (refugees.size() > 0)) {
                User user = randomRefugee();
                EditMessageText newMessage = iBuildSendMessageService.createHTMLEditMessage(user.toString(), inlineButtonPressed, inlineProfileNavigation.getMainMarkup());

                fetchRandomUniqueUserService.setIsViewed(user.getId(), Status.REFUGEE);
                messageSender.editMessageSend(newMessage);
            } else if ((inlineButtonPressed.getData().equals("next_action")) && (refugees.size() > 0)) {
                //fetching previous user
                int index = -1;  /*Integer.parseInt(String.valueOf(this.lastViewedrefugee.getId()));*/

                for (int i = 0; i < refugees.size(); i++) {
                    if (refugees.get(i).getId() == lastViewedRefugee.getId()) {
                        index = i;
                        break;
                    }
                }
                index++;
                if (index > refugees.size()) {
                    index = 0;
                }

                if (index > 0) {
                    int poiner = index;
                    List<User> newArray = new ArrayList<>(refugees.size());
                    for (; index < refugees.size(); index++) {
                        newArray.add(refugees.get(index));
                    }
                    for (int index2 = 0; index2 < poiner; index2++) {
                        newArray.add(refugees.get(index2));
                    }

                    int temp = 0;
                    refugees = newArray;
                }
                for (int i = 0; i < refugees.size(); i++) {
//                if (!refugees.get(i).isViewed()) {
                    EditMessageText newMessage = iBuildSendMessageService.createHTMLEditMessage(refugees.get(i).toString(), inlineButtonPressed, inlineProfileNavigation.getMainMarkup());
                    refugees.get(i).setViewed(true);
                    this.lastViewedRefugee = refugees.get(i);
                    messageSender.editMessageSend(newMessage);
                    return;
//                }
                }

            } else if ((inlineButtonPressed.getData().equals("back_action")) && (refugeeProfile.getRefugees().size() > 1)) {
                int index = -1; //tracks index position within the arrayList

                //finding the index
                for (int i = 0; i < refugees.size(); i++) {
                    if (refugees.get(i).getId() == lastViewedRefugee.getId()) {
                        index = i;
                        break;
                    }
                }
                if (index > 0) {
                    int poiner = index;
                    index++;
                    if (index > refugees.size()) {
                        index = 0;
                    }
                    List<User> newArray = new ArrayList<>(refugees.size());
                    for (; index < refugees.size(); index++) {
                        newArray.add(refugees.get(index));
                    }
                    for (int index2 = 0; index2 <= poiner; index2++) {
                        newArray.add(refugees.get(index2));
                    }

                    int temp = 0;
                    refugees = newArray;
                }
                int i = refugees.size();
                i--;
                i--;
                for (; i >= 0; i--) {
                    //make outputs not unique users
//                if (!refugees.get(i).isViewed()) {
                    EditMessageText newMessage = iBuildSendMessageService.createHTMLEditMessage(refugees.get(i).toString(), inlineButtonPressed, inlineProfileNavigation.getMainMarkup());
                    refugees.get(i).setViewed(true);
                    this.lastViewedRefugee = refugees.get(i);
                    messageSender.editMessageSend(newMessage);
                    return;
                }
//                }
            }
        }

    }


    private boolean checkIfAllProfilesViewed(CallbackQuery callbackQuery) {
        if (fetchRandomUniqueUserService.areAllUsersViewed(Status.REFUGEE)) {
            EditMessageText editMessageText = iBuildSendMessageService.createHTMLEditMessage("You've viewed all profiles. " +
                    "Show them again or we can notify you when new profiles appear", callbackQuery, inlineNoProfiles.getMainMarkup());
            messageSender.editMessageSend(editMessageText);
            return true;
        }
        return false;
    }


    private User randomRefugee() {
        return this.fetchRandomUniqueUserService.fetchRandomUniqueUser(Status.REFUGEE);
    }


}
