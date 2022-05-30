package com.tehcman.informational_portal;

import com.tehcman.services.Emoji;

import java.util.HashMap;
import java.util.Map;

public class ListOfNewsChannels implements IListOfNewsChannels {

    private final Map<String, String> mapOfChannelsAndLinks;

    private String mapDescription;

    //TODO: POSSIBLE REFACTORING. place the description after the link not in the separate place
    public ListOfNewsChannels() {
        this.mapOfChannelsAndLinks = new HashMap<>();
        this.mapDescription = "List of trustable News resources " + Emoji.SMILING_FACE_WITH_SMILING_EYES + "\n\n" +
                "Washington Post channel, sharing live news coverage of Russia’s war in Ukraine\n\n" +
                "The Financial Times is a British daily newspaper printed in broadsheet and published digitally\n\n" +
                "The Kyiv Independent is a top-notch English-language journalism in Ukraine\n\n" +
                "The New York Times is essential news, photos and videos from the Russia-Ukraine war. Get the latest at";

        mapOfChannelsAndLinks.put("The New York Times", "https://t.me/nytimes");
        mapOfChannelsAndLinks.put("The Washington Post", "https://t.me/washingtonpost");
        mapOfChannelsAndLinks.put("The Kyiv Independent", "https://t.me/KyivIndependent_official");
        mapOfChannelsAndLinks.put("Financial Times", "https://t.me/financialtimes");
    }

    @Override
    public Map<String, String> getMapOfChannelsAndLinks() {
        return this.mapOfChannelsAndLinks;
    }

    @Override
    public String getMapDescription() {
        return this.mapDescription;
    }
}
