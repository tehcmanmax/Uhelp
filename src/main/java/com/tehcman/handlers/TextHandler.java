package com.tehcman.handlers;

import com.tehcman.sendmessage.MessageSender;
import com.tehcman.services.BuildInlineButtonsService;
import com.tehcman.services.BuildMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;


@Component
public class TextHandler implements Handler<Message> {
    private final MessageSender messageSender;
    private final BuildMessageService buildMessageService;
    private final BuildInlineButtonsService buildInlineButtonsService; //testing the inline buttons

    @Autowired
    public TextHandler(@Lazy MessageSender messageSender, BuildMessageService buildMessageService, BuildInlineButtonsService buildInlineButtonsService) {
        this.messageSender = messageSender;
        this.buildMessageService = buildMessageService;
        this.buildInlineButtonsService = buildInlineButtonsService;
    }


    @Override
    public void handle(Message message) {
        if (message.getText().equals("I want a joke")) {
            var sendMessage = SendMessage.builder()
                    .text("Are you ready for my collection of the most hilarious jokes??\nIf so, press the button below!")
                    .chatId(message.getChatId().toString())
                    .build();

            sendMessage.setReplyMarkup(buildInlineButtonsService.build());
            messageSender.messageSend(sendMessage);
        } else if (message.getText().equals("You're dumb")) {
            var sendMsg = new SendMessage(message.getChatId().toString(), "no, you're dumb!");
            messageSender.messageSend(sendMsg);
        }
        else{
            var sendMsg = new SendMessage(message.getChatId().toString(), "I did not understand you. Try to press/text something else");
            messageSender.messageSend(sendMsg);
        }
    }
}

