package com.tehcman.processors;

import com.tehcman.cahce.Cache;
import com.tehcman.entities.Phase;
import com.tehcman.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public abstract class Processor {

    abstract void handleText(Update update);

    abstract void handleCallBackQuery(CallbackQuery update);

    abstract void handleSaveToCache(Message message);

    abstract void commandHandler(Message message);

    private Cache<User> userCache;

    @Autowired
    public void setUserCache(Cache<User> userCache) {
        this.userCache = userCache;
    }

    //TODO fix
    public void direct(Update update) {
        if ((update.getMessage() != null) && (update.getMessage().getText() != null)) {
            if (update.getMessage().getText().equals("Accommodation search/hosting")) {
                handleSaveToCache(update.getMessage());
            } else if (update.getMessage().getText().equals("/start")) {
                commandHandler(update.getMessage());
            } else
                handleText(update);
        }

        //active registration
        else if (update.hasCallbackQuery()) {
            handleCallBackQuery(update.getCallbackQuery());
        }//forwarding the special Telegram commands /...
        else {
            User userFromCache = userCache.findBy(update.getMessage().getChatId());
            if ((userFromCache != null) && !userFromCache.getPhase().equals(Phase.NONE)) {
/*                switch (userFromCache.getPosition()) {
                    case PHONE_NUMBER:
                    case AGE:*/
                handleSaveToCache(update.getMessage());
                return; //so it won't go to if lines
//                }
            }
        }
    }
}
