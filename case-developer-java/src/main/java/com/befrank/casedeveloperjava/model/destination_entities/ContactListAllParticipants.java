package com.befrank.casedeveloperjava.model.destination_entities;

/**
 * Destination Entity Class to construct the contact list of all Participants from the Participants Entities.
 */
public class ContactListAllParticipants {

    private String participantNumber;
    private String fullName;
    private String email;
    private String telephone;
    private String cellphone;

    /* Getters and setters */
    public String getParticipantNumber() {
        return participantNumber;
    }
   public void setParticipantNumber(String participantNumber) {
        this.participantNumber = participantNumber;
    }

    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getCellphone() {
        return cellphone;
    }
    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

}

