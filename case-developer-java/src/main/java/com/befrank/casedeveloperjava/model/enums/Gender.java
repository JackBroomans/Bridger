package com.befrank.casedeveloperjava.model.enums;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.InvalidParameterException;

/**
 * <strong>Gender</strong><br>
 * Enumerates the genders which can be assigned to a person.
 */
public enum Gender {
    MALE("M", "Male"),
    FEMALE("F", "Female"),
    INDIFFERENCE("I", "Indifferent"),
    UNKNOWN("U", "Unknown");

    private static final Logger logger = LoggerFactory.getLogger(Gender.class);

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
            logger.warn("Invalid code of gender passed.");
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

