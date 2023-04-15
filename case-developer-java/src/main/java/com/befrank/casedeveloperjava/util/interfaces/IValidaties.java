package com.befrank.casedeveloperjava.util.interfaces;

import com.befrank.casedeveloperjava.util.DatumTijdFuncties;
import com.befrank.casedeveloperjava.util.DatumTijdFuncties.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public interface IValidaties {

    // Todo: Variabele opnemen in resources-bestand om externe configuratie mogelijk te maken
    DateTimeFormatter KORTE_DATUM_FORMAAT = DateTimeFormatter.ofPattern("d-MM-yyyy");
    int MINIMUM_LEEFTIJD_DEELNEMER = 18;
    int MAXIMUM_LEEFTTIJD_DEELNEMR = 120;

    /**
     * <strong>valideerTekenreeks(<i>String</i>)</strong><br>
     * Valideert of een tekenreeks niet <i>null</i> of leeg is. Onderleeg wordt ook een tekenreeks verstaan die
     * uitsluitend spaties bevat.<br>
     * @param tekst De tekenreeks die wordt onderzocht.
     * @return <i>Waar</i> indien de tekenreekst tekst bevat en <i>Niet Waar</i> wanneer de tekenreeks <i>null</i> of
     * leeg is.
     */
    static boolean valideerTekenreeks(String tekst) {
        return tekst != null && !tekst.isBlank();
    }

    /**
     * <strong>valideerGeboortedatumUitTekenreeks(<i>String</i></strong><br>
     * Valideert of een als tekenreeks gedefinieerde geboortedatum een geldige datum is volgens het voorgeschreven
     * datum-formaat en of de datum tussen de vastgestelde grenzen van de leeftijd ligt.<br>
     * @param datum De geboorteDatum die op geldigheis wordt gecontroleerd.
     * @return <i>True</i> waneer de geboorte datum geldig is en <i>False</i> wanneer dit niet het geval is.
     */
    static boolean valideerGeboortedatumUitTekenreeks(String datum) {
        try {
             LocalDate testDatum = LocalDate.parse(datum, KORTE_DATUM_FORMAAT);
            int leeftijd = DatumTijdFuncties.getLeeftijd(testDatum);
            if (leeftijd < MINIMUM_LEEFTIJD_DEELNEMER || leeftijd > MAXIMUM_LEEFTTIJD_DEELNEMR) {
                // Todo: Implementeer logging en log 'Ongeldige leeftijd deelnemer'
                return false;
            }
            return true;
        }
        catch(DateTimeParseException ex) {
            // Todo: Implementeer logging en log 'Ongeldig datumformaat'
            return false;
        }
    }
}
