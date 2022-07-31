package com.tehcman.input_final_destination.handlers;

import com.tehcman.cahce.Cache;
import com.tehcman.entities.Status;
import com.tehcman.entities.User;
import com.tehcman.input_final_destination.SendMessage_factories.CacheFactoryHost;
import com.tehcman.input_final_destination.SendMessage_factories.CacheFactoryRefugee;
import com.tehcman.sendmessage.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class SaveToCacheIHandler implements IHandler<Message> {

    private final MessageSender messageSender;
    private final CacheFactoryRefugee cacheFactoryRefugee;
    private final CacheFactoryHost cacheFactoryHost;
    private final InitDataAndStatusHandler initDataAndStatusHandler;
    private final Cache<User> userCache;

    @Autowired
    public SaveToCacheIHandler(MessageSender messageSender, CacheFactoryRefugee cacheFactoryRefugee, CacheFactoryHost cacheFactoryHost, InitDataAndStatusHandler initDataAndStatusHandler, Cache<User> userCache) {
        this.messageSender = messageSender;
        this.cacheFactoryRefugee = cacheFactoryRefugee;
        this.cacheFactoryHost = cacheFactoryHost;
        this.initDataAndStatusHandler = initDataAndStatusHandler;
        this.userCache = userCache;
    }


    @Override
    public void handle(Message message) {
        if ((userCache.findBy(message.getChatId()) == null)) {
            initDataAndStatusHandler.createSendMessage(message);
        } else {
            User user = userCache.findBy(message.getChatId());
            if (user.getStatus() == Status.HOST) {
                SendMessage newMsg = cacheFactoryRefugee.createSendMessage(message);
                messageSender.messageSend(newMsg);
            } else {
                SendMessage newMsg = cacheFactoryHost.createSendMessage(message);
                messageSender.messageSend(newMsg);
            }
        }
    }

}
