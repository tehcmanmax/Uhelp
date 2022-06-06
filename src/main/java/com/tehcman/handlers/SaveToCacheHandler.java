package com.tehcman.handlers;

import com.tehcman.cahce.Cache;
import com.tehcman.entities.Position;
import com.tehcman.entities.User;
import com.tehcman.sendmessage.MessageSender;
import com.tehcman.services.build_buttons.BuildButtonsService;
import com.tehcman.services.build_markup.IMarkup;
import com.tehcman.services.build_markup.MainMarkup;
import com.tehcman.services.build_mess.IBuildSendMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;

@Component
public class SaveToCacheHandler implements Handler<Message> {
    private final IBuildSendMessageService ibuildSendMessageService;
    private final Cache<User> userCache;
    private final MessageSender messageSender;
    private final BuildButtonsService buildButtonsService;
    private final IMarkup iMarkup;

    @Autowired
    public SaveToCacheHandler(Cache<User> userCache, MessageSender messageSender, BuildButtonsService buildButtonsService, IBuildSendMessageService ibuildSendMessageService, MainMarkup mainMarkup) {
        this.ibuildSendMessageService = ibuildSendMessageService;
        this.userCache = userCache;
        this.messageSender = messageSender;
        this.buildButtonsService = buildButtonsService;
        this.iMarkup = mainMarkup;
    }

    private User generateDefaultUserInformationFromMessage(Message message) {
        User newUser = new User(message.getChatId(), message.getFrom().getUserName(),
                message.getFrom().getFirstName(), Position.PHONE_NUMBER);
        buildButtonsService.addingPhoneNumberButton(); //adding phone number button
        messageSender.messageSend(ibuildSendMessageService.getSendMessage(message.getChatId().toString(), "Please, press on the \"Phone number\" button", iMarkup.getMarkup()));
        return newUser;
    }

    private void registerRestUserData(User user, Message message) {
        switch (user.getPosition()) {
            case PHONE_NUMBER: //phase 1
                if (message.hasContact()) {
                    user.setPhoneNumber(message.getContact().getPhoneNumber());
                    messageSender.messageSend(ibuildSendMessageService.getSendMessage(message.getChatId().toString(), "Please, type your <i>age</i> at this chat", new ReplyKeyboardRemove(Boolean.TRUE)));
                    user.setPosition(Position.AGE);
                } else if (message.getText().equals("I don't want to disclose the phone number")) {
                    ReplyKeyboardRemove replyKeyboardRemove = new ReplyKeyboardRemove(); //removes the phone number keyboard
                    replyKeyboardRemove.setRemoveKeyboard(true);
                    messageSender.messageSend(ibuildSendMessageService.getSendMessage(message.getChatId().toString(), "Please, type your <i>age</i> at this chat", replyKeyboardRemove));
                    user.setPosition(Position.AGE);
                } else {
                    var newMessage = SendMessage.builder().
                            text("You haven't shared the phone number!").
                            chatId(message.getChatId().toString()).build();
                    messageSender.messageSend(newMessage);

                    buildButtonsService.addingPhoneNumberButton();
                }
                break;
            case AGE:
                if (message.getText().matches("\\d{1,2}")) {
                    user.setAge(message.getText());
                    user.setPosition(Position.NONE);
                    buildButtonsService.afterRegistrationButtons();
                    messageSender.messageSend(ibuildSendMessageService.getSendMessage(message.getChatId().toString(), "ok", iMarkup.getMarkup()));

                } else {
                    SendMessage newMessage = new SendMessage();
                    newMessage.setText("Please, enter a <u>number</u> (0-99)");
                    newMessage.setParseMode("HTML");
                    newMessage.setChatId(user.getId().toString());

                    messageSender.messageSend(newMessage);
                }
                System.out.println(user);
                break;
        }
    }

    //entry point
    @Override
    public void handle(Message message) {
        //if no user is found in the registry(cache), start a new user registration
        User userFromCache = userCache.findBy(message.getChatId());
        if (userFromCache == null) {
            User newUser = generateDefaultUserInformationFromMessage(message);
            userCache.add(newUser);
        } else if (userFromCache.getPosition() == Position.NONE) {
            messageSender.messageSend(new SendMessage(message.getChatId().toString(), "Hey. You are already in the system." +
                    " Instead of duplicating data of yourself, do something useful in your life"));
        } else {
            registerRestUserData(userFromCache, message);
        }
    }
}
