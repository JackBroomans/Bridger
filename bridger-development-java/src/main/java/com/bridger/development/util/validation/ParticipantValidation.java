package com.bridger.development.util.validation;

import com.bridger.development.model.entity_utility_classes.UtilityParticipant;
import com.bridger.development.util.DatumTijdFuncties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.regex.PatternSyntaxException;

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
public class ParticipantValidation {
    private static final Logger logger = LoggerFactory.getLogger(ParticipantValidation.class);

    private static UtilityParticipant appVar;

    @Autowired
    public ParticipantValidation(UtilityParticipant appVar) {
        ParticipantValidation.appVar = appVar;
    }

    /**
     * <strong>validateString(<i>String</i>)</strong><br>
     * Checks if a string isn't null or empty or contains only whitespaces.<br>
     * @param text The String to be validated.
     * @return True if the string is validates and false when it's not specified.
     */
    public static boolean validateString(String text) {
        return text != null && !text.isBlank();
    }

    /**
     * <strong>validateBirthdate(<i>LocalDate</i>)</strong><br>
     * Checks if a birthdate is specified and matches the (external configured) requirements of the age boundaries.
     * @param birthdate The birthdate to be validated.
     * @return True if the birthdate os specified and matches the requirements, otherwise False.
     */
    public static boolean validateBirthdate(LocalDate birthdate) {
        try {
            if (birthdate == null) {
                logger.warn(appVar.MSG_MISSING_BIRTHDATE);
                return false;
            }
            int leeftijd = DatumTijdFuncties.getLeeftijd(birthdate);
            return leeftijd >= appVar.MIN_AGE_PARTICIPANT &&
                    leeftijd <= appVar.MAX_AGE_PARTICIPANT;
        }
        catch(DateTimeParseException ex) {
            logger.warn("Invalid date format.");
            return false;
        }
    }

    /**
     * <strong>validateEmailAddressFormat(<i>String</i></strong>)<br>
     * Validates the format of a given email-address against the external variable of the regular expression of the
     * email-address.
     * @param emailAddress The email-address to be validated.
     * @return True if the given email-address matches the regular expression and False if it isn't.
     * @throws PatternSyntaxException When an invalid regular expression is used.
     */
    public static boolean validateEmailAddressFormat(String emailAddress) throws PatternSyntaxException {
        if (emailAddress.matches(appVar.REGEX_EMAIL_ADDRESS)) {
            return true;
        }
        logger.warn(appVar.MSG_INVALID_REGEX_EMAIL);
        return false;
    }
}
