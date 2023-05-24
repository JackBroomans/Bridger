package com.befrank.casedeveloperjava.model;

import jakarta.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

import static com.befrank.casedeveloperjava.util.TekstFuncties.presentatie;
import static com.befrank.casedeveloperjava.util.Validations.validateString;
import static jakarta.persistence.GenerationType.IDENTITY;

/**
 * <strong>Address</strong><br>
 * Entity class which describes an address. Addresses are typical. This class contain also a method to assemble the
 * address elements for correspondency.
 */

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
        currentActive = currentActive;
    }

    /**
     * <strong>composeCorrespondenceAddress()</strong><br>
     * Composes the address element to a string which can be used for correspondence purposes.
     *
     * @return The for correspondence formatted address.
     */
    public String composeCorrespondenceAddress() {
        StringBuilder text = new StringBuilder()
                .append(validateString(this.street) ? this.street : "")
                .append(validateString(this.houseNumber) ? " " + this.houseNumber : "").append("\n")
                .append(validateString(this.postalcode) ? " " + this.postalcode : "")
                .append(validateString(this.city) ? " " + this.city : " ");
        return text.toString();
    }

    @Override
    public String toString() {
        StringBuilder text = new StringBuilder().append("Deelnemer");
        text.append("\n\tIdentifier: ").append(this.id);
        text.append("\n\tSequence: ").append(this.sequence == 0 ? "" : this.sequence);
        text.append("\n\tParticipant Id: ").append(this.participantId);
        text.append("\n\tStreet: ").append(presentatie(this.street));
        text.append("\n\tHouse number: ").append(presentatie(this.houseNumber));
        text.append("\n\tPostal code: ").append(presentatie(this.postalcode));
        text.append("\n\tCity: ").append(presentatie(this.city));
        text.append("\n\tCoutry: ").append(presentatie(this.country));
        text.append("\n\tCurrent address: ")
                .append(this.currentActive ? "ja" : "nee").append("\n");

        return text.toString();
    }
}
