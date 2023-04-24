package com.befrank.casedeveloperjava.model;

import com.befrank.casedeveloperjava.util.interfaces.IValidaties;

import javax.persistence.*;
import java.io.Serializable;

import static com.befrank.casedeveloperjava.util.TekstFuncties.presentatie;
import static com.befrank.casedeveloperjava.util.interfaces.IValidaties.valideerTekenreeks;
import static javax.persistence.GenerationType.IDENTITY;

/**
 * <strong>Adres</strong><br>
 * Entiteit waarin de adresgegevens betreffende een deelnemer worden ondergebracht en waarvan de velden corresponderen
 * met die in de tabel in de database. Aan een deelnemer kunnen meerdere adressen worden gekoppeld om de historie te
 * kunnen bijhouden. Echter slechts één adres kan als het actieve adres zijn ingesteld.<br>
 * De volgende eigenschappen zijn verplicht, de overige zijn optioneel:
 * <ul>
 *     <li>Kenmerk (Id)</li>
 *     <li>Volgnummer</li>
 *     <li>Deelnemer (Id)</li>
 *     <li>Straatnaam</li>
 *     <li>Plaatsnaam</li>
 * </ul>
 */

@Entity
@Table(name = "adres")
public class Adres implements Serializable, IValidaties {
    // Todo: Variabele opnemen in resources-bestand om externe configuratie mogelijk te maken
    @Transient
    private static final String STANDAARD_LAND = "Nederland";

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;
    private int volgnummer;
    private long deelnemer;
    private String straatnaam;
    private String huisnummer;

    private String postcode;
    private String plaatsnaam;
    private String land = STANDAARD_LAND;
    private Boolean actief = false;

    // Getters en Setters
    public long getId() {
        return id;
    }

    public int getVolgnummer() {
        return volgnummer;
    }
    public void setVolgnummer(int volgnummer) {
        this.volgnummer = volgnummer;
    }

    public long getDeelknemr() {
        return deelnemer;
    }
    public void setDeelknemr(long deelnemer) {
        this.deelnemer = deelnemer;
    }

    public String getStraatnaam() {
        return straatnaam;
    }
    public void setStraatnaam(String straatnaam) {
        this.straatnaam = straatnaam;
    }
    public String getHuisnummer() {
        return huisnummer;
    }
    public void setHuisnummer(String huisnummer) {
        this.huisnummer = huisnummer;
    }
    public String getPostcode() {
        return postcode;
    }
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getPlaatsnaam() {
        return plaatsnaam;
    }
    public void setPlaatsnaam(String plaatsnaam) {
        this.plaatsnaam = plaatsnaam;
    }

    public String getLand() {
       return land;
    }
    public void setLand(String land) {
        this.land = land;
    }

    public Boolean getActief() {
        return actief;
    }
    public void setActief(Boolean active) {
        actief = active;
    }

    /**
     * <strong>stelCorrespondentieAdresSamen()</strong><br>
     * De methode stelt uit de afzonderlijke componenten het volledige adres samen voor gebruik in correspondentie.<br>
     * @return Het samengestelde adres voor gebruik bij correspondentie
     */
    public String stelCorrespondentieAdresSamen() {
        StringBuilder tekst = new StringBuilder()
                .append(valideerTekenreeks(this.straatnaam) ? this.straatnaam : "")
                .append(valideerTekenreeks(this.huisnummer) ? " " + this.huisnummer : "").append("\n")
                .append(valideerTekenreeks(this.postcode) ? " " + this.postcode : "")
                .append(valideerTekenreeks(this.plaatsnaam) ?  " " + this.plaatsnaam : " ");
        if (!this.land.equals(STANDAARD_LAND)) { tekst.append("\n").append(this.land); }

        return tekst.toString();
    }

    /**
     * <strong>toString()</strong> <i>override</i><br>
     * Presenteert de adresgegevens in in een meer leesbare vorm.<br>
     * @return De gegevens van het adres.
     */
    @Override
    public String toString() {
        StringBuilder tekst = new StringBuilder().append("Deelnemer");

        try {
            tekst.append("\n\tKenmerk (Id): ").append(this.id);
            tekst.append("\n\tVolgnummer: ").append(this.volgnummer == 0 ? "" : this.volgnummer);
            tekst.append("\n\tDeelnemer (Id): ").append(this.deelnemer);
            tekst.append("\n\tStraatnaam: ").append(presentatie(this.straatnaam));
            tekst.append("\n\tHuisaanduiding: ").append(presentatie(this.huisnummer));
            tekst.append("\n\tPostcode: ").append(presentatie(this.postcode));
            tekst.append("\n\tPlaatsnaam: ").append(presentatie(this.plaatsnaam));
            tekst.append("\n\tLand: ").append(presentatie(this.land));
            tekst.append("\n\tActief adres: ").append(this.actief ? "ja" : "nee").append("\n");

            return tekst.toString();
        }
        catch (Exception ex) {
            // Todo: Implementeer logging en log 'Fout bij het presenteren van de persoonsgegevens.'
            return "";
        }
    }

}
