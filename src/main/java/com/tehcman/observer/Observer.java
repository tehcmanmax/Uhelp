package com.tehcman.observer;

import com.tehcman.entities.User;
import com.tehcman.sendmessage.MessageSender;
import com.tehcman.services.IBuildSendMessageService;
import com.tehcman.services.keyboards.profile_search.InlineNewProfilesNotification;
import com.tehcman.table_printers.HostProfile;
import com.tehcman.table_printers.RefugeeProfile;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component
public class Observer {
    @Setter
    private User observer;
    private final MessageSender sender;
    private final HostProfile hostProfile;
    private final RefugeeProfile refugeeProfile;
    private final IBuildSendMessageService buildSendMessageService;
    private final InlineNewProfilesNotification inlineNewProfilesNotification;
    @Setter
    private boolean inlineNoProfiles;

    public Observer(MessageSender sender, HostProfile hostProfile, RefugeeProfile refugeeProfile, IBuildSendMessageService buildSendMessageService, InlineNewProfilesNotification inlineNewProfilesNotification) {
        this.sender = sender;
        this.hostProfile = hostProfile;
        this.refugeeProfile = refugeeProfile;
        this.buildSendMessageService = buildSendMessageService;
        this.inlineNewProfilesNotification = inlineNewProfilesNotification;

    }

    public void update() {
        if (this.inlineNoProfiles) {
            SendMessage sendMes = buildSendMessageService.createHTMLMessage(String.valueOf(observer.getId()),
                    "New profiles were registered! Do you want to see them?",
                    inlineNewProfilesNotification.getMainMarkup());
            sender.messageSend(sendMes);
        }
    }
}
