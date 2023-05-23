package com.befrank.casedeveloperjava.util;

import static com.befrank.casedeveloperjava.util.Validations.valideerTekenreeks;

public class TekstFuncties {

    /**
     * <strong>presentatie</strong>
     * Controleert de specificatie van een tekst of deze null, leeg of uitsluitend uit spaties bestaat. De metode wordt
     * alleen door de methode 'toString()' gebruikt.<be></be>
     * @param tekst De tekenreeks die wordt gecontroleerd.
     * @return De gecontroleerde tekenreeks of indien deze niet aan de voorwaarden voldoet, een lege tekenreeks
     */
    public static String presentatie(String tekst) {
        return valideerTekenreeks(tekst) ? tekst : "";
    }
}
