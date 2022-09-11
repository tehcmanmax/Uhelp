package com.tehcman.input_final_destination.SendMessage_factories;

import static com.tehcman.entities.Phase.*;

import com.tehcman.cahce.Cache;
import com.tehcman.entities.User;
import com.tehcman.services.BuildButtonsService;
import com.tehcman.resources.Emoji;
import com.tehcman.services.IBuildSendMessageService;
import com.tehcman.services.keyboards.*;
import com.tehcman.services.keyboards.profile_registration.AddAmountOfPeopleKeyboard;
import com.tehcman.services.keyboards.profile_registration.AddContactsKeyboard;
import com.tehcman.services.keyboards.profile_registration.AddSkipButtonKeyboardRow;
import com.tehcman.services.keyboards.profile_registration.AddYesNo;
import com.tehcman.resources.RegexDictionary;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;


@Component
public class CacheFactoryRefugee implements ISendMessageFactory {
    private final IBuildSendMessageService ibuildSendMessageService;
    private final Cache<User> userCache;
    private BuildButtonsService buildButtonsService;

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
        User userFromCache = userCache.findBy(message.getChatId());
        return registerRestUserData(userFromCache, message);
    }


    private SendMessage registerRestUserData(User user, Message message) {
        switch (user.getPhase()) {

            case SEX:
                this.buildButtonsService = new BuildButtonsService(new AddSkipButtonKeyboardRow());
                if (message.getText().equals("Male")) {
                    user.setSex('M');
                    user.setPhase(NAME);

                    SendMessage sendMessage = ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Type your name or SKIP if you want to set your default Telegram name", buildButtonsService.getMainMarkup());
                    return sendMessage;
                } else if (message.getText().equals("Female")) {
                    user.setSex('F');
                    user.setPhase(NAME);

                    SendMessage sendMessage = ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Type your name or SKIP if you want to set your default Telegram name", buildButtonsService.getMainMarkup());
                    return sendMessage;
                } else if (message.getText().equals("SKIP " + Emoji.BLACK_RIGHTWARDS_ARROW)) {
                    user.setSex(null);
                    user.setPhase(NAME);

                    SendMessage sendMessage = ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Type your name or SKIP if you want to set your default Telegram name", buildButtonsService.getMainMarkup());
                    return sendMessage;
                } else {
                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Please, press on the <u>buttons</u>", buildButtonsService.getMainMarkup());
                }

            case NAME:
                this.buildButtonsService = new BuildButtonsService(addContactsKeyboard);
                if (message.getText().equals("SKIP " + Emoji.BLACK_RIGHTWARDS_ARROW)) {
                    user.setPhase(CONTACTS);
                    user.setName(null);

                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Please, leave contacts, so people can reach you out and help you. If you use a browser, share phone number in additional comments later in this registration form", buildButtonsService.getMainMarkup());

                }
                if (message.getText().matches(RegexDictionary.getRegex.get(NAME))) {
                    SendMessage newMessage = new SendMessage();
                    newMessage.setText("Please, enter your name with at least 2 charac");
                    newMessage.setParseMode("HTML");
                    newMessage.setChatId(user.getId().toString());

                    return newMessage;
                }

            case CONTACTS:
                if (message.getText() != null) {
                    if ((message.getText().equals("SKIP " + Emoji.BLACK_RIGHTWARDS_ARROW))) {
                        user.setPhase(AGE);
                        this.buildButtonsService = new BuildButtonsService(new AddSkipButtonKeyboardRow());
                        return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Please, type your <i>age</i> at this chat", this.buildButtonsService.getMainMarkup());
                    } else if (message.getText().equals("Email")) {
                        user.setPhase(EMAIL);
                        return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Type your email", new ReplyKeyboardRemove(true));

                    } else if (message.getText().equals("Social media")) {
                        user.setPhase(SOCIAL);
                        return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Type a <u>link</u> or a @username to Instagram/Facebook etc.", new ReplyKeyboardRemove(true));
                    } else
                        return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Please, press on the <u>buttons</u>", buildButtonsService.getMainMarkup());
                } else if (message.hasContact()) {
                    this.addContactsKeyboard.removeRow("Phone number");
                    user.setPhase(CONTACTS);
                    user.setPhoneNumber(message.getContact().getPhoneNumber());
                    return isKeyboardSizeOne(user, message, ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Phone number is saved!", this.buildButtonsService.getMainMarkup()));
                }

            case EMAIL:
                user.setEmail(message.getText());
                user.setPhase(CONTACTS);
                this.addContactsKeyboard.removeRow("Email");
                return isKeyboardSizeOne(user, message, ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Email is saved!", this.buildButtonsService.getMainMarkup()));

            case SOCIAL:
                user.setSocial(message.getText());
                user.setPhase(CONTACTS);

                this.addContactsKeyboard.removeRow("Social media");
                return isKeyboardSizeOne(user, message, ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Social media is saved!", buildButtonsService.getMainMarkup()));

            case AGE:
                if (message.getText().equals("SKIP " + Emoji.BLACK_RIGHTWARDS_ARROW)) {
                    user.setAge(null);
                    user.setPhase(CITY);

                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Please, type a <b>city</b> where you are planning to stay", new ReplyKeyboardRemove(true));

                } else if (message.getText().matches(String.valueOf(RegexDictionary.getRegex.get(AGE)))) {
                    user.setAge(message.getText());
                    user.setPhase(CITY);

                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Please, type a <b>city</b> where you are planning to stay", new ReplyKeyboardRemove(true));

                } else {
                    SendMessage newMessage = new SendMessage();
                    newMessage.setText("Please, enter a <u>number</u> (0-99)");
                    newMessage.setParseMode("HTML");
                    newMessage.setChatId(user.getId().toString());

                    return newMessage;
                }

            case CITY:
                if (message.getText().matches(RegexDictionary.getRegex.get(CITY))) {
                    user.setCity(message.getText());
                    user.setPhase(COUNTRY);
                    this.buildButtonsService = new BuildButtonsService(new AddYesNo());

                    this.buildButtonsService.getMainMarkup().setOneTimeKeyboard(Boolean.TRUE);
                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Please, type a <b>country</b> where you are planning to host", new ReplyKeyboardRemove(true));
                } else {
                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "You must type <i>full</i> city name", new ReplyKeyboardRemove(true));
                }

            case COUNTRY:
                if (message.getText().matches(String.valueOf(RegexDictionary.getRegex.get(COUNTRY)))) {
                    user.setCountry(message.getText());
                    user.setPhase(DATE);
                    this.buildButtonsService = new BuildButtonsService(new AddYesNo());

                    this.buildButtonsService.getMainMarkup().setOneTimeKeyboard(Boolean.TRUE);
                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Do you know your arrival date?", this.buildButtonsService.getMainMarkup());
                } else {
                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "You must type <i>full</i> country name", new ReplyKeyboardRemove(true));
                }

            case DATE:
                if (message.getText().equalsIgnoreCase("no")) {
                    user.setDate(null);
                    user.setPhase(AMOUNT_PEOPLE);

                    this.buildButtonsService = new BuildButtonsService(new AddAmountOfPeopleKeyboard());
                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "How many people are looking for housing? ", buildButtonsService.getMainMarkup());
                } else if (message.getText().equalsIgnoreCase("yes")) {
                    user.setPhase(DATE_YES);
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
                    user.setDate(null);

                } else {
                    user.setDate(message.getText());
                }
                user.setPhase(AMOUNT_PEOPLE);
                this.buildButtonsService = new BuildButtonsService(new AddAmountOfPeopleKeyboard());
                return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "How many people are looking for housing? ", buildButtonsService.getMainMarkup());
            case AMOUNT_PEOPLE:
                if ((message.getText().equalsIgnoreCase("I'm alone")) || (message.getText().equals("SKIP " + Emoji.BLACK_RIGHTWARDS_ARROW))) {
                    user.setAmountOfPeople(1);
                    user.setPhase(ADDITIONAL);


                    this.buildButtonsService = new BuildButtonsService(new AddSkipButtonKeyboardRow());
                    this.buildButtonsService.getMainMarkup().setOneTimeKeyboard(Boolean.TRUE);
                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "If you have additional comments (You will arrive with little children, pets etc.) Please type them below."
                            + "\n\n"
                            + " If you don't have any comments, press on the <u>SKIP</u> button", buildButtonsService.getMainMarkup());
                } else if (message.getText().equalsIgnoreCase("A group of people")) {
                    user.setPhase(AMOUNT_PEOPLE_SUB);
                    this.buildButtonsService = new BuildButtonsService(new AddSkipButtonKeyboardRow());
                    this.buildButtonsService.getMainMarkup().setOneTimeKeyboard(Boolean.TRUE);

                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Please, type a number (including you) in this chat",
                            this.buildButtonsService.getMainMarkup());
                } else {
                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Please, press on the <u>buttons</u>", buildButtonsService.getMainMarkup());
                }

            case AMOUNT_PEOPLE_SUB:
                if (message.getText().matches(RegexDictionary.getRegex.get(AMOUNT_PEOPLE_SUB))) {
                    user.setAmountOfPeople(Integer.valueOf(message.getText()));
                    user.setPhase(ADDITIONAL);

                    this.buildButtonsService = new BuildButtonsService(new AddSkipButtonKeyboardRow());
                    this.buildButtonsService.getMainMarkup().setOneTimeKeyboard(Boolean.TRUE);
                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "If you have additional comments (You will accept people  with little children, pets etc.) Please type them below."
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
                if (message.getText().equals("SKIP " + Emoji.BLACK_RIGHTWARDS_ARROW)) {
                    user.setAdditional(null);
                    user.setPhase(NONE);

                    this.buildButtonsService = new BuildButtonsService(new AfterRegistrationKeyboard(message, userCache));
                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Thank you! \n" +
                            "\n" +
                            "Your data has been saved. It is available only to other users if this service\n" +
                            "Now you can view users who\n" +
                            "ready to provide housing", buildButtonsService.getMainMarkup());

                } else {
                    user.setAdditional(message.getText());
                    user.setPhase(NONE);

                    this.buildButtonsService = new BuildButtonsService(new AfterRegistrationKeyboard(message, userCache));
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
            user.setPhase(AGE);
            this.buildButtonsService = new BuildButtonsService(new AddSkipButtonKeyboardRow());
            return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Data is saved! Please, type your <i>age</i> at this chat", this.buildButtonsService.getMainMarkup());
        } else return sendMessage;
    }
}
