package com.tehcman.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Collections;

@Service
public class BuildButtonsService {
    private final ArrayList<KeyboardRow> arrayOfKeyboardRows;
    private final ReplyKeyboardMarkup mainMarkup;
    ReplyKeyboardRemove replyKeyboardRemove ; //removes the phone number keyboard

    public ReplyKeyboardMarkup getMainMarkup() {
        return mainMarkup;
    }

    @Autowired
    public BuildButtonsService() {
        this.arrayOfKeyboardRows = new ArrayList<>();
        this.mainMarkup = new ReplyKeyboardMarkup();
        this.replyKeyboardRemove = new ReplyKeyboardRemove(Boolean.TRUE);

        //prettifies the buttons
        this.mainMarkup.setKeyboard(arrayOfKeyboardRows);
        this.mainMarkup.setResizeKeyboard(true);
    }

    public void beforeRegistrationButtons() {
        arrayOfKeyboardRows.clear();

        var row2 = new KeyboardRow();
        var button3 = new KeyboardButton("Temporary save my info into the cache");
        row2.add(button3);

        Collections.addAll(arrayOfKeyboardRows, createCommonButtonsRow(), row2);
    }


    //triggers if we register a new user
    public void addingPhoneNumberButton() {
        arrayOfKeyboardRows.clear();

        var phoneNumberButton = KeyboardButton.builder().text("Phone number").requestContact(Boolean.TRUE).build();
        var declineSharingPhoneNumber = KeyboardButton.builder().text("I don't want to disclose the phone number").build();
        var row1 = new KeyboardRow();
        Collections.addAll(row1, phoneNumberButton, declineSharingPhoneNumber);

        arrayOfKeyboardRows.add(row1);
    }

    public void afterRegistrationButtons() {
        arrayOfKeyboardRows.clear();
        var row2 = new KeyboardRow();
        var button3 = new KeyboardButton("View my data");
        var button4 = new KeyboardButton("Remove my data");
        row2.add(button3);
        row2.add(button4);

        Collections.addAll(arrayOfKeyboardRows, createCommonButtonsRow(), row2);
    }


    //this buttons used for before and after registration
    private KeyboardRow createCommonButtonsRow() {
        var row1 = new KeyboardRow();
        row1.add("I want a joke");
        row1.add("You're dumb");
        return row1;
    }
}