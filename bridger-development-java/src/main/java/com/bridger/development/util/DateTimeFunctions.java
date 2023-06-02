package com.bridger.development.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

@Component
public class DateTimeFunctions {

    @Value("${age_calculation_not_possible}")
    String MSG_DATE_BEFORE_CURRENT;

    private static final Logger logger = LoggerFactory.getLogger(DateTimeFunctions.class);

    /**
     * <strong>calculateAge()</strong><br>
     * Calculates the age of a participant based on the date of birth.<br>
     * @return The age of the participant.
     */
    public int calculateAge(LocalDate date) {
        if (date == null) {
            logger.warn(MSG_DATE_BEFORE_CURRENT);
            return 0;
        }
        return Period.between(date, LocalDate.now()).getYears();
    }
}
