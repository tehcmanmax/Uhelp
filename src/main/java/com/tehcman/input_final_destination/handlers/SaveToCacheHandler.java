package com.tehcman.input_final_destination.handlers;

import com.tehcman.cahce.Cache;
import com.tehcman.entities.Phase;
import com.tehcman.entities.Status;
import com.tehcman.entities.User;
import com.tehcman.input_final_destination.SendMessage_factories.CacheFactoryRefugee;
import com.tehcman.input_final_destination.SendMessage_factories.ISendMessageFactory;
import com.tehcman.sendmessage.MessageSender;
import com.tehcman.services.BuildButtonsService;
import com.tehcman.services.IBuildSendMessageService;
import com.tehcman.services.keyboards.AddSkipButtonKeyboardRow;
import com.tehcman.services.keyboards.AddStatusKeyboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class SaveToCacheHandler implements IHandler<Message> {

    private final MessageSender messageSender;
    private final ISendMessageFactory cacheFactoryRefugee;
    private final Cache<User> userCache;
    private BuildButtonsService buildButtonsService;
    private final IBuildSendMessageService ibuildSendMessageService;


    @Autowired
    public SaveToCacheHandler(MessageSender messageSender, CacheFactoryRefugee cacheFactoryRefugee, Cache<User> userCache, IBuildSendMessageService ibuildSendMessageService) {
        this.messageSender = messageSender;
        this.cacheFactoryRefugee = cacheFactoryRefugee;
        this.userCache = userCache;
        this.ibuildSendMessageService = ibuildSendMessageService;
    }


    @Override
    public void handle(Message message) {
        if (isUserInCache(message) != null) {
            if (isUserInCache(message).getPhase() == Phase.NONE) {
                messageSender.messageSend(new SendMessage(message.getChatId().toString(), "Hey. You are already in the system." + " Instead of duplicating data of yourself, do something useful in your life"));
            } else if ((!isUserInCache(message).getPhase().equals(Phase.STATUS)) && (isUserInCache(message).getStatus().equals(Status.REFUGEE))) {
                SendMessage newMsg = cacheFactoryRefugee.registerRestUserData(message);
                messageSender.messageSend(newMsg);
                //TODO else if for a host ,,,
            }
        } else {
            User newUser = generateDefaultUserInformationFromMessage(message);
            if (newUser.getPhase().equals(Phase.STATUS)) {
                if (message.getText().equals("Searching Accommodation")) {
                    newUser.setStatus(Status.REFUGEE);
                    newUser.setPhase(Phase.NAME);

                    this.buildButtonsService = new BuildButtonsService(new AddSkipButtonKeyboardRow());
                    messageSender.messageSend(ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Type your name or SKIP if you want to set your default Telegram name", buildButtonsService.getMainMarkup()));
                } else if (message.getText().equals("Providing Accommodation")) {
                    newUser.setStatus(Status.HOST);
                    newUser.setPhase(Phase.NAME);

                    this.buildButtonsService = new BuildButtonsService(new AddSkipButtonKeyboardRow());
                    messageSender.messageSend(ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Type your name or SKIP if you want to set your default Telegram name", buildButtonsService.getMainMarkup()));
                } else {
                    messageSender.messageSend(ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "You must press on a button!", buildButtonsService.getMainMarkup()));
                }
            }
        }
    }

    public User isUserInCache(Message message) {
        //if no user is found in the registry(cache), start a new user registration
        return userCache.findBy(message.getChatId());
    }

    private User generateDefaultUserInformationFromMessage(Message message) {
        User newUser = new User(message.getChatId(), message.getFrom().getUserName(), message.getFrom().getFirstName(), Phase.STATUS);
        this.buildButtonsService = new BuildButtonsService(new AddStatusKeyboard());
//        buildButtonsService.addingPhoneNumberButton(); //adding phone number button
        return newUser;
    }

}
