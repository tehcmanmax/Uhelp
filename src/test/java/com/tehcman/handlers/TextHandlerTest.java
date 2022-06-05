package com.tehcman.handlers;


/* Problem: cannot create unit tests
*  Solution:
*  impossible to create unit test since the textHandler is highly coupled;
*   create integrate tests with Mockito to resolve this
 */

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import static org.junit.jupiter.api.Assertions.*;

public class TextHandlerTest {

//    private TextHandler textHandler = new TextHandler();

    @Test
    public void test() {
//        textHandler.handle("");
        Message msg = new Message();
        msg.setText("/start");
        msg.setMessageId(324234);
        Chat chat = new Chat();
        chat.setId(324234L);
        msg.setChat(chat);

//        textHandler.handle(msg);
//        String actualResult = textHandler.getBotResponseForTesting();
//        assertEquals("Yay! You've just launched this bot!", actualResult);

    }

/*    @Test
    public void testHandleListOfChannelsButton() {
//        setUpdate();
//        botEntryPoint.onUpdateReceived(this.update);

//        defaultProcessor.handleText(update);


        assertEquals(textHandler.getBotResponseForTesting(), new ListOfNewsChannels().getMapDescription());
    }*/
}