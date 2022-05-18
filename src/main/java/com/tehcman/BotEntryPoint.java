package com.tehcman;

import com.tehcman.processors.Processor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Component
public class BotEntryPoint extends TelegramLongPollingBot {
    private final Processor processor;

    @Value("${telegrambot.botToken}")
    private String botToken;
    @Value("${telegrambot.botName}")
    private String botName;

    @Autowired
    public BotEntryPoint(@Lazy Processor processor) {
        this.processor = processor;
    }


    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.getMessage() != null) {
            System.out.println("\n ----------------------------");
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            System.out.println(dateFormat.format(date));
            log.info("\nNew message from User: {}, User first name: {}, User last name: {}, User ID: {}," +
                            " ChatId: {},  With text: {}",
                    update.getMessage().getFrom().getFirstName(),
                    update.getMessage().getFrom().getUserName(),
                    update.getMessage().getFrom().getLastName(),

                    update.getMessage().getChat().getId(),
                    update.getMessage().getChatId(),

                    update.getMessage().getText());

            processor.direct(update);
        } else {
            processor.direct(update);
        }
    }
}
