package com.tehcman.informational.portal;

public class GeneralInformation implements IGeneralInformation {
    private final String generalInformation;

    //TODO Olya, write this method
    GeneralInformation() {
        generalInformation = "*Brief overview of the current situation in Ukraine*";
    }

    @Override
    public String getGeneralInformation() {
        return generalInformation;
    }
}
