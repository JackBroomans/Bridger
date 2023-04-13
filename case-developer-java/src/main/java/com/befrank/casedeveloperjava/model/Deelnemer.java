package com.befrank.casedeveloperjava.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
public class Deelnemer {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    private String familyName;
    private String prefixesName;
    private String foreNames;
    private String initials;
    private String titlesPrefix;
    private String titlesSuffix;
    private Geslacht gender;

    public Integer getId() {
        return id;
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
}
