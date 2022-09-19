package com.tehcman.input_final_destination.handlers;

import com.tehcman.cahce.UserCache;
import com.tehcman.entities.Status;
import com.tehcman.entities.User;
import com.tehcman.input_final_destination.SendMessage_factories.Text1SendMessageFactory;
import com.tehcman.input_final_destination.SendMessage_factories.ISendMessageAbstractFactory;
import com.tehcman.input_final_destination.SendMessage_factories.Text2SendMessageAbstractFactory;
import com.tehcman.input_final_destination.handlers.callbacks.CallBackHostProfileNavigation;
import com.tehcman.input_final_destination.handlers.callbacks.CallBackRefugeeProfileNavigation;
import com.tehcman.printers.HostProfile;
import com.tehcman.printers.RefugeeProfile;
import com.tehcman.sendmessage.MessageSender;
import com.tehcman.services.FetchRandomUniqueUserService;
import com.tehcman.services.IBuildSendMessageService;
import com.tehcman.services.keyboards.profile_search.InlineNoProfiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;

@Component
public class TextHandler implements IHandler<Message> {
    private final MessageSender messageSender;
    private final HostProfile hostProfile;
    private final RefugeeProfile refugeeProfile;
    private final Text1SendMessageFactory text1SendMessageFactory;
    private final ISendMessageAbstractFactory create2SendMessagesFactory;
    private final UserCache userCache;
    private final FetchRandomUniqueUserService fetchRandomUniqueUserService;
    private final InlineNoProfiles inlineNoProfiles;
    private final IBuildSendMessageService iBuildSendMessageService;
    private final CallBackHostProfileNavigation callBackHostProfileNavigation;
    private final CallBackRefugeeProfileNavigation callBackRefugeeProfileNavigation;

    @Autowired
    public TextHandler(@Lazy MessageSender messageSender, HostProfile hostProfile, RefugeeProfile refugeeProfile, Text1SendMessageFactory text1SendMessageFactory, Text2SendMessageAbstractFactory create2SendMessagesFactory, UserCache userCache, FetchRandomUniqueUserService fetchRandomUniqueUserService, InlineNoProfiles inlineNoProfiles, IBuildSendMessageService iBuildSendMessageService, CallBackHostProfileNavigation callBackHostProfileNavigation, CallBackRefugeeProfileNavigation callBackRefugeeProfileNavigation) {
        this.messageSender = messageSender;
        this.hostProfile = hostProfile;
        this.refugeeProfile = refugeeProfile;
        this.text1SendMessageFactory = text1SendMessageFactory;
        this.create2SendMessagesFactory = create2SendMessagesFactory;
        this.userCache = userCache;
        this.fetchRandomUniqueUserService = fetchRandomUniqueUserService;
        this.inlineNoProfiles = inlineNoProfiles;
        this.iBuildSendMessageService = iBuildSendMessageService;
        this.callBackHostProfileNavigation = callBackHostProfileNavigation;
        this.callBackRefugeeProfileNavigation = callBackRefugeeProfileNavigation;
    }

    @Override
    public void handle(Message message) {
        //handling possible prior message to a user
        if (message.getText().equals("List of news channels about Ukraine (ENG)")) {
            SendMessage msg1 = create2SendMessagesFactory.create1stSendMessage(message);
            SendMessage msg2 = create2SendMessagesFactory.create2ndSendMessage(message);

            messageSender.messageSend(msg1);
            messageSender.messageSend(msg2);

        } else if ((message.getText().equals("Show me shelter seeking people"))) {
            User user = userCache.findBy(message.getChatId());
            if (user == null) {
                throw new NullPointerException("Complete the registration first!");
            }

            User refugee1;
            refugee1 = fetchRandomUniqueUserService.fetchRandomUniqueUser(Status.REFUGEE);
            if (refugee1 == null) {
                SendMessage sendMessage = iBuildSendMessageService.createHTMLMessage(String.valueOf(message.getChatId()), "You've viewed all profiles. " +
                        "Show them again or we can notify you when new profiles appear", inlineNoProfiles.getMainMarkup());
                messageSender.messageSend(sendMessage);
            }


            if (refugee1.getStatus() != null) {
                SendMessage msg = SendMessage.builder()
                        .chatId(String.valueOf(message.getChatId()))
                        .replyMarkup(new ReplyKeyboardRemove(true))
                        .text("Showing you people who seek a shelter")
                        .build();
                messageSender.messageSend(msg);
            }
            this.callBackRefugeeProfileNavigation.setLastViewedRefugee(refugee1);

            refugeeProfile.printInline(message, refugee1);

        } else if ((message.getText().equals("Show me shelter providing people"))) {
            User user = userCache.findBy(message.getChatId());
            if (user == null) {
                throw new NullPointerException("Complete the registration first!");
            }

            User host1;
            host1 = fetchRandomUniqueUserService.fetchRandomUniqueUser(Status.HOST);

            if (host1 == null) {
                SendMessage sendMessage = iBuildSendMessageService.createHTMLMessage(String.valueOf(message.getChatId()), "You've viewed all profiles. " +
                        "Show them again or we can notify you when new profiles appear", inlineNoProfiles.getMainMarkup());
                messageSender.messageSend(sendMessage);
            }

            if (host1.getStatus() != null) {
                SendMessage msg = SendMessage.builder()
                        .chatId(String.valueOf(message.getChatId()))
                        .replyMarkup(new ReplyKeyboardRemove(true))
                        .text("Showing you available hosts")
                        .build();
                messageSender.messageSend(msg);
            }
            this.callBackHostProfileNavigation.setLastViewedHost(host1);

            hostProfile.printInline(message, host1);

        } else {
            SendMessage newMessageToUser = text1SendMessageFactory.createSendMessage(message);

            messageSender.messageSend(newMessageToUser);
        }
    }
}