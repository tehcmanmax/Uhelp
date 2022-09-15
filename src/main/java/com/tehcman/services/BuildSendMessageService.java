package com.tehcman.services;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

@Service
public class BuildSendMessageService implements IBuildSendMessageService {

    public SendMessage createHTMLMessage(String chatID, String text, ReplyKeyboard mainMarkup) {
        SendMessage sendThisMessage = new SendMessage();
        sendThisMessage.setChatId(chatID);
        sendThisMessage.setReplyMarkup(mainMarkup);
        sendThisMessage.setParseMode("HTML");
        sendThisMessage.setText(text);
        return sendThisMessage;
    }

    public EditMessageText createHTMLEditMessage(String text, CallbackQuery inlineButtonPressed, InlineKeyboardMarkup mainMarkup){
/*        EditMessageText sendThisMessage = new EditMessageText();
        sendThisMessage.setReplyMarkup(mainMarkup);
        sendThisMessage.setParseMode("HTML");
        sendThisMessage.setText(text);
        sendThisMessage.setInlineMessageId(chatID);
        sendThisMessage.setMessageId(Integer.valueOf(chatID));*/

        var editMessageText = EditMessageText.builder()
                .text(text)
                .chatId(inlineButtonPressed.getMessage().getChatId().toString())
                .messageId(inlineButtonPressed.getMessage().getMessageId())
                .replyMarkup(mainMarkup)
                .build();

        return editMessageText;
    }
}