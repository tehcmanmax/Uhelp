package com.tehcman.input_final_destination.handlers.callbacks;

//TODO: next feature implementation: USE THIS CLASS TO FETCH NEWS FROM TELEGRAM NEWS CHANNELS; USE API TO FETCH THEM


import com.tehcman.cahce.UserCache;
import com.tehcman.entities.Status;
import com.tehcman.entities.User;
import com.tehcman.input_final_destination.handlers.IHandler;
import com.tehcman.printers.HostProfile;
import com.tehcman.sendmessage.MessageSender;
import com.tehcman.services.FetchRandomUniqueUserService;
import com.tehcman.services.IBuildSendMessageService;
import com.tehcman.services.keyboards.profile_search.InlineProfileNavigation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;


@Component
public class CallBackHostProfileNavigation implements IHandler<CallbackQuery> {
    private final MessageSender messageSender;
    private final InlineProfileNavigation inlineProfileNavigation; //testing the inline buttons
    private final UserCache userCache;
    private final HostProfile hostProfile;
    private final IBuildSendMessageService iBuildSendMessageService;
    private final FetchRandomUniqueUserService fetchRandomUniqueUserService;

    @Autowired
    public CallBackHostProfileNavigation(@Lazy MessageSender messageSender, InlineProfileNavigation inlineProfileNavigation, UserCache userCache, HostProfile hostProfile, IBuildSendMessageService iBuildSendMessageService, FetchRandomUniqueUserService fetchRandomUniqueUserService) {
        this.messageSender = messageSender;
        this.inlineProfileNavigation = inlineProfileNavigation;
        this.userCache = userCache;
        this.hostProfile = hostProfile;
        this.iBuildSendMessageService = iBuildSendMessageService;
        this.fetchRandomUniqueUserService = fetchRandomUniqueUserService;
    }

    //TODO: implement it specifically for different user.statuses; it has to know how many refugees or hosts in the cache
    @Override
    public void handle(CallbackQuery inlineButtonPressed) {
        var hosts = this.hostProfile.getHosts();

        if ((inlineButtonPressed.getData().equals("rand_action")) && (hosts.size() > 1)) {
            check();
            EditMessageText newMessage = iBuildSendMessageService.createHTMLEditMessage(randomHost(), inlineButtonPressed, inlineProfileNavigation.getMainMarkup());
            messageSender.editMessageSend(newMessage);
        } else if ((inlineButtonPressed.getData().equals("next_action")) && (hosts.size() > 1)) {
            check();

            int index = fetchRandomUniqueUserService.calculateCurrentUserArrayIndex(inlineButtonPressed, Status.HOST);
            index--;

            int i2 = 0;
            for (int i = index; index < ((hosts.size() - 1) + (index)); i++) {
                if (i > hosts.size()) {
                    if (!hosts.get(i2).isViewed()) {

                        EditMessageText newMessage = iBuildSendMessageService.createHTMLEditMessage(hosts.get(i2).toString(), inlineButtonPressed, inlineProfileNavigation.getMainMarkup());
                        hosts.get(i2).setViewed(true);
                        i2++;
                        messageSender.editMessageSend(newMessage);
                    }
                } else if (!hosts.get(i).isViewed()) {
                    EditMessageText newMessage = iBuildSendMessageService.createHTMLEditMessage(hosts.get(i).toString(), inlineButtonPressed, inlineProfileNavigation.getMainMarkup());
                    hosts.get(i).setViewed(true);
                    messageSender.editMessageSend(newMessage);
                }
            }


        } else if ((inlineButtonPressed.getData().equals("back_action")) && (hostProfile.getHosts().size() > 1)) {
            check();
        }

    }

    private void check() {
        if (fetchRandomUniqueUserService.areAllUsersViewed(Status.HOST)) {
            throw new ArrayStoreException("ran out the user profiles");
        }
    }


    private String randomHost() {
        return this.fetchRandomUniqueUserService.fetchRandomUniqueUser(Status.HOST).toString();
    }


}
