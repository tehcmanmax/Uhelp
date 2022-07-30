package com.tehcman.input_final_destination.SendMessage_factories;

import com.tehcman.cahce.Cache;
import com.tehcman.entities.Phase;
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
public class CacheFactoryRefugee implements ISendMessageFactory {
    private final IBuildSendMessageService ibuildSendMessageService;
    private BuildButtonsService buildButtonsService;
    private final Cache<User> userCache;

    private AddContactsKeyboard addContactsKeyboard;

    public CacheFactoryRefugee(IBuildSendMessageService ibuildSendMessageService, Cache<User> userCache) {
        this.ibuildSendMessageService = ibuildSendMessageService;
        this.userCache = userCache;
        addContactsKeyboard = new AddContactsKeyboard();
    }

    public void setAddContactsKeyboard(AddContactsKeyboard addContactsKeyboard) {
        this.addContactsKeyboard = addContactsKeyboard;
    }

    @Override
    public SendMessage createSendMessage(Message message) {
        //if no userFromCache is found in the registry(cache), start a new userFromCache registration
        User userFromCache = userCache.findBy(message.getChatId());
        switch (userFromCache.getPhase()) {
            case NAME:
                if (!(message.getText().equals("SKIP " + Emoji.BLACK_RIGHTWARDS_ARROW))) {
                    userFromCache.setPhase(Phase.SEX);
                    userFromCache.setName(message.getText());

                    this.buildButtonsService = new BuildButtonsService(new AddSexKeyboard());
                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Are you man or woman?", buildButtonsService.getMainMarkup());
                }
                userFromCache.setPhase(Phase.SEX);

                this.buildButtonsService = new BuildButtonsService(new AddSexKeyboard());
                return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Are you man or woman?", buildButtonsService.getMainMarkup());
            case SEX:
                if (message.getText().equals("Male")) {
                    userFromCache.setSex('M');
                    userFromCache.setPhase(Phase.CONTACTS);

                    this.buildButtonsService = new BuildButtonsService(addContactsKeyboard);
                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Please, leave contacts, so people can reach you out and help you", buildButtonsService.getMainMarkup());
                } else if (message.getText().equals("Female")) {
                    userFromCache.setSex('F');
                    userFromCache.setPhase(Phase.CONTACTS);

                    this.buildButtonsService = new BuildButtonsService(addContactsKeyboard);
                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Please, leave contacts, so people can reach you out and help you", buildButtonsService.getMainMarkup());
                } else if (message.getText().equals("SKIP " + Emoji.BLACK_RIGHTWARDS_ARROW)) {
                    userFromCache.setSex(null);
                    userFromCache.setPhase(Phase.CONTACTS);

                    this.buildButtonsService = new BuildButtonsService(addContactsKeyboard);
                    this.buildButtonsService.getMainMarkup().setOneTimeKeyboard(true);
                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Please, leave contacts, so people can reach you out and help you", buildButtonsService.getMainMarkup());
                } else {
                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Please, press on the <u>buttons</u>", buildButtonsService.getMainMarkup());
                }

            case CONTACTS:
                if (message.getText() != null) {
                    if ((message.getText().equals("SKIP " + Emoji.BLACK_RIGHTWARDS_ARROW))) {
                        userFromCache.setPhase(Phase.AGE);
                        this.buildButtonsService = new BuildButtonsService(new AddSkipButtonKeyboardRow());
                        return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Please, type your <i>age</i> at this chat", this.buildButtonsService.getMainMarkup());
                    } else if (message.getText().equals("Email")) {
                        userFromCache.setPhase(Phase.EMAIL);
                        return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Type your email", new ReplyKeyboardRemove(true));

                    } else if (message.getText().equals("Social media")) {
                        userFromCache.setPhase(Phase.SOCIAL);
                        return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Type a <u>link</u> or a @username to Instagram/Facebook etc.", new ReplyKeyboardRemove(true));
                    } else
                        return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Please, press on the <u>buttons</u>", buildButtonsService.getMainMarkup());
                } else if (message.hasContact()) {
                    this.addContactsKeyboard.removeRow("Phone number");
                    userFromCache.setPhase(Phase.CONTACTS);
                    userFromCache.setPhoneNumber(message.getContact().getPhoneNumber());
                    return isKeyboardSizeOne(userFromCache, message, ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Phone number is saved!", this.buildButtonsService.getMainMarkup()));
                }

            case EMAIL:
                userFromCache.setEmail(message.getText());
                userFromCache.setPhase(Phase.CONTACTS);
                this.addContactsKeyboard.removeRow("Email");
                return isKeyboardSizeOne(userFromCache, message, ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Email is saved!", this.buildButtonsService.getMainMarkup()));

            case SOCIAL:
                userFromCache.setSocial(message.getText());
                userFromCache.setPhase(Phase.CONTACTS);

                this.addContactsKeyboard.removeRow("Social media");
                return isKeyboardSizeOne(userFromCache, message, ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Social media is saved!", buildButtonsService.getMainMarkup()));

            case AGE:
                if (message.getText().equals("SKIP " + Emoji.BLACK_RIGHTWARDS_ARROW)) {
                    userFromCache.setAge(null);
                    userFromCache.setPhase(Phase.CITY);

                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Please, type a <b>city</b> where you are planning to stay", new ReplyKeyboardRemove(true));

                } else if (message.getText().matches("\\d{1,2}")) {
                    userFromCache.setAge(message.getText());
                    userFromCache.setPhase(Phase.CITY);

                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Please, type a <b>city</b> where you are planning to stay", new ReplyKeyboardRemove(true));

                } else {
                    SendMessage newMessage = new SendMessage();
                    newMessage.setText("Please, enter a <u>number</u> (0-99)");
                    newMessage.setParseMode("HTML");
                    newMessage.setChatId(userFromCache.getId().toString());

                    return newMessage;
                }
            case CITY:
                if (message.getText().matches("[^\\d\\W]{3,}")) {
                    userFromCache.setCity(message.getText());
                } else {
                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "You must type <i>full</i> city name", new ReplyKeyboardRemove(true));
                }
                userFromCache.setPhase(Phase.COUNTRY);
                this.buildButtonsService = new BuildButtonsService(new AddYesNo());

                this.buildButtonsService.getMainMarkup().setOneTimeKeyboard(Boolean.TRUE);
                return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Please, type a <b>country</b> where you are planning to stay", new ReplyKeyboardRemove(true));

            case COUNTRY:
                if (message.getText().matches("[^\\d\\W]{2,}")) {
                    userFromCache.setCountry(message.getText());
                } else {
                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "You must type <i>full</i> country name", new ReplyKeyboardRemove(true));
                }
                userFromCache.setPhase(Phase.DATE);
                this.buildButtonsService = new BuildButtonsService(new AddYesNo());

                this.buildButtonsService.getMainMarkup().setOneTimeKeyboard(Boolean.TRUE);
                return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Do you know your arrival date?", this.buildButtonsService.getMainMarkup());

            case DATE:
                if (message.getText().equalsIgnoreCase("no")) {
                    userFromCache.setDate(null);
                    userFromCache.setPhase(Phase.AMOUNT_PEOPLE);

                    this.buildButtonsService = new BuildButtonsService(new AddAmountOfPeopleKeyboard());
                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "How many people are looking for housing? ", buildButtonsService.getMainMarkup());
                } else if (message.getText().equalsIgnoreCase("yes")) {
                    userFromCache.setPhase(Phase.DATE_YES);
                    this.buildButtonsService = new BuildButtonsService(new AddSkipButtonKeyboardRow());
                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Please, type a date at this chat\n" +
                            "(YYYY-MM-DD)\n" +
                            "or time frame\n" +
                            "(October/Summer etc.)", this.buildButtonsService.getMainMarkup());

                } else {
                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Please, press on the <u>buttons</u>", buildButtonsService.getMainMarkup());
                }
            case DATE_YES:
                if (message.getText().equals("SKIP " + Emoji.BLACK_RIGHTWARDS_ARROW)) {
                    userFromCache.setDate(null);

                } else {
                    userFromCache.setDate(message.getText());
                }
                userFromCache.setPhase(Phase.AMOUNT_PEOPLE);
                this.buildButtonsService = new BuildButtonsService(new AddAmountOfPeopleKeyboard());
                return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "How many people are looking for housing? ", buildButtonsService.getMainMarkup());
            case AMOUNT_PEOPLE:
                if ((message.getText().equalsIgnoreCase("I'm alone")) || (message.getText().equals("SKIP " + Emoji.BLACK_RIGHTWARDS_ARROW))) {
                    userFromCache.setAmountOfPeople(1);
                    userFromCache.setPhase(Phase.ADDITIONAL);


                    this.buildButtonsService = new BuildButtonsService(new AddSkipButtonKeyboardRow());
                    this.buildButtonsService.getMainMarkup().setOneTimeKeyboard(Boolean.TRUE);
                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "If you have additional comments (You will arrive with little children, pets etc.) Please type them below."
                            + "\n\n"
                            + " If you don't have any comments, press on the <u>SKIP</u> button", buildButtonsService.getMainMarkup());
                } else if (message.getText().equalsIgnoreCase("A group of people")) {
                    userFromCache.setPhase(Phase.AMOUNT_PEOPLE_SUB);
                    this.buildButtonsService = new BuildButtonsService(new AddSkipButtonKeyboardRow());
                    this.buildButtonsService.getMainMarkup().setOneTimeKeyboard(Boolean.TRUE);

                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Please, type a number (including you) at this chat",
                            this.buildButtonsService.getMainMarkup());
                } else {
                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Please, press on the <u>buttons</u>", buildButtonsService.getMainMarkup());
                }

            case AMOUNT_PEOPLE_SUB:
                if (message.getText().matches("\\d{1,2}")) {
                    userFromCache.setAmountOfPeople(Integer.valueOf(message.getText()));
                    userFromCache.setPhase(Phase.ADDITIONAL);

                    this.buildButtonsService = new BuildButtonsService(new AddSkipButtonKeyboardRow());
                    this.buildButtonsService.getMainMarkup().setOneTimeKeyboard(Boolean.TRUE);
                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "If you have additional comments (You will arrive with little children, pets etc.) Please type them below."
                            + "\n\n"
                            + " If you don't have any comments, press on the <u>SKIP</u> button", buildButtonsService.getMainMarkup());
                } else {
                    SendMessage newMessage = new SendMessage();
                    newMessage.setText("Please, enter a <u>number</u> (0-99)");
                    newMessage.setParseMode("HTML");
                    newMessage.setChatId(userFromCache.getId().toString());

                    return newMessage;
                }
            case ADDITIONAL:
                if (message.getText().equals("SKIP " + Emoji.BLACK_RIGHTWARDS_ARROW)) {
                    userFromCache.setAdditional(null);
                    userFromCache.setPhase(Phase.NONE);

                    this.buildButtonsService = new BuildButtonsService(new AfterRegistrationKeyboard());
                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Thank you! \n" +
                            "\n" +
                            "Your data has been saved. It is available only to other users if this service\n" +
                            "Now you can view users who\n" +
                            "ready to provide housing", buildButtonsService.getMainMarkup());

                } else {
                    userFromCache.setAdditional(message.getText());
                    userFromCache.setPhase(Phase.NONE);

                    this.buildButtonsService = new BuildButtonsService(new AfterRegistrationKeyboard());
                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Thank you! \n" +
                            "\n" +
                            "Your data has been saved. It is available only to other users if this service\n\n" +
                            "Now you can view users who is ready to provide housing to you", buildButtonsService.getMainMarkup());

                }
        }
        System.out.println(userFromCache);
        return null;
    }

    private SendMessage isKeyboardSizeOne(User userFromCache, Message message, SendMessage sendMessage) {
        if ((this.addContactsKeyboard.getKeyboard().size() <= 1)) {
            userFromCache.setPhase(Phase.AGE);
            this.buildButtonsService = new BuildButtonsService(new AddSkipButtonKeyboardRow());
            return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Data is saved! Please, type your <i>age</i> at this chat", this.buildButtonsService.getMainMarkup());
        } else return sendMessage;
    }
}
