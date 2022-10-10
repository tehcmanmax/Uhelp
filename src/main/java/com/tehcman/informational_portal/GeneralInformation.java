package com.tehcman.informational_portal;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class GeneralInformation implements IGeneralInformation{
    private String generalInformation;
    private static int counter;

    public GeneralInformation() {
        Document page;
        try {
            page = Jsoup.connect("https://liveuamap.com/").get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Elements pageElements = page.select("div[data-link]");
        ArrayList<String> hyperLinks = new ArrayList<>();

        for (Element e:pageElements) {
            Document newsPage;
            String attr = e.selectFirst("div.top-right").selectFirst("a").attr("href");
            hyperLinks.add(e.child(1).text() + " " + attr);
        }

        generalInformation = hyperLinks.get(counter % hyperLinks.size());
        counter++;
    }

    public String getGeneralInformation() {
        return generalInformation;
    }
}