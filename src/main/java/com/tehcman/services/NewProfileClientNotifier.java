package com.tehcman.services;

import com.tehcman.sendmessage.MessageSender;
import com.tehcman.services.keyboards.profile_search.InlineNewProfilesNotification;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
public class NewProfileClientNotifier {
    private final InlineNewProfilesNotification inlineNewProfilesNotification;
    private final IBuildSendMessageService iBuildSendMessageService;
    private final MessageSender messageSender;
//    private User


    public NewProfileClientNotifier(InlineNewProfilesNotification inlineNewProfilesNotification, IBuildSendMessageService iBuildSendMessageService, MessageSender messageSender) {
        this.inlineNewProfilesNotification = inlineNewProfilesNotification;
        this.iBuildSendMessageService = iBuildSendMessageService;
        this.messageSender = messageSender;
    }

    public void notifyClient(Long clientId) {
        SendMessage sendMessage = iBuildSendMessageService.createHTMLMessage(String.valueOf(clientId), "New profiles were registered! " +
                "Do you want to see them?", this.inlineNewProfilesNotification.getMainMarkup());
        messageSender.messageSend(sendMessage);
    }

//    private
}
