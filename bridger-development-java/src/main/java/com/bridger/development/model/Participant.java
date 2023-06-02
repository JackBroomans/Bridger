package com.bridger.development.model;

import com.bridger.development.model.entity_utility_classes.UtilityParticipant;
import com.bridger.development.model.enums.Gender;
import com.bridger.development.util.StringFunctions;
import jakarta.persistence.*;

import static com.bridger.development.util.validation.ParticipantValidation.*;
import static jakarta.persistence.GenerationType.IDENTITY;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDate;

/**
 * <strong>Participant</strong><br>
 * This entity class describes the properties of a participant in the pension fund according to the associated database
 * table. The entity also contains the following (non persistable) methods:
 * <ul>
 *     <li>
 *         <strong>composeFullName()</strong>
 *     </li>
 *     <li>
 *         <strong>calculateAge()</strong>
 *     </li>
 * </ul>
 */
@Component
@Entity
@Table(name = "participant")
public class Participant implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(Participant.class);
    private static final UtilityParticipant appVar = new UtilityParticipant();

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "useraccount_id", nullable = false)
    public long useracccountId;

    @Column(name = "participantnumber", nullable = false, unique = true)
    private String participantNumber;

    @Column(name = "familyname", nullable = false)
    private String familyName;

    @Column(name = "prefixes")
    private String prefixes;

    @Column(name = "surnames", nullable = false)
    private String surNames;

    @Column(name = "initials")
    private String initials;

    @Column(name = "prefixtitles")
    private String prefixTitles;

    @Column(name = "suffixtitles")
    private String suffixTitles;

    @Transient
    private Gender gender;

    @Column(name = "gendercode", nullable = false)
    private String genderCode;

    @Column(name = "birthdate", nullable = false)
    private LocalDate birthdate;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "hometelephone")
    private String homeTelephone;

    @Column(name = "cellphone", nullable = false)
    private String cellphone;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "id", referencedColumnName = "useraccount_id")
//    UserAccount userAccount;

    // Getters en Setters
    public long getId() {
        return id;
    }

    public void setUseracccountId(long useracccountId) {
        this.useracccountId = useracccountId;
    }

    public String getParticipantNumber() {
        return participantNumber;
    }
    public void setParticipantNumber(String participantNumber) {
        if (!validateString(participantNumber)) {
            logger.warn(appVar.MSG_MISSING_NUMBER);
            return;
        }
        this.participantNumber = participantNumber;
    }

    public String getFamilyName() {
        return familyName;
    }
    public void setFamilyName(String familyName) {
        if (!validateString(familyName)) {
            logger.warn(appVar.MSG_MISSING_FAMILY_NAME);
            return;
        }
        this.familyName = familyName;
    }

    public String getPrefixes() {
        return prefixes;
    }
    public void setPrefixes(String prefixes) {
        this.prefixes = prefixes;
    }

    public String getSurNames() {
        return surNames;
    }
    public void setSurNames(String surNames) {
        if (!validateString(surNames)) {
            logger.warn(appVar.MSG_MISSING_SURNAMES);
            return;
        }
        this.surNames = surNames;
    }

    public String getInitials() {
        return initials;
    }
    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getPrefixTitles() {
        return prefixTitles;
    }
    public void setPrefixTitles(String prefixTitles) {
        this.prefixTitles = prefixTitles;
    }

    public String getSuffixTitles() {
        return suffixTitles;
    }
    public void setSuffixTitles(String suffixTitles) {
        this.suffixTitles = suffixTitles;
    }

    public Gender getGender() {
        return this.gender;
    }
    public void setGender(Gender gender) {
        if (gender != null) {
            this.gender = gender;
            this.genderCode = gender.getCode();
        } else {
            logger.warn(appVar.MSG_GENDER_NOT_SPECIFIED);
        }
    }

    public String getGenderCode() {
        return this.genderCode;
    }
    public void setGenderCode(String genderCode) {
        this.genderCode = genderCode;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }
    public void setBirthdate(LocalDate birthdate) {
        if (validateBirthdate(birthdate)) {
            this.birthdate = birthdate;
        }
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        if (!validateString(email)) {
            logger.warn(appVar.MSG_MISSING_EMAIL_ADDRESS);
            return;
        }
        else {
            if (!validateEmailAddressFormat(email)) {
                logger.warn(appVar.MSG_INVALID_EMAIL_ADDRESS);
                return;
            }
        }
        this.email = email;
    }

    public String getHomeTelephone() {
        return homeTelephone;
    }
    public void setHomeTelephone(String homeTelephone) {
        this.homeTelephone = homeTelephone;
    }

    public String getCellphone() {
        return cellphone;
    }
    public void setCellphone(String cellphone) {
        if (!validateString(cellphone)) {
            logger.warn(appVar.MSG_MISSING_CELLPHONE_NUMBER);
            return;
        }
        this.cellphone = cellphone;
    }

    /**
     * <strong>composeFullName()</strong><br>
     * Assembles the individual elements of the name to one string formatted which is commonly (readable) used. <br>
     * @return The composed name.
     */
    public String composeFullName() {
        return (validateString(this.prefixTitles) ? this.prefixTitles : "") +
                (validateString(this.initials) ? " " + this.initials : "") +
                (validateString(this.prefixes) ? " " + this.prefixes : "") +
                (validateString(this.familyName) ? " " + this.familyName : " ") +
                (validateString(this.suffixTitles) ? " " + this.suffixTitles : "");
    }

    @Override
    public String toString() {
        StringBuilder text = new StringBuilder().append("Participant");

        text.append("\n\tId: ").append(this.id);
        text.append("\n\tParticipant number: ").append(StringFunctions.presentation(this.participantNumber));
        text.append("\n\tFamilyname: ").append(StringFunctions.presentation(this.familyName));
        text.append("\n\tPrefixes: ").append(StringFunctions.presentation(this.prefixes));
        text.append("\n\tSurnames: ").append(StringFunctions.presentation(this.surNames));
        text.append("\n\tInitials: ").append(StringFunctions.presentation(this.initials));
        text.append("\n\tTitles (prefix): ").append(StringFunctions.presentation(this.prefixTitles));
        text.append("\n\tTitles (suffix): ").append(StringFunctions.presentation(this.suffixTitles));
        text.append("\n\tGender: ").append(this.gender.getDescription());
        text.append("\n\tEmail: ").append(StringFunctions.presentation(this.email));
        text.append("\n\tTelephone home: ").append(StringFunctions.presentation(this.homeTelephone));
        text.append("\n\tCellphone: ").append(StringFunctions.presentation(this.cellphone)).append("\n");

        return text.toString();
    }
}
