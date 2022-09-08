package com.tehcman.services.regex;
/*  REGEX EXPLAINED
* ***
* CITY
The list of cities it accepts:

Toronto
St. Catharines
San Fransisco
Val-d'Or
Presqu'ile
Niagara on the Lake
Niagara-on-the-Lake
München
toronto
toRonTo
villes du Québec
Provence-Alpes-Côte d'Azur
Île-de-France
Kópavogur
Garðabær
Sauðárkrókur
Þorlákshöfn
*
* TODO: POSSIBLE IMPROVEMENT: use class reflection on classes that utilize this class
*/

import com.tehcman.entities.Phase;

import java.util.HashMap;
import java.util.Map;

import static com.tehcman.entities.Phase.*;

public final class RegexDictionary {
    public final static Map<Phase, String> getRegex = new HashMap<>();

    static {
        getRegex.put(AGE, "\\d{1,2}");
        getRegex.put(AMOUNT_PEOPLE_SUB, "\\d{1,2}");
        getRegex.put(COUNTRY, "[a-zA-Z]{2,}\n");
        getRegex.put(CITY, "^([a-zA-Z\\u0080-\\u024F]+(?:. |-| |'))*[a-zA-Z\\u0080-\\u024F]*$\n");
    }
}
