package com.tehcman.informational_portal;

public class GeneralInformation implements IGeneralInformation{
    private final String generalInformation;

    public GeneralInformation() {
        generalInformation = "Russian invasion of Ukraine 2022 General information: \n\n" +
                "On 24 February 2022, Russia invaded Ukraine, marking a steep escalation of the Russo-Ukrainian War, " +
                "which had begun in 2014. The invasion has caused Europe's fastest-growing refugee crisis since World War II," +
                " with more than 6.7 million Ukrainians fleeing the country and a third of the population displaced.\n\n";
    }

    public String getGeneralInformation() {
        return generalInformation;
    }
}
