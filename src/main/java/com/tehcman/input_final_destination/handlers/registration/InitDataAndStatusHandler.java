package com.tehcman.input_final_destination.handlers.registration;

import com.tehcman.cahce.Cache;
import com.tehcman.entities.Phase;
import com.tehcman.entities.User;
import com.tehcman.input_final_destination.SendMessage_factories.ISendMessageFactory;
import com.tehcman.services.BuildButtonsService;
import com.tehcman.services.IBuildSendMessageService;
import com.tehcman.services.keyboards.profile_registration.AddStatusKeyboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class InitDataAndStatusHandler implements ISendMessageFactory {
    private final IBuildSendMessageService ibuildSendMessageService;
    private final Cache<User> userCache;
    private BuildButtonsService buildButtonsService;

    @Autowired
    public InitDataAndStatusHandler(IBuildSendMessageService ibuildSendMessageService, Cache<User> userCache) {
        this.ibuildSendMessageService = ibuildSendMessageService;
        this.userCache = userCache;
    }

    @Override
    public SendMessage createSendMessage(Message message) {
        User userFromCache = userCache.findBy(message.getChatId());
        if (userFromCache == null) {
            User newUser = generateDefaultUserInformationFromMessage(message);
            userCache.add(newUser);
            return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "How can we help you", buildButtonsService.getMainMarkup());

        } else if (userFromCache.getPhase() == Phase.NONE) {
            return new SendMessage(message.getChatId().toString(), "Hey. You are already in the system.");
        } else {
            return null;
        }
    }

    private User generateDefaultUserInformationFromMessage(Message message) {
        User newUser = new User(message.getChatId(), message.getFrom().getUserName(), message.getFrom().getFirstName(), Phase.STATUS);
        this.buildButtonsService = new BuildButtonsService(new AddStatusKeyboard());
//        buildButtonsService.addingPhoneNumberButton(); //adding phone number button
        return newUser;
    }

}
