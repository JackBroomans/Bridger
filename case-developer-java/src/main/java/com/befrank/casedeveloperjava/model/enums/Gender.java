package com.befrank.casedeveloperjava.model.enums;

import java.security.InvalidParameterException;

/**
 * <strong>Gender</strong><br>
 * Enumerates the gender of a person.
 */
public enum Gender {
    MALE("M", "Male"),
    FEMALE("F", "Female"),
    INDIFFERENCE("I", "Indifferent"),
    UNKNOWN("U", "Unknown");

    private String code;
    private String description;

    Gender(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return this.code;
    }

    public String getDescription() {
        return this.description;
    }

    /**
     * <strong>getByCode(<i>String</i></strong><br>
     * Search and selects a gender based on a given code.<br>
     * @return The gender with the given code, if not found, <null> will be returned.
     */
    public static Gender getByCode(String code) {
        try {
            if (code == null || code.isBlank() || code.length() > 1) {
                throw new InvalidParameterException();
            }
            for (Gender gender : Gender.values()) {
                if (gender.getCode().equals(code)) {
                    return gender;
                }
            }
            throw new InvalidParameterException();
        } catch (InvalidParameterException ex) {
            // Todo: Implementeer logger en log 'ongeldige code geslacht.'
        }
        return null;
    }

    /**
     * <strong>toString()</strong> <i>override</i><br>
     * Present the gender in a more readable way.
     */
    @Override
    public String toString() {
        return description = "Gender" +
                "\n\tElement: " + this.name() +
                "\n\tCode: " + this.code +
                "\n\tDescription: " + this.description;
    }
}

