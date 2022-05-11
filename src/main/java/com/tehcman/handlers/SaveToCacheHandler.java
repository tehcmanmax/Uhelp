package com.tehcman.handlers;

import com.tehcman.entities.Position;

import com.tehcman.cahce.Cache;
import com.tehcman.entities.User;
import com.tehcman.sendmessage.MessageSender;
import com.tehcman.services.BuildMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;

@Component
public class SaveToCacheHandler implements Handler<Message> {
    private final BuildMessageService buildMessageService;
    private final Cache<User> userCache;
    private final MessageSender messageSender;

    @Autowired
    public SaveToCacheHandler(@Lazy BuildMessageService buildMessageService, Cache<User> userCache, MessageSender messageSender) {
        this.buildMessageService = buildMessageService;
        this.userCache = userCache;
        this.messageSender = messageSender;
    }

    private User generateDefaultUserInformationFromMessage(Message message) {
        User newUser = new User(message.getChatId(), message.getFrom().getUserName(),
                message.getFrom().getFirstName(), Position.PHONE_NUMBER);

        User.setActiveUserRegistration(true);

        buildMessageService.addingPhoneNumberButton(message); //adding phone number button

        return newUser;
    }

    private void registerRestUserData(User user, Message message) {
        switch (user.getPosition()) {
            case PHONE_NUMBER: //phase 1
                if (message.hasContact()) {
                    user.setPhoneNumber(message.getContact().getPhoneNumber());
                    sendMsgAskAge(user);
                } else if (message.getText().equals("I don't want to disclose the phone number")) {
                    sendMsgAskAge(user);
                } else {
                    var newMessage = SendMessage.builder().
                            text("You haven't shared the phone number!").
                            chatId(message.getChatId().toString()).build();
                    messageSender.messageSend(newMessage);


                    buildMessageService.addingPhoneNumberButton(message);
                }
                break;
            case AGE:
                if (message.getText().matches("\\d{1,2}")) {
                    user.setAge(message.getText());
                    user.setPosition(Position.NONE);
                    buildMessageService.buildButtons(message);
                    User.setActiveUserRegistration(false);
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

    private void sendMsgAskAge(User user){
        ReplyKeyboardRemove replyKeyboardRemove = new ReplyKeyboardRemove(); //removes the phone number keyboard
        replyKeyboardRemove.setRemoveKeyboard(true);

        user.setPosition(Position.AGE);
        SendMessage newMessage = new SendMessage();
        newMessage.setText("Please, type your <i>age</i> at this chat");
        newMessage.setParseMode("HTML");
        newMessage.setChatId(user.getId().toString());
        newMessage.setReplyMarkup(replyKeyboardRemove);
        messageSender.messageSend(newMessage);
    }

}
