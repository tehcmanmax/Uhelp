package com.tehcman.processors;

import com.tehcman.handlers.CallBackQueryHandler;
import com.tehcman.handlers.SaveToCacheHandler;
import com.tehcman.handlers.TextHandler;
import com.tehcman.handlers.StartHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class DefaultProcessor implements Processor{
    private final CallBackQueryHandler callBackQueryHandler;
    private final TextHandler textHandler;
    private final StartHandler startHandler;
    private final SaveToCacheHandler saveToCacheHandler;

    @Autowired
    public DefaultProcessor(CallBackQueryHandler callBackQueryHandler, TextHandler textHandler, StartHandler startHandler, SaveToCacheHandler saveToCacheHandler) {
        this.callBackQueryHandler = callBackQueryHandler;
        this.textHandler = textHandler;
        this.startHandler = startHandler;
        this.saveToCacheHandler = saveToCacheHandler;
    }


    @Override
    public void handleCallBackQuery(CallbackQuery update) {
        callBackQueryHandler.handle(update);
    }

    @Override
    public void handleSaveToCache(Message message) {
           saveToCacheHandler.handle(message);
    }

    @Override
    public void handleStart(Update update) {
        startHandler.handle(update.getMessage());
    }

    @Override
    public void handleText(Update update) {
        textHandler.handle(update.getMessage());
    }

}
