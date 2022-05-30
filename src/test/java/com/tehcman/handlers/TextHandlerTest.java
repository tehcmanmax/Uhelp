package com.tehcman.handlers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.inlinequery.ChosenInlineQuery;
import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery;

import static org.junit.jupiter.api.Assertions.*;

class TextHandlerTest {

    private TextHandler textHandler;
    private Update update;


    @Autowired
    void setTextHandler(@Lazy TextHandler textHandler) {
        this.textHandler = textHandler;
    }

    void setUpdate() {
        Message msg = new Message();
        msg.setText("List of TG news channels on Ukraine (ENG)");
                this.update = new Update();
                update.setMessage(msg);
    }

    @Test
    void testHandleListOfChannelsButton() {
//        textHandler.handle(new Message(12, ));
        assertEquals(11, 11);
    }
}