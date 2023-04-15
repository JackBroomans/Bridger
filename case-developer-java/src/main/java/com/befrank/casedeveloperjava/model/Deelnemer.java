package com.befrank.casedeveloperjava.model;

import com.befrank.casedeveloperjava.util.interfaces.IValidaties;
import static com.befrank.casedeveloperjava.util.interfaces.IValidaties.valideerTekenreeks;

import javax.persistence.*;
import static javax.persistence.GenerationType.IDENTITY;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

/**
 * <strong>Deelnemer</strong><br>
 * Entiteit waarin de persoonsgegevens betreffen een deelnemer worden ondergebrach en waarvan de velden corresponderen
 * met die in de tabel in de database.<br>
 * De volgende elementen dienen gespecificieerd te zijn, de overige zijn optioneel:
 * <ul>
 *     <li>Kenmerk (Id)</li>
 *     <li>Dellnemersnummer</li>
 *     <li>Familienaam</li>
 *     <li>Voornamen</li>
 *     <li>Emailadres</li>
 *     <li>Mobiel telefoonnummer</li>
 * </ul>
 */

@Entity
public class Deelnemer implements Serializable, IValidaties {
    // Todo: Variabele opnemen in resources-bestand om externe configuratie mogelijk te maken
    @Transient
    private static final DateTimeFormatter KORTE_DATUM_FORMAAT = DateTimeFormatter.ofPattern("d-MM-yyyy");

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    private String deelnemersNummer;
    private String familieNaam;
    private String voorvoegsels;
    private String voornamen;
    private String initialen;
    private String prefixTitels;
    private String suffixTitels;
    @Transient
    private Geslacht geslacht = Geslacht.getStandaardOptie();
    private String codeGeslacht;
    private LocalDate geboortedatum;
    private String email;
    private String telefoonVast;
    private String telefoonMobiel;

    // Getters en Setters
    public Integer getId() {
        return id;
    }

    public String getDeelnemersNummer() {
        return deelnemersNummer;
    }
    public void setDeelnemersNummer(String deelnemersNummer) {
        if (!valideerTekenreeks(deelnemersNummer)) {
            // Todo: Implementeer logging en log 'Geen of ongeldig deelnemrsnummer'
            return;
        }
        this.deelnemersNummer = deelnemersNummer;
    }

    public String getFamilieNaam() {
        return familieNaam;
    }
    public void setFamilieNaam(String familieNaam) {
        if (!valideerTekenreeks(familieNaam)) {
            // Todo: Implementeer logging en log 'Geen of ongeldige familienaam'
            return;
        }
        this.familieNaam = familieNaam;
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

    public Geslacht getGeslacht() {
        return geslacht;
    }
    public void setGeslacht(Geslacht geslacht) {
        if (geslacht == null) {
            // Todo: Implementeer logging en log 'Geen specificate van het geslacht'
            return;
        }
        this.geslacht = geslacht;
        this.codeGeslacht = geslacht.getCode();
    }

    public LocalDate getGeboortedatum() {
        return geboortedatum;
    }
    public void setGeboortedatum(String geboortedatum) {
        if (IValidaties.valideerGeboortedatumUitTekenreeks(geboortedatum)) {
            this.geboortedatum = LocalDate.parse(geboortedatum, KORTE_DATUM_FORMAAT);
        }
        else {
            // Todo: Implementeer logging en log 'Ongeldig geboortedatum. Niet ingesteld.'
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

    /**
     * <strong>stelCorrespondentienaamSamen()</strong><br>
     * De methode stelt uit de afzonderlijke componenten de volledige naam van de deelnemer samen voor gebruik in
     * correspondentie.<br>
     * @return De samengestelde naam voor gebruik bij correspondentie
     */
    public String stelCorrespondentienaamSamen() {
        StringBuilder tekst = new StringBuilder()
                .append(valideerTekenreeks(this.prefixTitels) ? this.prefixTitels : "")
                .append(valideerTekenreeks(this.initialen) ? " " + this.initialen : "")
                .append(valideerTekenreeks(this.voorvoegsels) ? " " + this.voorvoegsels : "")
                .append(valideerTekenreeks(this.familieNaam) ?  " " + this.familieNaam  : " ")
                .append(valideerTekenreeks(this.suffixTitels) ? " " + this.suffixTitels : "");
        return tekst.toString();
    }

    /**
     * <strong>getLeeftijd()</strong><br>
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
            tekst.append("\n\tKenmerk: ").append(this.id == null ? "" : this.id);
            tekst.append("\n\tDeelnemersnummer: ").append(presentatie(this.deelnemersNummer));
            tekst.append("\n\tFamilienaam: ").append(presentatie(this.familieNaam));
            tekst.append("\n\tVoorvoegsels: ").append(presentatie(this.voorvoegsels));
            tekst.append("\n\tVoornamen: ").append(presentatie(this.voornamen));
            tekst.append("\n\tInitialen: ").append(presentatie(this.initialen));
            tekst.append("\n\tTitels (prefix): ").append(presentatie(this.prefixTitels));
            tekst.append("\n\tTitels (suffix): ").append(presentatie(this.suffixTitels));
            tekst.append("\n\tGeslacht: ").append(this.geslacht.getTekst());
            tekst.append("\n\tEmail: ").append(presentatie(this.email));
            tekst.append("\n\tTelefoon vast: ").append(presentatie(this.telefoonVast));
            tekst.append("\n\tTelefoon mobiel: ").append(presentatie(this.telefoonMobiel));

            return tekst.toString();
        }
        catch (Exception ex) {
            // Todo: Implementeer logging en log 'Fout bij het presenteren van de persoonsgegevens.'
            return "";
        }
    }

    /**
     * <strong>presentatie</strong>
     * Controleert de specificatie van een tekst of deze null, leeg of uitsluitend uit spaties bestaat. De metode wordt
     * alleen door de methode 'toString()' gebruikt.<be></be>
     * @param tekst De tekenreeks die wordt gecontroleerd.
     * @return De gecontroleerde tekenreeks of indien deze niet aan de voorwaarden voldoet, een lege tekenreeks
     */
    private String presentatie(String tekst) {
        return valideerTekenreeks(tekst) ? tekst : "";
    }
}
