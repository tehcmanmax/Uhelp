package com.tehcman.informational.portal;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ListOfNewsChannels implements IListOfNewsChannels {

    private final List<String> listOfChannels;

    public ListOfNewsChannels() {
        this.listOfChannels = new LinkedList<>();

        //bulk addition
        //TODO Danila, implement this sht
        Collections.addAll(this.listOfChannels, "@channel1", "@channel2");
    }

    @Override
    public List<String> getListOfChannels() {
        return listOfChannels;
    }

}
