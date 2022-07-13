package com.tehcman.input_final_destination.SendMessage_factories;

import com.tehcman.cahce.Cache;
import com.tehcman.entities.Position;
import com.tehcman.entities.Status;
import com.tehcman.entities.User;
import com.tehcman.services.BuildButtonsService;
import com.tehcman.services.Emoji;
import com.tehcman.services.IBuildSendMessageService;
import com.tehcman.services.keyboards.*;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;

@Component
public class CacheFactory implements ISendMessageFactory {
    private final IBuildSendMessageService ibuildSendMessageService;
    private final Cache<User> userCache;
    private BuildButtonsService buildButtonsService;

    public CacheFactory(IBuildSendMessageService ibuildSendMessageService, Cache<User> userCache) {
        this.ibuildSendMessageService = ibuildSendMessageService;
        this.userCache = userCache;
    }


    @Override
    public SendMessage createSendMessage(Message message) {
        //if no user is found in the registry(cache), start a new user registration
        User userFromCache = userCache.findBy(message.getChatId());
        if (userFromCache == null) {
            User newUser = generateDefaultUserInformationFromMessage(message);
            userCache.add(newUser);
            return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Please, choose who you are", buildButtonsService.getMainMarkup());

        } else if (userFromCache.getPosition() == Position.NONE) {
            return new SendMessage(message.getChatId().toString(), "Hey. You are already in the system." + " Instead of duplicating data of yourself, do something useful in your life");
        } else {
            return registerRestUserData(userFromCache, message);
        }
    }


    private User generateDefaultUserInformationFromMessage(Message message) {
        User newUser = new User(message.getChatId(), message.getFrom().getUserName(), message.getFrom().getFirstName(), Position.STATUS);
        this.buildButtonsService = new BuildButtonsService(new AddStatusKeyboard());
//        buildButtonsService.addingPhoneNumberButton(); //adding phone number button
        return newUser;
    }

