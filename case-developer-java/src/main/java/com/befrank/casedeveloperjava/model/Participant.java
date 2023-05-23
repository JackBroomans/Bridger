package com.befrank.casedeveloperjava.model;

import com.befrank.casedeveloperjava.configuration.AppVariablesParticipant;
import com.befrank.casedeveloperjava.model.enums.Gender;
import jakarta.persistence.*;

import static jakarta.persistence.GenerationType.IDENTITY;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

import static com.befrank.casedeveloperjava.util.TekstFuncties.presentatie;
import static com.befrank.casedeveloperjava.util.Validations.valideerGeboortedatum;
import static com.befrank.casedeveloperjava.util.Validations.valideerTekenreeks;

/**
 * <strong>Participant</strong><br>
 * Entity which describes the base properties of a participant in the pension fund. This entity also contains the k
 * following non persistable functionality methods:
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
    private static final AppVariablesParticipant appVar = new AppVariablesParticipant();

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private long id;

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

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id", nullable = false, unique = true)
    private com.befrank.casedeveloperjava.model.ParticipantPremium ParticipantPremium;

    // Getters en Setters
    public long getId() {
        return id;
    }

    public String getParticipantNumber() {
        return participantNumber;
    }

    public void setParticipantNumber(String participantNumber) {
        if (!valideerTekenreeks(participantNumber)) {
            logger.warn(appVar.MSG_MISSING_NUMBER);
            return;
        }
        this.participantNumber = participantNumber;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        if (!valideerTekenreeks(familyName)) {
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
        if (!valideerTekenreeks(surNames)) {
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

    /**
     * If the birthdate is invalid or not specified then it will remain the same. Logging of this exception takes
     * place during the validation.
     */
    public void setBirthdate(LocalDate birthdate) {
        if (valideerGeboortedatum(birthdate)) {
            this.birthdate = birthdate;
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        // Todo: Controleren op het juiiste formaat van een email adres m.b.v. een reguliere expressie
        if (!valideerTekenreeks(email)) {
            logger.warn(appVar.MSG_MISSING_EMAIL_ADDRESS);
            return;
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
        if (!valideerTekenreeks(cellphone)) {
            logger.warn(appVar.MSG_MISSING_CELLPHONE_NUMBER);
            return;
        }
        this.cellphone = cellphone;
    }

    public com.befrank.casedeveloperjava.model.ParticipantPremium getParticipantPremium() {
        return ParticipantPremium;
    }

    public void setParticipantPremium(com.befrank.casedeveloperjava.model.ParticipantPremium participantPremium) {
        this.ParticipantPremium = participantPremium;
    }

    /**
     * <strong>composeFullName()</strong><br>
     * Assembles the individual elements of the name to one string formatted which is commonly (readable) used. <br>
     * @return The composed name.
     */
    public String composeFullName() {
        return (valideerTekenreeks(this.prefixTitles) ? this.prefixTitles : "") +
                (valideerTekenreeks(this.initials) ? " " + this.initials : "") +
                (valideerTekenreeks(this.prefixes) ? " " + this.prefixes : "") +
                (valideerTekenreeks(this.familyName) ? " " + this.familyName : " ") +
                (valideerTekenreeks(this.suffixTitles) ? " " + this.suffixTitles : "");
    }

    /**
     * <strong>calculateAge()</strong><br>
     * Calculates the age of a participant based on the date of birth.<br>
     * @return The age of the participant.
     */
    public int calculateAge() {
        if (this.birthdate == null) {
            logger.warn(appVar.MSG_AGE_CALCULATION_NOT_POSSIBLE);
            return 0;
        }
        return Period.between(this.birthdate, LocalDate.now()).getYears();
    }

    @Override
    public String toString() {
        StringBuilder text = new StringBuilder().append("Participant");

        text.append("\n\tId: ").append(this.id);
        text.append("\n\tParticipant number: ").append(presentatie(this.participantNumber));
        text.append("\n\tFamilyname: ").append(presentatie(this.familyName));
        text.append("\n\tPrefixes: ").append(presentatie(this.prefixes));
        text.append("\n\tSurnames: ").append(presentatie(this.surNames));
        text.append("\n\tInitials: ").append(presentatie(this.initials));
        text.append("\n\tTitles (prefix): ").append(presentatie(this.prefixTitles));
        text.append("\n\tTitles (suffix): ").append(presentatie(this.suffixTitles));
        text.append("\n\tGender: ").append(this.gender.getDescription());
        text.append("\n\tEmail: ").append(presentatie(this.email));
        text.append("\n\tTelephone home: ").append(presentatie(this.homeTelephone));
        text.append("\n\tCellphone: ").append(presentatie(this.cellphone)).append("\n");

        return text.toString();
    }
}
