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
    ReplyKeyboardRemove replyKeyboardRemove; //removes the phone number keyboard

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

        var row3 = new KeyboardRow();

        //TODO implement the behavior after pressing on the buttons
//        var button3 = new KeyboardButton("Temporary save my info into the cache");
        var button3 = new KeyboardButton("Accommodation search/hosting");
        row3.add(button3);

        Collections.addAll(arrayOfKeyboardRows, create1stRow(), create2ndRow(), row3);
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
        var row3 = new KeyboardRow();
        var button3 = new KeyboardButton("View my data");
        var button4 = new KeyboardButton("Remove my data");
        row3.add(button3);
        row3.add(button4);

        Collections.addAll(arrayOfKeyboardRows, create1stRow(), create2ndRow(), row3);
    }


/*    private KeyboardRow createCommonButtonsRow() {
        var row1 = new KeyboardRow();
        var row2 = new KeyboardRow();
//        row1.add("I want a joke");
//        row1.add("You're dumb");
        row1.add("What's going on in Ukraine");
        row1.add("List of TG news channels on Ukraine (ENG)");
        return row1;
    }    */

    //this buttons used for before and after registration
    //TODO implement the behavior after pressing on the buttons
    private KeyboardRow create1stRow() {
        var row1 = new KeyboardRow();
        row1.add("What's going on in Ukraine");
        row1.add("List of TG news channels on Ukraine (ENG)");
        return row1;
    }

    private KeyboardRow create2ndRow() {
        var row2 = new KeyboardRow();
        row2.add("Donation (links)");
        row2.add("Change language");
        return row2;
    }
}