package com.tehcman.handlers;


/* Problem: @Autowire does not see the components
*  TODO:
*  1. watch the video
*  2. fix autowire
* */
//https://www.youtube.com/watch?v=__M8QtrpsB4 (possible fix)

import com.tehcman.BotEntryPoint;
import com.tehcman.informational_portal.ListOfNewsChannels;
import com.tehcman.processors.DefaultProcessor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.junit.jupiter.api.Assertions.*;

@Component
public class TextIHandlerTest {

    private TextIHandler textHandler;
    private Update update;
    private BotEntryPoint botEntryPoint;
    private DefaultProcessor defaultProcessor;

    @Autowired
    public TextIHandlerTest(TextIHandler textHandler, BotEntryPoint botEntryPoint, DefaultProcessor defaultProcessor) {
        this.textHandler = textHandler;
        this.botEntryPoint = botEntryPoint;
        this.defaultProcessor = defaultProcessor;
    }

    public void setUpdate() {
        Message msg = new Message();
        msg.setText("List of TG news channels on Ukraine (ENG)");
        this.update = new Update();
        update.setMessage(msg);
    }

    @Test
    public void testHandleListOfChannelsButton() {
        setUpdate();
        defaultProcessor.handleText(update);

        assertEquals(textHandler.getBotResponseForTesting(), new ListOfNewsChannels().getMapDescription());
    }
}