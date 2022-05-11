package com.tehcman.services;
// TODO: 2/18/2022

import com.tehcman.sendmessage.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Collections;

@Service
public class BuildMessageService {
    private final MessageSender messageSender;
//    private ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();


    //spring init it with component/bean that implement MessageSender interface
    @Autowired
    public BuildMessageService(@Lazy MessageSender messageSender) {
        this.messageSender = messageSender;
    }


    public void buildTGmessageTest(Message message) {
        var ms1 = SendMessage.builder() //static method; otherwise I need implement it by myself
                .text("<u>Testing this shit</u>")
                .chatId(message.getChatId().toString())
                .parseMode("HTML")
                .build();

        messageSender.messageSend(ms1);
    }

    public void buildButtons(Message message) {
        ArrayList<KeyboardRow> arrayOfKeyboardRows = new ArrayList<>();
        ReplyKeyboardMarkup mainMarkup = new ReplyKeyboardMarkup();


        String messageToTheUser = chooseMsgForUser(message);

        var row1 = new KeyboardRow();
        row1.add("I want a joke"); //check for the poem
        row1.add("You're dumb");

        var row2 = new KeyboardRow();
        var button3 = new KeyboardButton("Temporary save my info into the cache");
        row2.add(button3);


        Collections.addAll(arrayOfKeyboardRows, row1, row2);

        mainMarkup.setKeyboard(arrayOfKeyboardRows);
        mainMarkup.setResizeKeyboard(true);

        //without the following lines, buttons won't build
        SendMessage sendThisMessage = new SendMessage();
        sendThisMessage.setChatId(message.getChatId().toString());
        sendThisMessage.setReplyMarkup(mainMarkup);
        sendThisMessage.setParseMode("HTML");
        sendThisMessage.setText(messageToTheUser);

        messageSender.messageSend(sendThisMessage);
    }

    //triggers if we register a new user

    public void addingPhoneNumberButton(Message message) {
        var markup = new ReplyKeyboardMarkup();
        markup.setResizeKeyboard(true);
        markup.setOneTimeKeyboard(false);


        var phoneNumberButton = KeyboardButton.builder().text("Phone number").requestContact(Boolean.TRUE).build();
        var declineSahringPhoneNumber = KeyboardButton.builder().text("I don't want to disclose the phone number").build();

        var row1 = new KeyboardRow();
        Collections.addAll(row1, phoneNumberButton, declineSahringPhoneNumber);

        var arrayOfKeyboardRows = new ArrayList<KeyboardRow>();
        arrayOfKeyboardRows.add(row1);


        markup.setKeyboard(arrayOfKeyboardRows);
        markup.setResizeKeyboard(Boolean.TRUE);

        //without the following lines, buttons won't build
        SendMessage sendThisMessage = new SendMessage();
        sendThisMessage.setChatId(message.getChatId().toString());
        sendThisMessage.setReplyMarkup(markup);
        sendThisMessage.setParseMode("HTML");
        sendThisMessage.setText("Please, press on the \"Phone number\" button");

        messageSender.messageSend(sendThisMessage);
    }
/*
    public void removingPhoneNumberButton(Message message) {
        this.markup.
    }
*/

    //SHOULD I KEEP THE FOLLOWING CODE?
    //after pressing a button the user will receive a message
    private String chooseMsgForUser(Message message) {
        String messageToTheUser;
        switch (message.getText()) {
            case "/start":
                messageToTheUser = "Yay! You've just launched this bot!";
                break;
            case "Temporary save my info into the cache":
                messageToTheUser = "Press button Phone Number";
                break;
            default:
                messageToTheUser = "ok";
        }
        return messageToTheUser;
    }

}
