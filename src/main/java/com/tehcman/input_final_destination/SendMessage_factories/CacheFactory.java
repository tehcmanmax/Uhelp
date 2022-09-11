package com.tehcman.input_final_destination.SendMessage_factories;

import com.tehcman.cahce.Cache;
import com.tehcman.entities.Phase;
import com.tehcman.entities.Status;
import com.tehcman.entities.User;
import com.tehcman.services.BuildButtonsService;
import com.tehcman.services.BuildSendMessageService;
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
    private final Cache userCache;
    private BuildButtonsService buildButtonsService;
    private final BuildSendMessageService buildSendMessageService;


    private AddContactsKeyboard addContactsKeyboard;

    public CacheFactory(IBuildSendMessageService ibuildSendMessageService, Cache userCache, BuildSendMessageService buildSendMessageService) {
        this.ibuildSendMessageService = ibuildSendMessageService;
        this.userCache = userCache;
        this.buildSendMessageService = buildSendMessageService;
        addContactsKeyboard = new AddContactsKeyboard();
    }

    public void setAddContactsKeyboard(AddContactsKeyboard addContactsKeyboard) {
        this.addContactsKeyboard = addContactsKeyboard;
    }

    @Override
    public SendMessage createSendMessage(Message message) {
        //if no user is found in the registry(cache), start a new user registration
        User userFromCache = userCache.findBy(message.getChatId());
        if (userFromCache == null) {
            User newUser = generateDefaultUserInformationFromMessage(message);
            userCache.add(newUser);
            return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "How can we help you", buildButtonsService.getMainMarkup());

        } else if (userFromCache.getPhase() == Phase.NONE) {
            return new SendMessage(message.getChatId().toString(), "Hey. You are already in the system." + " Instead of duplicating data of yourself, do something useful in your life");
        } else {
            return registerRestUserData(userFromCache, message);
        }
    }


    private User generateDefaultUserInformationFromMessage(Message message) {
        User newUser = new User(message.getChatId(), message.getFrom().getUserName(), message.getFrom().getFirstName(), Phase.STATUS);
        this.buildButtonsService = new BuildButtonsService(new AddStatusKeyboard());
//        buildButtonsService.addingPhoneNumberButton(); //adding phone number button
        return newUser;
    }

    private SendMessage applyStartCommand(Message message, User user) {
        this.buildButtonsService = new BuildButtonsService(new BeforeRegistrationKeyboard());
        userCache.remove(message.getChatId());
        user.setPhase(Phase.STATUS);
        return buildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Yay! You've just restarted this bot!", buildButtonsService.getMainMarkup());

    }


    private SendMessage registerRestUserData(User user, Message message) {
        switch (user.getPhase()) {

            case STATUS:
                if (message.getText().equals("Searching Accommodation")) {
                    user.setStatus(Status.REFUGEE);
                    user.setPhase(Phase.NAME);

                    this.buildButtonsService = new BuildButtonsService(new AddSkipButtonKeyboardRow());
                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Type your name or SKIP if you want to set your default Telegram name", buildButtonsService.getMainMarkup());
                } else if (message.getText().equals("Providing Accommodation")) {
                    user.setStatus(Status.HOST);
                    user.setPhase(Phase.NAME);

                    this.buildButtonsService = new BuildButtonsService(new AddSkipButtonKeyboardRow());
                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Type your name or SKIP if you want to set your default Telegram name", buildButtonsService.getMainMarkup());

                } else if (message.getText().equals("/start")) {
                    this.buildButtonsService = new BuildButtonsService(new BeforeRegistrationKeyboard());
                    userCache.remove(message.getChatId());
                    user.setPhase(Phase.STATUS);
                    return buildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Yay! You've just restarted this bot!", buildButtonsService.getMainMarkup());


                } else {
                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "You must press on a button!", buildButtonsService.getMainMarkup());
                }
            case NAME:
                if (message.getText().equals("/start")) {
                    this.buildButtonsService = new BuildButtonsService(new BeforeRegistrationKeyboard());
                    userCache.remove(message.getChatId());
                    user.setPhase(Phase.STATUS);
                    return buildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Yay! You've just restarted this bot!", buildButtonsService.getMainMarkup());


                } else if (message.getText().equals("SKIP " + Emoji.BLACK_RIGHTWARDS_ARROW)) {
                    user.setPhase(Phase.SEX);
                    user.setName(message.getText());

                    this.buildButtonsService = new BuildButtonsService(new AddSexKeyboard());
                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Are you man or woman?", buildButtonsService.getMainMarkup());
                } else {
                    user.setPhase(Phase.SEX);

                    this.buildButtonsService = new BuildButtonsService(new AddSexKeyboard());
                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Are you man or woman?", buildButtonsService.getMainMarkup());
                }
            case SEX:
                if (message.getText().equals("/start")) {
                    this.buildButtonsService = new BuildButtonsService(new BeforeRegistrationKeyboard());
                    userCache.remove(message.getChatId());
                    user.setPhase(Phase.STATUS);
                    return buildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Yay! You've just restarted this bot!", buildButtonsService.getMainMarkup());

                } else if (message.getText().equals("Male")) {
                    user.setSex('M');
                    user.setPhase(Phase.CONTACTS);

                    this.buildButtonsService = new BuildButtonsService(addContactsKeyboard);
                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Please, leave contacts, so people can reach you out and help you", buildButtonsService.getMainMarkup());
                } else if (message.getText().equals("Female")) {
                    user.setSex('F');
                    user.setPhase(Phase.CONTACTS);

                    this.buildButtonsService = new BuildButtonsService(addContactsKeyboard);
                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Please, leave contacts, so people can reach you out and help you", buildButtonsService.getMainMarkup());
                } else if (message.getText().equals("SKIP " + Emoji.BLACK_RIGHTWARDS_ARROW)) {
                    user.setSex(null);
                    user.setPhase(Phase.CONTACTS);

                    this.buildButtonsService = new BuildButtonsService(addContactsKeyboard);
                    this.buildButtonsService.getMainMarkup().setOneTimeKeyboard(true);
                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Please, leave contacts, so people can reach you out and help you", buildButtonsService.getMainMarkup());
                } else {
                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Please, press on the <u>buttons</u>", buildButtonsService.getMainMarkup());
                }

            case CONTACTS:
                if (message.getText().equals("/start")) {
                    this.buildButtonsService = new BuildButtonsService(new BeforeRegistrationKeyboard());
                    userCache.remove(message.getChatId());
                    user.setPhase(Phase.STATUS);
                    return buildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Yay! You've just restarted this bot!", buildButtonsService.getMainMarkup());
                } else if (message.getText() != null) {
                    if ((message.getText().equals("SKIP " + Emoji.BLACK_RIGHTWARDS_ARROW))) {
                        user.setPhase(Phase.AGE);
                        this.buildButtonsService = new BuildButtonsService(new AddSkipButtonKeyboardRow());
                        return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Please, type your <i>age</i> at this chat", this.buildButtonsService.getMainMarkup());
                    } else if (message.getText().equals("Email")) {
                        user.setPhase(Phase.EMAIL);
                        return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Type your email", new ReplyKeyboardRemove(true));

                    } else if (message.getText().equals("Social media")) {
                        user.setPhase(Phase.SOCIAL);
                        return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Type a <u>link</u> or a @username to Instagram/Facebook etc.", new ReplyKeyboardRemove(true));
                    } else
                        return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Please, press on the <u>buttons</u>", buildButtonsService.getMainMarkup());
                } else if (message.hasContact()) {
                    this.addContactsKeyboard.removeRow("Phone number");
                    user.setPhase(Phase.CONTACTS);
                    user.setPhoneNumber(message.getContact().getPhoneNumber());
                    return isKeyboardSizeOne(user, message, ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Phone number is saved!", this.buildButtonsService.getMainMarkup()));
                }

            case EMAIL:
                if (message.getText().equals("/start")) {
                    this.buildButtonsService = new BuildButtonsService(new BeforeRegistrationKeyboard());
                    userCache.remove(message.getChatId());
                    user.setPhase(Phase.STATUS);
                    return buildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Yay! You've just restarted this bot!", buildButtonsService.getMainMarkup());
                }

                user.setEmail(message.getText());
                user.setPhase(Phase.CONTACTS);
                this.addContactsKeyboard.removeRow("Email");
                return isKeyboardSizeOne(user, message, ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Email is saved!", this.buildButtonsService.getMainMarkup()));

            case SOCIAL:
                if (message.getText().equals("/start")) {
                    this.buildButtonsService = new BuildButtonsService(new BeforeRegistrationKeyboard());
                    userCache.remove(message.getChatId());
                    user.setPhase(Phase.STATUS);
                    return buildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Yay! You've just restarted this bot!", buildButtonsService.getMainMarkup());
                }

                user.setSocial(message.getText());
                user.setPhase(Phase.CONTACTS);

                this.addContactsKeyboard.removeRow("Social media");
                return isKeyboardSizeOne(user, message, ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Social media is saved!", buildButtonsService.getMainMarkup()));

            case AGE:
                if (message.getText().equals("/start")) {
                    this.buildButtonsService = new BuildButtonsService(new BeforeRegistrationKeyboard());
                    userCache.remove(message.getChatId());
                    user.setPhase(Phase.STATUS);
                    return buildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Yay! You've just restarted this bot!", buildButtonsService.getMainMarkup());
                } else if (message.getText().equals("SKIP " + Emoji.BLACK_RIGHTWARDS_ARROW)) {
                    user.setAge(null);
                    user.setPhase(Phase.CITY);

                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Please, type a <b>city</b> where you are planning to stay", new ReplyKeyboardRemove(true));

                } else if (message.getText().matches("\\d{1,2}")) {
                    user.setAge(message.getText());
                    user.setPhase(Phase.CITY);

                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Please, type a <b>city</b> where you are planning to stay", new ReplyKeyboardRemove(true));

                } else {
                    SendMessage newMessage = new SendMessage();
                    newMessage.setText("Please, enter a <u>number</u> (0-99)");
                    newMessage.setParseMode("HTML");
                    newMessage.setChatId(user.getId().toString());

                    return newMessage;
                }
            case CITY:
                if (message.getText().equals("/start")) {
                    this.buildButtonsService = new BuildButtonsService(new BeforeRegistrationKeyboard());
                    userCache.remove(message.getChatId());
                    user.setPhase(Phase.STATUS);
                    return buildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Yay! You've just restarted this bot!", buildButtonsService.getMainMarkup());
                } else if (message.getText().matches("[^\\d\\W]{3,}")) {
                    user.setCity(message.getText());
                } else {
                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "You must type <i>full</i> city name", new ReplyKeyboardRemove(true));
                }
                user.setPhase(Phase.COUNTRY);
                this.buildButtonsService = new BuildButtonsService(new AddYesNo());

                this.buildButtonsService.getMainMarkup().setOneTimeKeyboard(Boolean.TRUE);
                return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Please, type a <b>country</b> where you are planning to stay", new ReplyKeyboardRemove(true));

            case COUNTRY:
                if (message.getText().equals("/start")) {
                    this.buildButtonsService = new BuildButtonsService(new BeforeRegistrationKeyboard());
                    userCache.remove(message.getChatId());
                    user.setPhase(Phase.STATUS);
                    return buildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Yay! You've just restarted this bot!", buildButtonsService.getMainMarkup());
                } else if (message.getText().matches("[^\\d\\W]{2,}")) {
                    user.setCountry(message.getText());
                } else {
                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "You must type <i>full</i> country name", new ReplyKeyboardRemove(true));
                }
                user.setPhase(Phase.DATE);
                this.buildButtonsService = new BuildButtonsService(new AddYesNo());

                this.buildButtonsService.getMainMarkup().setOneTimeKeyboard(Boolean.TRUE);
                return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Do you know your arrival date?", this.buildButtonsService.getMainMarkup());

            case DATE:
                if (message.getText().equals("/start")) {
                    this.buildButtonsService = new BuildButtonsService(new BeforeRegistrationKeyboard());
                    userCache.remove(message.getChatId());
                    user.setPhase(Phase.STATUS);
                    return buildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Yay! You've just restarted this bot!", buildButtonsService.getMainMarkup());
                } else if (message.getText().equalsIgnoreCase("no")) {
                    user.setDate(null);
                    user.setPhase(Phase.AMOUNT_PEOPLE);

                    this.buildButtonsService = new BuildButtonsService(new AddAmountOfPeopleKeyboard());
                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "How many people are looking for housing? ", buildButtonsService.getMainMarkup());
                } else if (message.getText().equalsIgnoreCase("yes")) {
                    user.setPhase(Phase.DATE_YES);
                    this.buildButtonsService = new BuildButtonsService(new AddSkipButtonKeyboardRow());
                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Please, type a date at this chat\n" +
                            "(YYYY-MM-DD)\n" +
                            "or time frame\n" +
                            "(October/Summer etc.)", this.buildButtonsService.getMainMarkup());

                } else {
                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Please, press on the <u>buttons</u>", buildButtonsService.getMainMarkup());
                }
            case DATE_YES:
                if (message.getText().equals("/start")) {
                    this.buildButtonsService = new BuildButtonsService(new BeforeRegistrationKeyboard());
                    userCache.remove(message.getChatId());
                    user.setPhase(Phase.STATUS);
                    return buildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Yay! You've just restarted this bot!", buildButtonsService.getMainMarkup());
                } else if (message.getText().equals("SKIP " + Emoji.BLACK_RIGHTWARDS_ARROW)) {
                    user.setDate(null);

                } else {
                    user.setDate(message.getText());
                }
                user.setPhase(Phase.AMOUNT_PEOPLE);
                this.buildButtonsService = new BuildButtonsService(new AddAmountOfPeopleKeyboard());
                return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "How many people are looking for housing? ", buildButtonsService.getMainMarkup());
            case AMOUNT_PEOPLE:
                if (message.getText().equals("/start")) {
                    this.buildButtonsService = new BuildButtonsService(new BeforeRegistrationKeyboard());
                    userCache.remove(message.getChatId());
                    user.setPhase(Phase.STATUS);
                    return buildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Yay! You've just restarted this bot!", buildButtonsService.getMainMarkup());
                } else if ((message.getText().equalsIgnoreCase("I'm alone")) || (message.getText().equals("SKIP " + Emoji.BLACK_RIGHTWARDS_ARROW))) {
                    user.setAmountOfPeople(1);
                    user.setPhase(Phase.ADDITIONAL);


                    this.buildButtonsService = new BuildButtonsService(new AddSkipButtonKeyboardRow());
                    this.buildButtonsService.getMainMarkup().setOneTimeKeyboard(Boolean.TRUE);
                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "If you have additional comments (You will arrive with little children, pets etc.) Please type them below."
                            + "\n\n"
                            + " If you don't have any comments, press on the <u>SKIP</u> button", buildButtonsService.getMainMarkup());
                } else if (message.getText().equalsIgnoreCase("A group of people")) {
                    user.setPhase(Phase.AMOUNT_PEOPLE_SUB);
                    this.buildButtonsService = new BuildButtonsService(new AddSkipButtonKeyboardRow());
                    this.buildButtonsService.getMainMarkup().setOneTimeKeyboard(Boolean.TRUE);

                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Please, type a number (including you) at this chat",
                            this.buildButtonsService.getMainMarkup());
                } else {
                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Please, press on the <u>buttons</u>", buildButtonsService.getMainMarkup());
                }

            case AMOUNT_PEOPLE_SUB:
                if (message.getText().equals("/start")) {
                    this.buildButtonsService = new BuildButtonsService(new BeforeRegistrationKeyboard());
                    userCache.remove(message.getChatId());
                    user.setPhase(Phase.STATUS);
                    return buildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Yay! You've just restarted this bot!", buildButtonsService.getMainMarkup());
                } else if (message.getText().matches("\\d{1,2}")) {
                    user.setAmountOfPeople(Integer.valueOf(message.getText()));
                    user.setPhase(Phase.ADDITIONAL);

                    this.buildButtonsService = new BuildButtonsService(new AddSkipButtonKeyboardRow());
                    this.buildButtonsService.getMainMarkup().setOneTimeKeyboard(Boolean.TRUE);
                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "If you have additional comments (You will arrive with little children, pets etc.) Please type them below."
                            + "\n\n"
                            + " If you don't have any comments, press on the <u>SKIP</u> button", buildButtonsService.getMainMarkup());
                } else {
                    SendMessage newMessage = new SendMessage();
                    newMessage.setText("Please, enter a <u>number</u> (0-99)");
                    newMessage.setParseMode("HTML");
                    newMessage.setChatId(user.getId().toString());

                    return newMessage;
                }
            case ADDITIONAL:
                if (message.getText().equals("/start")) {
                    this.buildButtonsService = new BuildButtonsService(new BeforeRegistrationKeyboard());
                    userCache.remove(message.getChatId());
                    user.setPhase(Phase.STATUS);
                    return buildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Yay! You've just restarted this bot!", buildButtonsService.getMainMarkup());
                } else if (message.getText().equals("SKIP " + Emoji.BLACK_RIGHTWARDS_ARROW)) {
                    user.setAdditional(null);
                    user.setPhase(Phase.NONE);

                    this.buildButtonsService = new BuildButtonsService(new AfterRegistrationKeyboard());
                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Thank you! \n" +
                            "\n" +
                            "Your data has been saved. It is available only to other users if this service\n" +
                            "Now you can view users who\n" +
                            "ready to provide housing", buildButtonsService.getMainMarkup());

                } else {
                    user.setAdditional(message.getText());
                    user.setPhase(Phase.NONE);

                    this.buildButtonsService = new BuildButtonsService(new AfterRegistrationKeyboard());
                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Thank you! \n" +
                            "\n" +
                            "Your data has been saved. It is available only to other users if this service\n\n" +
                            "Now you can view users who is ready to provide housing to you", buildButtonsService.getMainMarkup());

                }
        }
        System.out.println(user);
        return null;
    }

    private SendMessage isKeyboardSizeOne(User user, Message message, SendMessage sendMessage) {
        if ((this.addContactsKeyboard.getKeyboard().size() <= 1)) {
            user.setPhase(Phase.AGE);
            this.buildButtonsService = new BuildButtonsService(new AddSkipButtonKeyboardRow());
            return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Data is saved! Please, type your <i>age</i> at this chat", this.buildButtonsService.getMainMarkup());
        } else return sendMessage;
    }
}
