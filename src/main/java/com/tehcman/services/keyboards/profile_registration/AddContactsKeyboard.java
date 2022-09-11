package com.tehcman.services.keyboards.profile_registration;

import lombok.NonNull;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AddContactsKeyboard extends ReplyKeyboardMarkup {
    private final AddSkipButtonKeyboardRow addSkipButtonKeyboardRow;
    private final List<KeyboardRow> keyboard;

    public AddContactsKeyboard() {
        addSkipButtonKeyboardRow = new AddSkipButtonKeyboardRow();

        var row1 = new KeyboardRow();
        var row2 = new KeyboardRow();
        var row3 = new KeyboardRow();
        var row4 = new KeyboardRow();


        this.keyboard = new ArrayList<>();

        var phoneNumber = KeyboardButton.builder().text("Phone number").requestContact(Boolean.TRUE).build();
        var email = KeyboardButton.builder().text("Email").build();
        var social = KeyboardButton.builder().text("Social media").build();

        row1.add(phoneNumber);
        row2.add(social);
        row3.add(email);
        row4.add(this.addSkipButtonKeyboardRow.getKeyboard().get(0).get(0));

        Collections.addAll(this.keyboard, row1, row2, row3, row4);
    }


    public @NonNull List<KeyboardRow> getKeyboard() {
        return this.keyboard;
    }

    public void removeRow(String buttonName) {

/*
        int i = 0;
        for (KeyboardRow button : this.keyboard) {
            if (button.contains(buttonName)) {
                this.keyboard.remove(i);
            }
            i++;
        }
        return this.keyboard;
*/

/*
List<String> toRemove = new ArrayList<String>();

for (String str : myArrayList) {
    if (someCondition) {
        toRemove.add(str);
    }
}
myArrayList.removeAll(toRemove);

* */

        if (buttonName.equals("Phone number")) {
//            int index = this.keyboard.size() - 2;
//            this.keyboard.get(0).remove(0);
            this.keyboard.remove(0);
        }
        this.keyboard.removeIf(str -> str.contains(buttonName));
    }
}
