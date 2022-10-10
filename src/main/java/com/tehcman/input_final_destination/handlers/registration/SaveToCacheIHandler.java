package com.tehcman.input_final_destination.handlers.registration;

import com.tehcman.cahce.Cache;
import com.tehcman.entities.Phase;
import com.tehcman.entities.Status;
import com.tehcman.entities.User;
import com.tehcman.input_final_destination.SendMessage_factories.CacheFactoryHost;
import com.tehcman.input_final_destination.SendMessage_factories.CacheFactoryRefugee;
import com.tehcman.input_final_destination.handlers.IHandler;
import com.tehcman.repository.UserRepository;
import com.tehcman.sendmessage.MessageSender;
import com.tehcman.services.BuildButtonsService;
import com.tehcman.services.IBuildSendMessageService;
import com.tehcman.services.keyboards.profile_registration.AddSexKeyboard;
import com.tehcman.services.keyboards.profile_registration.AddSkipButtonKeyboardRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;

import java.util.ArrayList;

@Component
public class SaveToCacheIHandler implements IHandler<Message> {
    private final IBuildSendMessageService ibuildSendMessageService;
    private final MessageSender messageSender;
    private final CacheFactoryRefugee cacheFactoryRefugee;
    private final CacheFactoryHost cacheFactoryHost;
    private final InitDataAndStatusHandler initDataAndStatusHandler;
    private final Cache<User> userCache;
    private BuildButtonsService buildButtonsService;
    private UserRepository userRepository;

    @Autowired
    public SaveToCacheIHandler(IBuildSendMessageService ibuildSendMessageService, MessageSender messageSender, CacheFactoryRefugee cacheFactoryRefugee, CacheFactoryHost cacheFactoryHost, InitDataAndStatusHandler initDataAndStatusHandler, Cache<User> userCache, UserRepository userRepository) {
        this.ibuildSendMessageService = ibuildSendMessageService;
        this.messageSender = messageSender;
        this.cacheFactoryRefugee = cacheFactoryRefugee;
        this.cacheFactoryHost = cacheFactoryHost;
        this.initDataAndStatusHandler = initDataAndStatusHandler;
        this.userCache = userCache;
        this.userRepository = userRepository;
    }


    @Override
    public void handle(Message message) {
        User user = userCache.findBy(message.getChatId());

        /*
        if ((user == null) || (user.getStatus() == null)) {
            if ((user != null) && (!user.getPhase().equals(Phase.STATUS))){
                userCache.remove(user.getId());
            }
            */
        if ((message.getText().equals("Continue the registration")) && (user.getPhase().equals(Phase.STATUS))) {
            this.userCache.remove(message.getChatId());
        }
        if (userCache.findBy(message.getChatId()) == null) {
            user = userCache.findBy(message.getChatId());
        }
        if ((user == null)) {

            messageSender.messageSend(initDataAndStatusHandler.createSendMessage(message));
        } else if (user.getPhase() == Phase.STATUS) {
            registerRestUserData(user, message);

        } else if ((user.getPhase().equals(Phase.SEX) && (message.getText().equals("Continue the registration")))) {
            user.setPhase(Phase.STATUS);
            userRepository.save(user);
            if (user.getStatus().equals(Status.HOST)) {
                message.setText("Providing Accommodation");
            } else message.setText("Searching Accommodation");
            registerRestUserData(user, message);

            //careful with this part!
        } else {
            if (user.getStatus() == Status.REFUGEE) {
                SendMessage newMsg = cacheFactoryRefugee.createSendMessage(message);
                messageSender.messageSend(newMsg);
            } else {
                SendMessage newMsg = cacheFactoryHost.createSendMessage(message);
                messageSender.messageSend(newMsg);
            }
        }
    }

    private SendMessage registerRestUserData(User user, Message message) {
/*        if (message.getText().equals("Continue the registration")&& (user.getPhase().equals(Phase.STATUS))){
            var keyboardMarkup = new ReplyKeyboardMarkup();
            ArrayList<KeyboardButton> keyboardRow = new ArrayList<>();
            keyboardRow.add(new KeyboardButton("Searching Accommodation"));
            keyboardRow.add(new KeyboardButton("Providing Accommodation"));

            this.buildButtonsService = new BuildButtonsService(keyboardMarkup);
            return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "How can we help you", buildButtonsService.getMainMarkup());
        }*/

        switch (user.getPhase()) {
            case STATUS:
                if (message.getText().equals("Searching Accommodation")) {
                    user.setStatus(Status.REFUGEE);
                    user.setPhase(Phase.SEX);
                    userRepository.save(user);


                    this.buildButtonsService = new BuildButtonsService(new AddSkipButtonKeyboardRow());

                    //careful with this part!
                    SendMessage newMessage = cacheFactoryRefugee.createSendMessage(message);
//                    Message msg = newMessage;
                    this.buildButtonsService = new BuildButtonsService(new AddSexKeyboard());
                    messageSender.messageSend(ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Are you man or woman?", buildButtonsService.getMainMarkup()));
//                    SendMessage sendMessage = ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Type your name or SKIP if you want to set your default Telegram name", buildButtonsService.getMainMarkup());
                    return null;
                } else if (message.getText().equals("Providing Accommodation")) {
                    user.setStatus(Status.HOST);
                    user.setPhase(Phase.SEX);
                    userRepository.save(user);

                    this.buildButtonsService = new BuildButtonsService(new AddSkipButtonKeyboardRow());

                    //careful with this part!
                    SendMessage newMessage = cacheFactoryHost.createSendMessage(message);
//                    Message msg = newMessage;
                    this.buildButtonsService = new BuildButtonsService(new AddSexKeyboard());
                    messageSender.messageSend(ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Are you man or woman?", buildButtonsService.getMainMarkup()));
//                    SendMessage sendMessage = ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Type your name or SKIP if you want to set your default Telegram name", buildButtonsService.getMainMarkup());
                    return null;

                } else {
                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "You must press on a button!", buildButtonsService.getMainMarkup());
                }
        }
        return null;
    }
}