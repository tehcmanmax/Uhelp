package com.tehcman.input_final_destination.SendMessage_factories;

import static com.tehcman.entities.Phase.*;

import com.tehcman.cahce.Cache;
import com.tehcman.printers.HostProfile;
import com.tehcman.entities.User;
import com.tehcman.repository.UserRepository;
import com.tehcman.services.BuildButtonsService;
import com.tehcman.resources.Emoji;
import com.tehcman.services.IBuildSendMessageService;
import com.tehcman.services.NewProfileClientNotifier;
import com.tehcman.services.keyboards.*;
import com.tehcman.services.keyboards.profile_registration.AddAmountOfPeopleKeyboard;
import com.tehcman.services.keyboards.profile_registration.AddContactsKeyboard;
import com.tehcman.services.keyboards.profile_registration.AddSkipButtonKeyboardRow;
import com.tehcman.services.keyboards.profile_registration.AddYesNo;
import com.tehcman.resources.RegexDictionary;
import com.tehcman.services.keyboards.profile_search.InlineNoProfiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;


@Component
public class CacheFactoryHost implements ISendMessageFactory {
    private InlineNoProfiles inlineNoProfiles;
    private NewProfileClientNotifier newProfileClientNotifier;
    private final IBuildSendMessageService ibuildSendMessageService;
    private final Cache<User> userCache;
    private BuildButtonsService buildButtonsService;

    private AddContactsKeyboard addContactsKeyboard;
    private UserRepository userRepository;

    @Autowired
    @Lazy
    public void setHostProfile(HostProfile hostProfile) {
        this.hostProfile = hostProfile;
    }

    private HostProfile hostProfile;

