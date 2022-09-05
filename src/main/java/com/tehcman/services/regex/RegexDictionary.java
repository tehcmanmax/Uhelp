package com.tehcman.services.regex;

import com.tehcman.entities.Phase;

import java.util.HashMap;
import java.util.Map;

import static com.tehcman.entities.Phase.*;

public final class RegexDictionary {
    public final static Map<Phase, String> getRegex = new HashMap<>();

    static {
        getRegex.put(AGE, "\\d{1,2}");
        getRegex.put(COUNTRY, "[^\\d\\W]{2,}");
        getRegex.put(CITY, "[^\\d\\W]{3,}");
    }
}
