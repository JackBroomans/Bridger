package com.befrank.casedeveloperjava.model;

import com.befrank.casedeveloperjava.model.enums.Gender;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

import static com.befrank.casedeveloperjava.util.TekstFuncties.presentatie;
import static com.befrank.casedeveloperjava.util.Validaties.valideerGeboortedatum;
import static com.befrank.casedeveloperjava.util.Validaties.valideerTekenreeks;
import static javax.persistence.GenerationType.IDENTITY;

/**
 * <strong>Deelnemer</strong><br>
 * Entiteit waarin de basisgegevens van een deelnemer worden ondergebracht. Daarnaast zijn er een aantal methoden
 * beschikbaar waarin frequent gebruikte functionaliteit is opgenomen.<br>
 * Voor de specificatie van een deelnemer zijn de volgende velden zijn verplicht:
 * <ul>
 *     <li>Deelnemersnummer (Id)</li>
 *     <li>Familienaam</li>
 *     <li>Voornamen (Id)</li>
 *     <li>Geboortedatum</li>
 *     <li>Code van het geslacht (standaard 'O')</li>
 *     <li>Emailadres</li>
 *     <li>Mobiel telefoonnummer.</li>
 * </ul>
 */
@Component
@Entity
@Table(name="deelnemer")
public class Deelnemer implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name="deelnemersnummer", nullable = false, unique = true)
    private String deelnemersnummer;

    @Column(name="familienaam", nullable = false)
    private String familienaam;

    @Column(name="voorvoegsels")
    private String voorvoegsels;

    @Column(name="voornamen", nullable = false)
    private String voornamen;

    @Column(name="initialen")
    private String initialen;

    @Column(name="titelsprefix")
    private String prefixTitels;

    @Column(name="titelssuffix")
    private String suffixTitels;

    @Transient
    private Gender gender;

    @Column(name="geslachtscode", nullable = false)
    private String geslachtscode;

    @Column(name = "geboortedatum", nullable = false)
    private LocalDate geboortedatum;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name="telefoon_vast")
    private String telefoonVast;

    @Column(name="telefoon_mobiel", nullable = false)
    private String telefoonMobiel;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id", nullable = false, unique = true)
    private PremieDeelnemer premieDeelnemer;

    // Getters en Setters
    public long getId() {
        return id;
    }

    public String getDeelnemersnummer() {
        return deelnemersnummer;
    }
    public void setDeelnemersnummer(String deelnemersNummer) {
        if (!valideerTekenreeks(deelnemersNummer)) {
            // Todo: Implementeer logging en log 'Geen of ongeldig deelnemrsnummer'
            return;
        }
        this.deelnemersnummer = deelnemersNummer;
    }

    public String getFamilienaam() {
        return familienaam;
    }
    public void setFamilienaam(String familieNaam) {
        if (!valideerTekenreeks(familieNaam)) {
            // Todo: Implementeer logging en log 'Geen of ongeldige familienaam'
            return;
        }
        this.familienaam = familieNaam;
    }

    public String getVoorvoegsels() {
        return voorvoegsels;
    }
    public void setVoorvoegsels(String voorvoegsels) {
        this.voorvoegsels = voorvoegsels;
    }

    public String getVoornamen() {
        return voornamen;
    }
    public void setVoornamen(String voornamen) {
        if (!valideerTekenreeks(voornamen)) {
            // Todo: Implementeer logging en log 'Geen of ongeldige voorna(a)m(en)'
            return;
        }
        this.voornamen = voornamen;
    }

    public String getInitialen() {
        return initialen;
    }
    public void setInitialen(String initialen) {
        this.initialen = initialen;
    }

    public String getPrefixTitels() {
        return prefixTitels;
    }
    public void setPrefixTitels(String prefixTitels) {
        this.prefixTitels = prefixTitels;
    }

    public String getSuffixTitels() {
        return suffixTitels;
    }
    public void setSuffixTitels(String suffixTitels) {
        this.suffixTitels = suffixTitels;
    }

    public Gender getGeslacht() {
        return this.gender;
    }
    public void setGeslacht(Gender gender) {
        if (gender != null) {
            this.gender = gender;
            this.geslachtscode = gender.getCode();
        }
        else {
            // Todo: Implementeer logging en log 'Geen specificate van het geslacht'
        }
    }

    public String getGeslachtscode() {
        return this.geslachtscode;
    }
    public void setGeslachtscode(String codeGeslacht) {
        this.geslachtscode = codeGeslacht;
    }

    public LocalDate getGeboortedatum() {
        return geboortedatum;
    }

    /**
     * Indien de geboortedatum ongeldig of niet gespecificeerd is, dan blijft deze ongewijzigd. De logging hiervan
     * vindt tijdens de validatie plaats.
     */
    public void setGeboortedatum(LocalDate geboortedatum) {
        if (valideerGeboortedatum(geboortedatum)) {
            this.geboortedatum = geboortedatum;
        }
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        // Todo: Controleren op het juiiste formaat van een email adres m.b.v. een reguliere expressie
        if (!valideerTekenreeks(email)) {
            // Todo: Implementeer logging en log 'Geen of ongeldig email adres'
            return;
        }
        this.email = email;
    }

    public String getTelefoonVast() {
        return telefoonVast;
    }
    public void setTelefoonVast(String telefoonVast) {
        this.telefoonVast = telefoonVast;
    }

    public String getTelefoonMobiel() {
        return telefoonMobiel;
    }
    public void setTelefoonMobiel(String telefoonMobiel) {
        if (!valideerTekenreeks(telefoonMobiel)) {
            // Todo: Implementeer logging en log 'Geen of ongeldige familienaam'
            return;
        }
        this.telefoonMobiel = telefoonMobiel;
    }

    public PremieDeelnemer getPremieDeelnemer() {
        return premieDeelnemer;
    }
    public void setPremieDeelnemer(PremieDeelnemer premieDeelnemer) {
        this.premieDeelnemer = premieDeelnemer;
    }

    /**
     * <strong>stelCorrespondentienaamSamen()</strong><br>
     * De methode stelt uit de afzonderlijke componenten de volledige naam van de deelnemer samen voor gebruik in
     * correspondentie.<br>
     * @return De samengestelde naam voor gebruik bij correspondentie
     */
    public String getCorrespondentieRegel() {
        return (valideerTekenreeks(this.prefixTitels) ? this.prefixTitels : "") +
               (valideerTekenreeks(this.initialen) ? " " + this.initialen : "") +
               (valideerTekenreeks(this.voorvoegsels) ? " " + this.voorvoegsels : "") +
               (valideerTekenreeks(this.familienaam) ? " " + this.familienaam : " ") +
               (valideerTekenreeks(this.suffixTitels) ? " " + this.suffixTitels : "");
    }

    /**
     * <strong>getLeeftijd()</strong><br>
     *
     * Bepaalt de actuele leeftijd van de deelnemer gebaseerd op de geboortedatum.<br>
     * @return  De actuele leeftijd van de deelnemer.
     */
    public int getLeeftijd() {
        if (this.geboortedatum == null) {
            // Todo: Implementeer logging en log 'Geboortedatum niet gespecificeerd, kan leeftijd niet berekenen'
            return 0;
        }
        return Period.between(this.geboortedatum, LocalDate.now()).getYears();
    }

    /**
     * <strong>toString()</strong> <i>override</i><br>
     * Presenteert de (basis)gegevens van de deelnemer in in een meer leesbare vorm.<br>
     * @return De gegevens van de deelnemer.
     */
    @Override
    public String toString() {
        StringBuilder tekst = new StringBuilder().append("Deelnemer");

        try {
            tekst.append("\n\tKenmerk: ").append(this.id);
            tekst.append("\n\tDeelnemersnummer: ").append(presentatie(this.deelnemersnummer));
            tekst.append("\n\tFamilienaam: ").append(presentatie(this.familienaam));
            tekst.append("\n\tVoorvoegsels: ").append(presentatie(this.voorvoegsels));
            tekst.append("\n\tVoornamen: ").append(presentatie(this.voornamen));
            tekst.append("\n\tInitialen: ").append(presentatie(this.initialen));
            tekst.append("\n\tTitels (prefix): ").append(presentatie(this.prefixTitels));
            tekst.append("\n\tTitels (suffix): ").append(presentatie(this.suffixTitels));
            tekst.append("\n\tGeslacht: ").append(this.gender.getDescription());
            tekst.append("\n\tEmail: ").append(presentatie(this.email));
            tekst.append("\n\tTelefoon vast: ").append(presentatie(this.telefoonVast));
            tekst.append("\n\tTelefoon mobiel: ").append(presentatie(this.telefoonMobiel)).append("\n");

            return tekst.toString();
        }
        catch (Exception ex) {
            // Todo: Implementeer logging en log 'Fout bij het presenteren van de persoonsgegevens.'
            return "";
        }
    }
}
