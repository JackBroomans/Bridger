package com.bridger.development.util;

import static com.bridger.development.util.validation.ParticipantValidation.validateString;

public class TextFunctions {

    /**
     * <strong>presentation<i>(String)</i></strong><br>
     * Checks if a string is <i>null</i>, empty or contains only whitespaces. If this is the case the string will be
     * reformed to an empty string.
     * alleen door de methode 'toString()' gebruikt.<br>
     * @param text The string to check. and reformed.
     * @return The original or reformed string.
     */
    public static String presentation(String text) {
        return validateString(text) ? text : "";
    }

}
