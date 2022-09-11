package com.tehcman.input_final_destination.handlers;

import com.tehcman.cahce.Cache;
import com.tehcman.entities.Phase;
import com.tehcman.entities.User;
import com.tehcman.sendmessage.MessageSender;
import com.tehcman.services.BuildButtonsService;
import com.tehcman.input_final_destination.resources.Command;
import com.tehcman.services.IBuildSendMessageService;
import com.tehcman.services.keyboards.AfterRegistrationKeyboard;
import com.tehcman.services.keyboards.BeforeRegistrationKeyboard;
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

    @Autowired
    public CommandHandler(Cache<User> userCache, MessageSender messageSender, IBuildSendMessageService buildSendMessageService) {
        this.userCache = userCache;
        this.messageSender = messageSender;
        this.buildSendMessageService = buildSendMessageService;
    }


    @Override
    public boolean handleCommand(Message message) {
        //TODO root place for commands; when adding more commands in the future, code here
        User user = userCache.findBy(message.getChatId());

        if (message.getText().equals(Command.START.toString())) {
            this.buildButtonsService = new BuildButtonsService(new BeforeRegistrationKeyboard());
            if (user != null) {
                userCache.remove(message.getChatId());
                user.setPhase(Phase.STATUS);
            }
            SendMessage msg = buildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Yay! You've just restarted this bot!", buildButtonsService.getMainMarkup());
            messageSender.messageSend(msg);
            return true;
        }
        if (message.getText().equals(Command.HOME.toString())) {
            if (user != null) {
                Phase phase = userCache.findBy(message.getChatId()).getPhase();
                switch (phase) {

                    //after registration
                    case NONE:
                        this.buildButtonsService = new BuildButtonsService(new AfterRegistrationKeyboard(message, userCache));
                        SendMessage msg1 = buildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Returned home!", buildButtonsService.getMainMarkup());
                        messageSender.messageSend(msg1);
                        return true;
//                    default:
                }

            //case not started registration. user == null
            } else
                this.buildButtonsService = new BuildButtonsService(new BeforeRegistrationKeyboard());
            SendMessage msg2 = buildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Returned home!", buildButtonsService.getMainMarkup());
            messageSender.messageSend(msg2);

            return true;
        }

        else return false;
    }
}