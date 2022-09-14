package com.tehcman.processors;

import com.tehcman.cahce.Cache;
import com.tehcman.entities.Phase;
import com.tehcman.entities.Status;
import com.tehcman.entities.User;
import com.tehcman.input_final_destination.handlers.commands.CommandHandler;
import com.tehcman.services.keyboards.SuspendedRegistrationKeyboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public abstract class Processor {

    abstract void handleText(Update update);

    abstract void handleCallBackQueryHostNav(CallbackQuery update);

    abstract void handleCallBackQueryRefNav(CallbackQuery update);

    abstract void handleSaveToCache(Message message);

    private Cache<User> userCache;
    private CommandHandler commandHandler;
    private SuspendedRegistrationKeyboard suspendedRegistrationKeyboard;

    @Autowired
    public void setCommandHandler(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    @Autowired
    public void setSuspendedRegistrationKeyboard(SuspendedRegistrationKeyboard suspendedRegistrationKeyboard) {
        this.suspendedRegistrationKeyboard = suspendedRegistrationKeyboard;
    }

    @Autowired
    public void setUserCache(Cache<User> userCache) {
        this.userCache = userCache;
    }

    public void direct(Update update) {

        if ((update.getMessage() != null) && (commandHandler.handleCommand(update.getMessage()))) {
            //handles commands!
            return;
        }

        if ((update.getMessage() != null) && (update.getMessage().getText() != null) /*&& (update.getMessage().getText().equals("Continue the registration"))*/) {
            this.suspendedRegistrationKeyboard.setSuspended(false);
        }

        if (update.hasCallbackQuery()) {
            if (userCache.findBy(update.getCallbackQuery().getFrom().getId()).getStatus().equals(Status.REFUGEE)) {
                handleCallBackQueryHostNav(update.getCallbackQuery());
                return;
            } else
                handleCallBackQueryRefNav(update.getCallbackQuery());
            return;
        } else {
            User userFromCache = userCache.findBy(update.getMessage().getChatId());
            if ((userFromCache != null) && (!userFromCache.getPhase().equals(Phase.NONE)) && (!suspendedRegistrationKeyboard.getSuspended())) {
/*                switch (userFromCache.getPosition()) {
                    case PHONE_NUMBER:
                    case AGE:*/
                handleSaveToCache(update.getMessage());
                return; //so it won't go to if lines
//                }
            }
        }

        //FIXME put the InitDataAndStatus class method instaed of handle...
        if ((update.getMessage() != null) && (update.getMessage().getText() != null) &&
                ((update.getMessage().getText().equals("Accommodation search/hosting")/* || (update.getMessage().getText().equals("Continue the registration")*/) &&
                        (!suspendedRegistrationKeyboard.getSuspended()))) {
            handleSaveToCache(update.getMessage());
        } else if ((update.getMessage() != null) && (update.getMessage().getText() != null)) {
            handleText(update);
        }
    }

}
