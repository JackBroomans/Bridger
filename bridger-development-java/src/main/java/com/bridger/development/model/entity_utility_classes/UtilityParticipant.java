package com.bridger.development.model.entity_utility_classes;

import com.bridger.development.model.Participant;
import com.bridger.development.model.enums.Gender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Configuration
@PropertySource("classpath:appVar.yml")
@ConfigurationProperties(prefix = "participant")
public class UtilityParticipant {

    /* Regular expressions */
    @Value("${regex_participant_number}")
    public String REGEX_PARTICIPANT_NUMBER;

    @Value("${regex_email_address}")
    public String REGEX_EMAIL_ADDRESS;

    /* Date and time formats */
    @Value("${short_date_format}")
    public String SHORT_DATE_FORMAT;

    @Value("${short_date_time_format}")
    public String SHORT_DATE_TIME_FORMAT;

    /* Participants age boundaries */
    @Value("${minimum_age_participant}")
    public int MIN_AGE_PARTICIPANT;

    @Value("${maximum_age_participant}")
    public int MAX_AGE_PARTICIPANT;

    /* Default values */
    @Value("${default_gender}")
    public String DEFAULT_GENDER;

    @Value("${default_country}")
    public String DEFAULT_COUNTRY;

   /* Messages */
    @Value("${missing_number}")
    public String MSG_MISSING_NUMBER;

    @Value("${missing_family_name}")
    public String MSG_MISSING_FAMILY_NAME;

    @Value("${missing_surnames}")
    public String MSG_MISSING_SURNAMES;

    @Value("${missing_gender_specification}")
    public String MSG_GENDER_NOT_SPECIFIED;

    @Value("${missing_cellphone_number}")
    public String MSG_MISSING_CELLPHONE_NUMBER;

    @Value("${missing_birthdate}")
    public String MSG_MISSING_BIRTHDATE;

    @Value("${missing_email_address}")
    public String MSG_MISSING_EMAIL_ADDRESS;
    @Value("${invalid_email_address}")
    public String MSG_INVALID_EMAIL_ADDRESS;
    @Value("${invalid_regular_expression_email}")
    public String MSG_INVALID_REGEX_EMAIL;

    @Value("${age_calculation_not_possible}")
    public String MSG_AGE_CALCULATION_NOT_POSSIBLE;

    /* To assign the default settings on instantiation of an entity class without using a field assignment of the
     external variables, instantiation of an entity should be done by the method in the configuration class. */
    public Participant participant() {
        Participant participant = new Participant();
        participant.setGender(Gender.getByCode(this.DEFAULT_GENDER));
        participant.setParticipantNumber(generateParticipantNumber());
        return participant;
    }

    /**
     * <strong>generateParticipantNumber<i>()</i></strong><br>
     * Generates a unique participant number with three components:
     * <ol>
     *     <li>A date part from the actual date in the format 'yyMMdd'.</li>
     *     <li>A time part from the actual time in the format 'mmSSS'.</li>
     *     <li>A random generate number of three digits.</li>
     * </ol>
     * @return The generated participant number.
     */
    private String generateParticipantNumber() {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyMMdd-ssSSS");

        String builder = LocalDateTime.now().format(dateFormat) +
                "-" +
                new Random().nextInt(1000);
        return builder;
    }
}
