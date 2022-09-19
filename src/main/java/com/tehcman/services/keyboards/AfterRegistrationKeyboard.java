package com.tehcman.services.keyboards;

import com.tehcman.cahce.Cache;
import com.tehcman.cahce.UserCache;
import com.tehcman.entities.Status;
import com.tehcman.entities.User;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AfterRegistrationKeyboard extends ReplyKeyboardMarkup {
    private final DefaultKeyboardRows keyboardRows;

    private final Cache<User> userCache;
    private final Long userId;

    public AfterRegistrationKeyboard(Message message, Cache<User> userCache) {
        this.keyboardRows = new DefaultKeyboardRows();
        this.userCache = userCache;
        this.userId = message.getChatId();
    }

    public AfterRegistrationKeyboard(Long userId, Cache<User> userCache) {
        this.keyboardRows = new DefaultKeyboardRows();
        this.userCache = userCache;
        this.userId = userId;
    }

    public @NonNull List<KeyboardRow> getKeyboard() {
        List<KeyboardRow> arrayOfKeyboardRows = new ArrayList<>();

        var row3 = new KeyboardRow();
        var row4 = new KeyboardRow();
        var button3 = new KeyboardButton("View my data");
        var button4 = new KeyboardButton("Remove my data");

        var button5 = new KeyboardButton("Oops, something is wrong");

        if (userCache.findBy(this.userId).getStatus() == Status.HOST) {
            button5 = new KeyboardButton("Show me shelter seeking people");
        }
        else if (userCache.findBy(this.userId).getStatus() == Status.REFUGEE) {
            button5 = new KeyboardButton("Show me shelter providing people");
        }

        row3.add(button3);
        row3.add(button4);
        row4.add(button5);

        Collections.addAll(arrayOfKeyboardRows, keyboardRows.create1stRow(), /*keyboardRows.create2ndRow(),*/ row3, row4);
        return arrayOfKeyboardRows;
    }
}
