package com.tehcman.informational.portal;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ListOfTgNewsChannels implements IListOfTgNewsChannels{

    private final List<String> listOfTgChannels;

    public ListOfTgNewsChannels() {
        this.listOfTgChannels = new LinkedList<>();

        //bulk addition
        //TODO Danila, implement this sht
        Collections.addAll(this.listOfTgChannels, "@channel1", "@channel2");
    }

    @Override
    public List<String> getListOfTgChannels() {
        return listOfTgChannels;
    }
}
