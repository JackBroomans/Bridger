package com.bridger.development.model;

import com.bridger.development.util.StringFunctions;
import com.bridger.development.util.validation.ParticipantValidation;
import jakarta.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.Serializable;

import static jakarta.persistence.GenerationType.IDENTITY;

/**
 * <strong>Address</strong><br>
 * Entity class which describes an address. Addresses are typical. This class contain also a method to assemble the
 * address elements for correspondency.
 */
@Component
@Entity
@Table(name = "address")
public class Address implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(Address.class);

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "sequence", nullable = false)
    private int sequence;

    @Column(name = "participant_id", nullable = false)
    private long participantId;

    @Column(name = "street")
    private String street;

    @Column(name = "housenumber")
    private String houseNumber;

    @Column(name = "postalcode")
    private String postalcode;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Column(name = "current", nullable = false)
    private Boolean currentActive = false;

    // Getters en Setters
    public long getId() {
        return id;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public long getParticipantId() {
        return participantId;
    }

    public void setParticipantId(long participantId) {
        this.participantId = participantId;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String straatnaam) {
        this.street = straatnaam;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Boolean getCurrentActive() {
        return currentActive;
    }

    public void setCurrentActive(Boolean currentActive) {
        this.currentActive = currentActive;
    }

    /**
     * <strong>composeCorrespondenceAddress()</strong><br>
     * Composes the address element to a string which can be used for correspondence purposes.
     *
     * @return The for correspondence formatted address.
     */
    public String composeCorrespondenceAddress() {
        return (ParticipantValidation.validateString(this.street) ? this.street : "") +
                (ParticipantValidation.validateString(this.houseNumber) ? " " + this.houseNumber : "") + "\n" +
                (ParticipantValidation.validateString(this.postalcode) ? " " + this.postalcode : "") +
                (ParticipantValidation.validateString(this.city) ? " " + this.city : " ");
    }

    @Override
    public String toString() {
        return "Deelnemer" +
                "\n\tIdentifier: " + this.id +
                "\n\tSequence: " + (this.sequence == 0 ? "" : this.sequence) +
                "\n\tParticipant Id: " + this.participantId +
                "\n\tStreet: " + StringFunctions.presentation(this.street) +
                "\n\tHouse number: " + StringFunctions.presentation(this.houseNumber) +
                "\n\tPostal code: " + StringFunctions.presentation(this.postalcode) +
                "\n\tCity: " + StringFunctions.presentation(this.city) +
                "\n\tCoutry: " + StringFunctions.presentation(this.country) +
                "\n\tCurrent address: " +
                (this.currentActive ? "ja" : "nee") + "\n";
    }
}
