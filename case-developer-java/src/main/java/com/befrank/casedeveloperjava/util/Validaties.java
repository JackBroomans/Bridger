package com.befrank.casedeveloperjava.util;

import com.befrank.casedeveloperjava.configuration.AppVariabelenDeelnemer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * <strong>Validations</strong><br>
 * Contains methods which performs all kinds of validations based on either formats or business rules..
 * The implemented methods are:
 * <ul>
 *     <li>
 *         <strong>validateString()</strong>
 *     </li>
 *     <li>
 *         <strong>validateDateOfBirth()</strong>
 *     </li>
 *      <li>
 *         <strong>validateEmailAddress()</strong>
 *     </li>
 *     <li>
 *         <strong>vaildateParticipantNumber()</strong>
 *     </li>
 * </ul>
 */
@Component
public class Validaties {

    private static AppVariabelenDeelnemer appVar;

    @Autowired
    public Validaties(AppVariabelenDeelnemer appVar) {
        Validaties.appVar = appVar;
    }

    /**
     * <strong>valideerTekenreeks(<i>String</i>)</strong><br>
     * Valideert of een tekenreeks niet <i>null</i> of leeg is. Onderleeg wordt ook een tekenreeks verstaan die
     * uitsluitend spaties bevat.<br>
     * @param tekst De tekenreeks die wordt onderzocht.
     * @return <i>Waar</i> indien de tekenreekst tekst bevat en <i>Niet Waar</i> wanneer de tekenreeks <i>null</i> of
     * leeg is.
     */
    public static boolean valideerTekenreeks(String tekst) {
        return tekst != null && !tekst.isBlank();
    }

    /**
     * <strong>valideerGeboortedatum(<i>LocalDate</i>)</strong><br>
     * De functie controleert of de opgegeven geboortedatum van een deelnemer geldig is. Deze is geldig indien:
     * <ol>
     *     <li>
     *         De datum is gespecificeerd, dus niet <i>null</i> is.
     *     </li>
     *     <li>
     *         De datum binnen de grenzen van de ingestelde leeftijden voldoet.
     *     </li>
     * </ol>
     * @param geboortedatum De te controleren geboortedatum van de deelnemer.
     * @return <i>True</i> indien de datum geldig is, <i>False</i> indien niet.
     */
    public static boolean valideerGeboortedatum(LocalDate geboortedatum) {
        try {
            if (geboortedatum == null) {
                // Todo: Implementeer logging en log 'Ongeldige of ontbrekende geboortedatum.'
                return false;
            }
            int leeftijd = DatumTijdFuncties.getLeeftijd(geboortedatum);
            return leeftijd >= appVar.minLeeftijdDeelnemer &&
                    leeftijd <= appVar.maxLeeftijdDeelnemer;
        }
        catch(DateTimeParseException ex) {
            // Todo: Implementeer logging en log 'Ongeldig datumformaat'
            return false;
        }
    }
}