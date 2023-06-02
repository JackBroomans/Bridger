package com.bridger.development.util;

import static com.bridger.development.util.validation.ParticipantValidation.validateString;

public class StringFunctions {

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

    /**
     * <strong>addLeadingZeros<i>(String, int)</i></strong><br>
     * Add a number of leading zero's to a string (left padding). The string can't contain any errors or an exception
     * will be thrown.
     * @param text The string where the leading zero's should be added.
     * @param length The requested length of the string, including the leading zero's.
     * @return A string of the requested length if necessary with added leading zero's.
     * @throws IllegalArgumentException This happens when the string parameter is either null or contains whitespaces.
     */
    public static String addLeadingZeros(String text, int length)  {
        if(text == null || text.contains(" ")) {
            throw new IllegalArgumentException("String parameter must not contain any whitespaces");
        }
        return String.format("%1$" + length + "s", text).replace(' ', '0');
    }

}
