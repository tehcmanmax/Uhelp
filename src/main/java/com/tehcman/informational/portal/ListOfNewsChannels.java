package com.tehcman.informational.portal;

import java.util.ArrayList;

public class ListOfNewsChannels implements IListOfNewsChannels {

    private final ArrayList<String> listOfChannels;

    private String listDescription;

    public ListOfNewsChannels() {
        this.listOfChannels = new ArrayList<>();
        this.listDescription = "List of trustable News resources:";

        listOfChannels.add("https://t.me/nytimes");
        listOfChannels.add("https://t.me/washingtonpost");
        listOfChannels.add("https://t.me/financialtimes");
        listOfChannels.add("https://t.me/KyivIndependent_official");
    }

//    public String getChannels() {
//        return String.join("\n\n", listOfChannels);
//    }

    @Override
    public ArrayList<String> getListOfChannels() {
        return listOfChannels;
    }

    @Override
    public String getListDescription() {
        return this.listDescription;
    }
}
