package com.bridger.development.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.Period;

public class DatumTijdFuncties {

    private static final Logger logger = LoggerFactory.getLogger(DatumTijdFuncties.class);

    /**
     * <strong>getLeeftijd()</strong><br>
     * Bepaalt de actuele leeftijd van de deelnemer gebaseerd op de geboortedatum.<br>
     * @param geboortedatum De geboortedatum waarop de berekening van de actuele leeftijd plaatsvindt.
     * @return  De actuele leeftijd van de deelnemer. Wanneer deze niet kan worden berekend, dan zal de waarde 0 worden
     * geretourneerd.
     */
    public static int getLeeftijd(LocalDate geboortedatum) {
        if (geboortedatum == null) {
            logger.warn("Date of birth not specified.");
            return 0;
        }
        return Period.between(geboortedatum, LocalDate.now()).getYears();
    }
}
