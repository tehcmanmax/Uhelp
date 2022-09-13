package com.tehcman.processors;

import com.tehcman.input_final_destination.handlers.callbacks.CallBackHostProfileNavigation;
import com.tehcman.input_final_destination.handlers.registration.SaveToCacheIHandler;
import com.tehcman.input_final_destination.handlers.TextHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class DefaultProcessor extends Processor{
    private final CallBackHostProfileNavigation callBackQueryHandler;
    private final TextHandler textHandler;
    private final SaveToCacheIHandler saveToCacheHandler;

    @Autowired
    public DefaultProcessor(CallBackHostProfileNavigation callBackQueryHandler, TextHandler textHandler, SaveToCacheIHandler saveToCacheHandler) {
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