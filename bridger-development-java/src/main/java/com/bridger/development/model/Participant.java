package com.bridger.development.model;

import com.bridger.development.model.enums.Gender;
import com.bridger.development.util.StringFunctions;
import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDate;

import static com.bridger.development.util.StringFunctions.validateString;
import static com.bridger.development.util.validation.ParticipantValidation.validateBirthdate;
import static com.bridger.development.util.validation.ParticipantValidation.validateEmailAddressFormat;
import static jakarta.persistence.GenerationType.IDENTITY;

/**
 * <strong>Participant</strong><br>
 * This entity class describes the properties of a participant in the pension fund according to the associated database
 * table. The entity also contains the following (non persistable) methods:
 * <ul>
 *     <li>
 *         <strong>composeFullName()</strong>
 *     </li>
 * </ul>
 */
@Component
@Entity
@Table(name = "participant")
public class Participant implements Serializable {

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
        if (validateString(participantNumber)) {
            this.participantNumber = participantNumber;
        }
    }

    public String getFamilyName() {
        return familyName;
    }
    public void setFamilyName(String familyName) {
        if (validateString(familyName)) {
            this.familyName = familyName;
        }
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
        if (validateString(surNames)) {
            this.surNames = surNames;
        }
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
        if (validateString(email) && validateEmailAddressFormat(email)) {
            this.email = email;
        }
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
        if (validateString(cellphone)) {
            this.cellphone = cellphone;
        }
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
        text.append("\n\tBirthdate: ").append(this.birthdate == null ? "" :this.birthdate);
        text.append("\n\tEmail: ").append(StringFunctions.presentation(this.email));
        text.append("\n\tTelephone home: ").append(StringFunctions.presentation(this.homeTelephone));
        text.append("\n\tCellphone: ").append(StringFunctions.presentation(this.cellphone)).append("\n");

        return text.toString();
    }
}
