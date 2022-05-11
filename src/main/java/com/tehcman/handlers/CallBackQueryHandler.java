package com.tehcman.handlers;

import com.tehcman.sendmessage.MessageSender;
import com.tehcman.services.BuildInlineButtonsService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.*;


@Component
public class CallBackQueryHandler implements Handler<CallbackQuery> {
    private final MessageSender messageSender;
    private final BuildInlineButtonsService buildInlineButtonsService; //testing the inline buttons
    private List<String> jokes;
    private int prevNumber = -1;

    public CallBackQueryHandler(@Lazy MessageSender messageSender, BuildInlineButtonsService buildInlineButtonsService) {
        this.messageSender = messageSender;
        this.buildInlineButtonsService = buildInlineButtonsService;
    }

    @Override
    public void handle(CallbackQuery inlineButtonPressed) {
        if (inlineButtonPressed.getData().equals("next_action")) {

            var editMessageText = EditMessageText.builder()
                    .text(randomJoke())
                    .chatId(inlineButtonPressed.getMessage().getChatId().toString())
                    .messageId(inlineButtonPressed.getMessage().getMessageId())
                    .replyMarkup(buildInlineButtonsService.build())
                    .build();

            messageSender.editMessageSend(editMessageText);
        }
    }

    private String randomJoke() {
        jokes = new ArrayList<>(Arrays.asList(
                " - What’s the best thing about Switzerland?\n\n" +
                        " - I don’t know, but the flag is a big plus.",
                "Hear about the new restaurant called Karma?\n" +
                        "\n" +
                        "There’s no menu: You get what you deserve.",
                "A woman in labor suddenly shouted, “Shouldn’t! Wouldn’t! Couldn’t! Didn’t! Can’t!”\n" +
                        "\n" +
                        "“Don’t worry,” said the doc. “Those are just contractions.”",
                "A bear walks into a bar and says, “Give me a whiskey and … cola.”\n" +
                        "\n" +
                        "“Why the big pause?” asks the bartender. The bear shrugged. “I’m not sure; I was born with them.”",
                "Did you hear about the actor who fell through the floorboards?\n" +
                        "\n" +
                        "He was just going through a stage.",
                "Did you hear about the claustrophobic astronaut?\n" +
                        "\n" +
                        "He just needed a little space.",
                "Why don’t scientists trust atoms?\n" +
                        "\n" +
                        "Because they make up everything.",
                "How do you drown a hipster?\n" +
                        "\n" +
                        "Throw him in the mainstream",
                "A man tells his doctor, “Doc, help me. I’m addicted to Twitter!”\n" +
                        "\n" +
                        "The doctor replies, “Sorry, I don’t follow you …”",
                "What did the left eye say to the right eye?\n" +
                        "\n" +
                        "Between you and me, something smells.",
                "Two Russian invaders are out in the woods in Ukraine when one of them collapses.\n" +
                        "He doesn't seem to be breathing and his eyes are glazed.\n" +
                        "The other guy whips out his phone and calls the Russian emergency services.\n" +
                        "He gasps, \"My comrade is dead! What can I do?\".\n" +
                        "The operator says \"Calm down. I can help. First,\n" +
                        "let's make sure he's dead.\" There is a silence, then a shot is heard.\n" +
                        "Back on the phone, the guy says \"OK, now what?\"\n"
        ));
        int randNumb = (int) (Math.random() * jokes.size());
        while(randNumb == prevNumber) {
            randNumb = (int) (Math.random() * jokes.size());
        }
        prevNumber = randNumb;

        return jokes.get(randNumb);
    }

}
