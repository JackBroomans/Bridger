package com.befrank.casedeveloperjava.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
public class Deelnemer {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    private String familieNaam;
    private String voorvoegsels;
    private String voornamen;
    private String initialen;
    private String prefixTitels;
    private String suffixTitels;

    @Transient
    private Geslacht geslacht;

    private String codeGeslacht;

    public Integer getId() {
        return id;
    }

    public String getFamilieNaam() {
        return familieNaam;
    }
    public void setFamilieNaam(String familyName) {
        this.familieNaam = familyName;
    }

    public String getVoorvoegsels() {
        return voorvoegsels;
    }
    public void setVoorvoegsels(String prefixesName) {
        this.voorvoegsels = prefixesName;
    }

    public String getVoornamen() {
        return voornamen;
    }
    public void setVoornamen(String foreNames) {
        this.voornamen = foreNames;
    }

    public String getInitialen() {
        return initialen;
    }
    public void setInitialen(String initials) {
        this.initialen = initials;
    }

    public String getPrefixTitels() {
        return prefixTitels;
    }
    public void setPrefixTitels(String titlesPrefix) {
        this.prefixTitels = titlesPrefix;
    }

    public String getSuffixTitels() {
        return suffixTitels;
    }
    public void setSuffixTitels(String titlesSuffix) {
        this.suffixTitels = titlesSuffix;
    }

    /**
     * <strong>formatFullName()</strong><br>
     * De methode stelt uit de afzonderlijke componenten de volledige naam van de deelnemer samen voor gebruik in
     * correspondentie.<br>
     *
     */
    public String formatFullName() {
        return "";
    }

    /**
     * <strong></strong>
     */
}