    private SendMessage registerRestUserData(User user, Message message) {
        switch (user.getPosition()) {
            case STATUS:
                if (message.getText().equals("Searching Accommodation")) {
                    user.setStatus(Status.REFUGEE);
                    user.setPosition(Position.NAME);

                    this.buildButtonsService = new BuildButtonsService(new AddSkipButtonKeyboardRow());
                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Type your name or SKIP if you want to set your default Telegram name", buildButtonsService.getMainMarkup());
                } else if (message.getText().equals("Providing Accommodation")) {
                    user.setStatus(Status.HOST);
                    user.setPosition(Position.NAME);

                    this.buildButtonsService = new BuildButtonsService(new AddSkipButtonKeyboardRow());
                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Type your name or SKIP if you want to set your default Telegram name", buildButtonsService.getMainMarkup());

                } else {
                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "You must press on a button!", buildButtonsService.getMainMarkup());
                }

            case NAME:
                if (!(message.getText().equals("SKIP " + Emoji.BLACK_RIGHTWARDS_ARROW))) {
                    user.setPosition(Position.SEX);
                    user.setName(message.getText());

                    this.buildButtonsService = new BuildButtonsService(new AddSexKeyboard());
                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Are you man or woman?", buildButtonsService.getMainMarkup());
                }
                user.setPosition(Position.SEX);

                this.buildButtonsService = new BuildButtonsService(new AddSexKeyboard());
                return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Are you man or woman?", buildButtonsService.getMainMarkup());
            case SEX:
                if (message.getText().equals("Male")) {
                    user.setSex('M');
                    user.setPosition(Position.PHONE_NUMBER);

                    this.buildButtonsService = new BuildButtonsService(new AddPhoneNumberKeyboard());
                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Please, press on the \"Phone number\" button", buildButtonsService.getMainMarkup());
                } else if (message.getText().equals("Woman")) {
                    user.setSex('F');
                    user.setPosition(Position.PHONE_NUMBER);

                    this.buildButtonsService = new BuildButtonsService(new AddPhoneNumberKeyboard());
                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Please, press on the \"Phone number\" button", buildButtonsService.getMainMarkup());
                } else if (message.getText().equals("SKIP " + Emoji.BLACK_RIGHTWARDS_ARROW)) {
                    user.setSex(null);
                    user.setPosition(Position.PHONE_NUMBER);
                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Please, press on the \"Phone number\" button", buildButtonsService.getMainMarkup());
                } else {
                    var newMessage = SendMessage.builder().text("You haven't pressed a button!").chatId(message.getChatId().toString()).build();
                    this.buildButtonsService = new BuildButtonsService(new AddPhoneNumberKeyboard());
                    return newMessage;
                }

            case PHONE_NUMBER: //phase 1
                if (message.hasContact()) {
                    user.setPhoneNumber(message.getContact().getPhoneNumber());
                    user.setPosition(Position.AGE);
                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Please, type your <i>age</i> at this chat", new ReplyKeyboardRemove(Boolean.TRUE));
                    //FIXME: POSSIBLE REFACTORING following line?
                } else if (message.getText().equals("SKIP " + Emoji.BLACK_RIGHTWARDS_ARROW)) {
                    ReplyKeyboardRemove replyKeyboardRemove = new ReplyKeyboardRemove(); //removes the phone number keyboard
                    replyKeyboardRemove.setRemoveKeyboard(true);
                    user.setPosition(Position.AGE);
                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Please, type your <i>age</i> at this chat", replyKeyboardRemove);
                } else {
                    var newMessage = SendMessage.builder().text("You haven't shared the phone number!").chatId(message.getChatId().toString()).build();
                    this.buildButtonsService = new BuildButtonsService(new AddPhoneNumberKeyboard());
                    return newMessage;
                }

            case AGE:
                if (message.getText().equals("SKIP " + Emoji.BLACK_RIGHTWARDS_ARROW)) {
                    user.setAge(null);
                    user.setPosition(Position.CITY);

                    this.buildButtonsService = new BuildButtonsService(new AddSkipButtonKeyboardRow());
                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Please, type a city where you are planning to stay", buildButtonsService.getMainMarkup());

                } else if (message.getText().matches("\\d{1,2}")) {
                    user.setAge(message.getText());
                    user.setPosition(Position.CITY);

                    this.buildButtonsService = new BuildButtonsService(new AddSkipButtonKeyboardRow());
                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Please, type a city where you are planning to stay", buildButtonsService.getMainMarkup());

                } else {
                    SendMessage newMessage = new SendMessage();
                    newMessage.setText("Please, enter a <u>number</u> (0-99)");
                    newMessage.setParseMode("HTML");
                    newMessage.setChatId(user.getId().toString());

                    return newMessage;
                }
            case CITY:
                if (message.getText().equals("SKIP " + Emoji.BLACK_RIGHTWARDS_ARROW)) {
                    user.setCity(null);

                } else {
                    user.setCity(message.getText());

                }
                user.setPosition(Position.DATE);
                this.buildButtonsService = new BuildButtonsService(new AddYesNo());
                return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Do you know your arrival date?", buildButtonsService.getMainMarkup());
            case DATE:
                if (message.getText().equalsIgnoreCase("no")) {
                    user.setDate(null);
                    user.setPosition(Position.AMOUNT_PEOPLE);

                    this.buildButtonsService = new BuildButtonsService(new AddAmountOfPeopleKeyboard());
                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "How many people are looking for housing? ", buildButtonsService.getMainMarkup());
                } else if (message.getText().equalsIgnoreCase("yes")) {
                    this.buildButtonsService.getMainMarkup().setOneTimeKeyboard(Boolean.TRUE);
                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Please, type a date at this chat\n" +
                            "(YYYY-MM-DD)\n" +
                            "or time frame\n" +
                            "(October/Summer etc.)", buildButtonsService.getMainMarkup());

                } else if (!(message.getText().equalsIgnoreCase("no")) || !(message.getText().equalsIgnoreCase("yes"))) {
                    user.setDate(message.getText());
                    user.setPosition(Position.AMOUNT_PEOPLE);

                    this.buildButtonsService = new BuildButtonsService(new AddAmountOfPeopleKeyboard());
                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "How many people are looking for housing? ", buildButtonsService.getMainMarkup());

                }
            case AMOUNT_PEOPLE:
                if ((message.getText().equalsIgnoreCase("I'm alone")) || (message.getText().equals("SKIP " + Emoji.BLACK_RIGHTWARDS_ARROW))) {
                    user.setAmountOfPeople(1);
                    user.setPosition(Position.ADDITIONAL);

                    this.buildButtonsService = new BuildButtonsService(new AddYesNo());
                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Do you have additional comments\n" +
                            "(You have little children, pets etc.)?", buildButtonsService.getMainMarkup());
                }
                else if(message.getText().equalsIgnoreCase("A group of people")){
                    this.buildButtonsService = new BuildButtonsService(new AddSkipButtonKeyboardRow());
                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Please, type a number (including you) at this chat",
                            buildButtonsService.getMainMarkup());
                }
                else if (message.getText().matches("\\d{1,2}")){
                    user.setAmountOfPeople(Integer.valueOf(message.getText()));
                    user.setPosition(Position.ADDITIONAL);

                    this.buildButtonsService = new BuildButtonsService(new AddYesNo());
                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Do you have additional comments\n" +
                            "(You have little children, pets etc.)?", buildButtonsService.getMainMarkup());
                }
                else {
                    SendMessage newMessage = new SendMessage();
                    newMessage.setText("Please, enter a <u>number</u> (0-99)");
                    newMessage.setParseMode("HTML");
                    newMessage.setChatId(user.getId().toString());

                    return newMessage;
                }
            case ADDITIONAL:
                if (message.getText().equalsIgnoreCase("no")) {
                    user.setAdditional(null);
                    user.setPosition(Position.NONE);

                    this.buildButtonsService = new BuildButtonsService(new AfterRegistrationKeyboard());
                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Thank you! \n" +
                            "\n" +
                            "Your data has been saved. It is available only to other users if this service\n" +
                            "Now you can view users who\n" +
                            "ready to provide housing", buildButtonsService.getMainMarkup());
                } else if (message.getText().equalsIgnoreCase("yes")) {
                    this.buildButtonsService.getMainMarkup().setOneTimeKeyboard(Boolean.TRUE);
                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Please, type your comment at this chat", buildButtonsService.getMainMarkup());

                } else if (!(message.getText().equalsIgnoreCase("no")) || !(message.getText().equalsIgnoreCase("yes"))) {
                    user.setAdditional(message.getText());
                    user.setPosition(Position.NONE);

                    this.buildButtonsService = new BuildButtonsService(new AfterRegistrationKeyboard());
                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Thank you! \n" +
                            "\n" +
                            "Your data has been saved. It is available only to other users if this service\n" +
                            "Now you can view users who\n" +
                            "ready to provide housing", buildButtonsService.getMainMarkup());
                }

        }
        System.out.println(user);
        return null;
    }
}
