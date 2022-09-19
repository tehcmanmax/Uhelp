package com.tehcman.resources;
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
* And what it rejects:

A----B
------
*******
&&
()
//
\\
https://stackoverflow.com/questions/11757013/regular-expressions-for-city-name
*
*  ***
*  EMAIL
It allows numeric values from 0 to 9.
We allow both uppercase and lowercase letters from a to z.
Hyphen “-” and dot “.” aren't allowed at the start and end of the domain part.
No consecutive dots.

* TODO: POSSIBLE IMPROVEMENT: use class reflection on classes that utilize this class
*/

import com.tehcman.entities.Phase;

import java.util.HashMap;
import java.util.Map;

import static com.tehcman.entities.Phase.*;

public final class RegexDictionary {
    public final static Map<Phase, String> getRegex = new HashMap<>();

    static {
        getRegex.put(NAME, "\\W{2,}");
        getRegex.put(AGE, "\\d{1,2}");
        getRegex.put(AMOUNT_PEOPLE_SUB, "\\d{1,2}");
        getRegex.put(CITY, "^([a-zA-Z\\u0080-\\u024F]+(?:. |-| |'))*[a-zA-Z\\u0080-\\u024F]*$");
        getRegex.put(COUNTRY, "[a-zA-Z]{2,}");
        getRegex.put(EMAIL, "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$");
    }
}
