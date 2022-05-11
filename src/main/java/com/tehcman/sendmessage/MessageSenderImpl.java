package com.tehcman.sendmessage;

import com.tehcman.BotEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
//sends messages to a user
public class MessageSenderImpl implements MessageSender{
    private BotEntryPoint entryPoint;

    @Autowired
    public void setEntryPoint(@Lazy BotEntryPoint entryPoint) {
        this.entryPoint = entryPoint;
    }


    @Override
    public void messageSend(SendMessage sendMessage) {
        try {
            entryPoint.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void editMessageSend(EditMessageText editMessageText) {
        try {
            entryPoint.execute(editMessageText);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
