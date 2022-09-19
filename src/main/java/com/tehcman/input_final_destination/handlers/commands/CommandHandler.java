package com.tehcman.input_final_destination.handlers.commands;

import com.tehcman.cahce.Cache;
import com.tehcman.entities.Phase;
import com.tehcman.entities.Status;
import com.tehcman.entities.User;
import com.tehcman.printers.HostProfile;
import com.tehcman.printers.RefugeeProfile;
import com.tehcman.sendmessage.MessageSender;
import com.tehcman.services.BuildButtonsService;
import com.tehcman.resources.Command;
import com.tehcman.services.IBuildSendMessageService;
import com.tehcman.services.keyboards.AfterRegistrationKeyboard;
import com.tehcman.services.keyboards.BeforeRegistrationKeyboard;
import com.tehcman.services.keyboards.SuspendedRegistrationKeyboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class CommandHandler implements IHandlerCommand<Message> {
    private BuildButtonsService buildButtonsService;
    private final Cache<User> userCache;
    private final MessageSender messageSender;
    private final IBuildSendMessageService buildSendMessageService;
    private final SuspendedRegistrationKeyboard suspendedRegistrationKeyboard;
    private final HostProfile hostProfile;
    private final RefugeeProfile refugeeProfile;

    @Autowired
    public CommandHandler(Cache<User> userCache, MessageSender messageSender, IBuildSendMessageService buildSendMessageService, SuspendedRegistrationKeyboard suspendedRegistrationKeyboard, HostProfile hostProfile, RefugeeProfile refugeeProfile) {
        this.userCache = userCache;
        this.messageSender = messageSender;
        this.buildSendMessageService = buildSendMessageService;
        this.suspendedRegistrationKeyboard = suspendedRegistrationKeyboard;
        this.hostProfile = hostProfile;
        this.refugeeProfile = refugeeProfile;
    }


    @Override
    public boolean handleCommand(Message message) {
        //root place for commands; when adding more commands in the future, code here
        User user = userCache.findBy(message.getChatId());

        if (message.getText().equals(Command.START.toString())) {
            this.suspendedRegistrationKeyboard.setSuspended(false);
            this.buildButtonsService = new BuildButtonsService(new BeforeRegistrationKeyboard());
            if (user != null) {
                if (user.getStatus() == Status.HOST) {
                    hostProfile.getHosts().remove(user);
                } else if (user.getStatus() == Status.REFUGEE) {
                    refugeeProfile.getRefugees().remove(user);
                }
                userCache.remove(message.getChatId());
                user.setPhase(Phase.STATUS);
            }
            settingViewedToFalse();
            SendMessage msg = buildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Yay! You've just restarted this bot!", buildButtonsService.getMainMarkup());
            messageSender.messageSend(msg);
            return true;
        }
        if (message.getText().equals(Command.HOME.toString())) {
            if (user != null) {
                Phase phase = userCache.findBy(message.getChatId()).getPhase();
                switch (phase) {

                    case NONE:
                        //after registration
                        this.buildButtonsService = new BuildButtonsService(new AfterRegistrationKeyboard(message, userCache));
                        SendMessage msg1 = buildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Returned home!", buildButtonsService.getMainMarkup());
                        messageSender.messageSend(msg1);
                        return true;

                    default:
                        this.buildButtonsService = new BuildButtonsService(new SuspendedRegistrationKeyboard());
                        SendMessage msg3 = buildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Returned home!", buildButtonsService.getMainMarkup());
                        this.suspendedRegistrationKeyboard.setSuspended(true);
                        messageSender.messageSend(msg3);
                        return true;
                }

                //case not started registration. user == null
            } else
                this.buildButtonsService = new BuildButtonsService(new BeforeRegistrationKeyboard());
            SendMessage msg2 = buildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Returned home!", buildButtonsService.getMainMarkup());
            messageSender.messageSend(msg2);

            return true;
        } else return false;
    }

    private void settingViewedToFalse() {
        if ((userCache.getAll().size() > 0) && (userCache.getAll() != null)) {
            userCache.getAll().forEach(user -> user.setViewed(false));
        }
        if ((this.hostProfile.getHosts().size() > 0) && (this.hostProfile.getHosts() != null)) {
            this.hostProfile.getHosts().forEach(user -> user.setViewed(false));
        }
        if ((this.refugeeProfile.getRefugees().size() > 0) && (this.refugeeProfile.getRefugees() != null)) {
            this.refugeeProfile.getRefugees().forEach(user -> user.setViewed(false));
        }
    }
}