    @Autowired
    @Lazy
    public CacheFactoryHost(InlineNoProfiles inlineNoProfiles, NewProfileClientNotifier newProfileClientNotifier, IBuildSendMessageService ibuildSendMessageService, Cache<User> userCache, UserRepository userRepository) {
        this.ibuildSendMessageService = ibuildSendMessageService;
        this.userCache = userCache;
        addContactsKeyboard = new AddContactsKeyboard();
        this.inlineNoProfiles = inlineNoProfiles;
        this.newProfileClientNotifier = newProfileClientNotifier;
        this.userRepository = userRepository;
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
        if ((user.getPhase().equals(NAME)) && (message.getText().equals("Continue the registration"))) {
            SendMessage sendMessage = ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Type your name or SKIP if you want to set your default Telegram name", buildButtonsService.getMainMarkup());
            return sendMessage;
        }

        switch (user.getPhase()) {

            case SEX:
                this.buildButtonsService = new BuildButtonsService(new AddSkipButtonKeyboardRow());
                if (message.getText().equals("Male")) {
                    user.setSex('M');
                    user.setPhase(NAME);
                    userRepository.save(user);

                    SendMessage sendMessage = ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Type your name or SKIP if you want to set your default Telegram name", buildButtonsService.getMainMarkup());
                    return sendMessage;
                } else if (message.getText().equals("Female")) {
                    user.setSex('F');
                    user.setPhase(NAME);
                    userRepository.save(user);

                    SendMessage sendMessage = ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Type your name or SKIP if you want to set your default Telegram name", buildButtonsService.getMainMarkup());
                    return sendMessage;
                } else if (message.getText().equals("SKIP " + Emoji.BLACK_RIGHTWARDS_ARROW)) {
                    user.setSex(null);
                    user.setPhase(NAME);
                    userRepository.save(user);

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
                    userRepository.save(user);

                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Please, leave contacts, so people can reach you out and help you. If you use a browser, share phone number in additional comments later in this registration form", buildButtonsService.getMainMarkup());

                } else if (message.getText().matches(RegexDictionary.getRegex.get(NAME))) {
                    SendMessage newMessage = new SendMessage();
                    newMessage.setText("Please, enter your name with at least 2 characters");
                    newMessage.setParseMode("HTML");
                    newMessage.setChatId(user.getId().toString());

                    return newMessage;
                } else {
                    user.setName(message.getText());
                    user.setPhase(CONTACTS);
                    userRepository.save(user);
                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Please, leave contacts, so people can reach you out and help you. If you use a browser, share phone number in additional comments later in this registration form", buildButtonsService.getMainMarkup());
                }

            case CONTACTS:
                if (message.getText() != null) {
                    if ((message.getText().equals("SKIP " + Emoji.BLACK_RIGHTWARDS_ARROW))) {
                        user.setPhase(AGE);
                        userRepository.save(user);
                        this.buildButtonsService = new BuildButtonsService(new AddSkipButtonKeyboardRow());
                        return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Please, type your <i>age</i> at this chat", this.buildButtonsService.getMainMarkup());
                    } else if (message.getText().equals("Email")) {
                        user.setPhase(EMAIL);
                        userRepository.save(user);
                        return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Type your email", new ReplyKeyboardRemove(true));

                    } else if (message.getText().equals("Social media")) {
                        user.setPhase(SOCIAL);
                        userRepository.save(user);
                        return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Type a <u>link</u> or a @username to Instagram/Facebook etc.", new ReplyKeyboardRemove(true));
                    } else
                        return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Please, press on the <u>buttons</u>", buildButtonsService.getMainMarkup());
                } else if (message.hasContact()) {
                    this.addContactsKeyboard.removeRow("Phone number");
                    user.setPhase(CONTACTS);
                    user.setPhoneNumber(message.getContact().getPhoneNumber());
                    userRepository.save(user);
                    return isKeyboardSizeOne(user, message, ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Phone number is saved!", this.buildButtonsService.getMainMarkup()));
                }

            case EMAIL:
                user.setEmail(message.getText());
                user.setPhase(CONTACTS);
                userRepository.save(user);
                this.addContactsKeyboard.removeRow("Email");
                return isKeyboardSizeOne(user, message, ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Email is saved!", this.buildButtonsService.getMainMarkup()));

            case SOCIAL:
                user.setSocial(message.getText());
                user.setPhase(CONTACTS);
                userRepository.save(user);

                this.addContactsKeyboard.removeRow("Social media");
                return isKeyboardSizeOne(user, message, ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Social media is saved!", buildButtonsService.getMainMarkup()));

            case AGE:
                if (message.getText().equals("SKIP " + Emoji.BLACK_RIGHTWARDS_ARROW)) {
                    user.setAge(null);
                    user.setPhase(CITY);
                    userRepository.save(user);

                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Please, type a <b>city</b> where you are planning to host", new ReplyKeyboardRemove(true));

                } else if (message.getText().matches(RegexDictionary.getRegex.get(AGE))) {
                    user.setAge(message.getText());
                    user.setPhase(CITY);
                    userRepository.save(user);

                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Please, type a <b>city</b> where you are planning to host", new ReplyKeyboardRemove(true));

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
                    userRepository.save(user);
                    this.buildButtonsService = new BuildButtonsService(new AddYesNo());

                    this.buildButtonsService.getMainMarkup().setOneTimeKeyboard(Boolean.TRUE);
                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Please, type a <b>country</b> where you are planning to host", new ReplyKeyboardRemove(true));
                } else {
                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "You must type <i>full</i> city name", new ReplyKeyboardRemove(true));
                }

            case COUNTRY:
                if (message.getText().matches(RegexDictionary.getRegex.get(COUNTRY))) {
                    user.setCountry(message.getText());
                    user.setPhase(DATE);
                    userRepository.save(user);
                    this.buildButtonsService = new BuildButtonsService(new AddYesNo());

                    this.buildButtonsService.getMainMarkup().setOneTimeKeyboard(Boolean.TRUE);
                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Do you have preferences in the dates?", this.buildButtonsService.getMainMarkup());
                } else {
                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "You must type <i>full</i> country name", new ReplyKeyboardRemove(true));
                }

            case DATE:
                if (message.getText().equalsIgnoreCase("no")) {
                    user.setDate(null);
                    user.setPhase(AMOUNT_PEOPLE_SUB);
                    userRepository.save(user);

                    this.buildButtonsService = new BuildButtonsService(new AddAmountOfPeopleKeyboard());
                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Type in the chat how many people you can host? ", new ReplyKeyboardRemove(true));
                } else if (message.getText().equalsIgnoreCase("yes")) {
                    user.setPhase(DATE_YES);
                    userRepository.save(user);
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
                    userRepository.save(user);

                } else {
                    user.setDate(message.getText());
                    userRepository.save(user);
                }
                user.setPhase(AMOUNT_PEOPLE_SUB);
                userRepository.save(user);

                return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Type in the chat how many people you can host? ", new ReplyKeyboardRemove(true));

            case AMOUNT_PEOPLE_SUB:
                if (message.getText().matches(RegexDictionary.getRegex.get(AMOUNT_PEOPLE_SUB))) {
                    user.setAmountOfPeople(Integer.valueOf(message.getText()));
                    user.setPhase(ADDITIONAL);
                    userRepository.save(user);

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
                if (message.getText().equals("SKIP " + Emoji.BLACK_RIGHTWARDS_ARROW)) {
                    user.setAdditional(null);
                    user.setPhase(NONE);
                    userRepository.save(user);
                    hostProfile.addSingleUserFromCache(user);


                    this.buildButtonsService = new BuildButtonsService(new AfterRegistrationKeyboard(message, userCache));
                    updateListeningClient(user);

                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Thank you! \n" +
                            "\n" +
                            "Your data has been saved. It is available only to other users if this service\n" +
                            "Now you can view users who\n" +
                            "ready to provide housing", buildButtonsService.getMainMarkup());

                } else {
                    user.setAdditional(message.getText());
                    user.setPhase(NONE);
                    userRepository.save(user);
                    hostProfile.addSingleUserFromCache(user);


                    this.buildButtonsService = new BuildButtonsService(new AfterRegistrationKeyboard(message, userCache));
                    updateListeningClient(user);
                    return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Thank you! \n" +
                            "\n" +
                            "Your data has been saved. It is available only to other users if this service\n\n" +
                            "Now you can view users who is ready to provide housing to you", buildButtonsService.getMainMarkup());

                }
        }
        userRepository.save(user);
        System.out.println(user);
        return null;
    }

    private SendMessage isKeyboardSizeOne(User user, Message message, SendMessage sendMessage) {
        if ((this.addContactsKeyboard.getKeyboard().size() <= 1)) {
            user.setPhase(AGE);
            userRepository.save(user);
            this.buildButtonsService = new BuildButtonsService(new AddSkipButtonKeyboardRow());
            return ibuildSendMessageService.createHTMLMessage(message.getChatId().toString(), "Data is saved! Please, type your <i>age</i> at this chat", this.buildButtonsService.getMainMarkup());
        } else return sendMessage;
    }

    private void updateListeningClient(User user) {
        Long listenerID = inlineNoProfiles.getClientListener().getUserThatListensId();
        if ((listenerID != 0L) && (!userCache.findBy(listenerID).getStatus().equals(userCache.findBy(user.getId()).getStatus()))) {
            newProfileClientNotifier.notifyListeningClient(inlineNoProfiles.getClientListener().getUserThatListensId());
            newProfileClientNotifier.setNewRegisteredUser(user);
            inlineNoProfiles.getClientListener().setUserThatListensId(0L);
        }
    }
}
