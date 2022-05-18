package com.tehcman.processors;

import com.tehcman.handlers.CallBackQueryHandler;
import com.tehcman.handlers.SaveToCacheHandler;
import com.tehcman.handlers.TextHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class DefaultProcessor extends Processor{
    private final CallBackQueryHandler callBackQueryHandler;
    private final TextHandler textHandler;
    private final SaveToCacheHandler saveToCacheHandler;

    @Autowired
    public DefaultProcessor(CallBackQueryHandler callBackQueryHandler, TextHandler textHandler, SaveToCacheHandler saveToCacheHandler) {
        this.callBackQueryHandler = callBackQueryHandler;
        this.textHandler = textHandler;
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
    public void handleText(Update update) {
        textHandler.handle(update.getMessage());
    }

}