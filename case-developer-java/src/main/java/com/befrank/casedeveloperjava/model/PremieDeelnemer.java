package com.befrank.casedeveloperjava.model;

import org.springframework.stereotype.Component;

import jakarta.persistence.*;
import static jakarta.persistence.GenerationType.IDENTITY;

/**
 *
 */
@Component
@Entity
@Table(name = "premie")
public class PremieDeelnemer {

    // Todo: Vaste (actueel) franchise elders vastleggen voor externe premie configuratie
    @Transient
    private static final float FRANCISE_ACTUEEL = 15.599f;
    // Todo: Vaste (actueel) bechikbare premie percentage elders vastleggen voor externe premie configuratie
    @Transient
    private static final float BESCHIBARE_PREMIE_PERCENTAGE_ACTUEEL = 5f;


    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    // Field name in the 'premie' class
    @OneToOne(mappedBy = "premieDeelnemer", fetch = FetchType.EAGER)
    private Deelnemer deelnemer;
    @Column(name = "participant_id")
    private long participantId;
    @Column(name = "fulltime_salaris")
    private float fullTimeSalaris;
    @Column(name = "parttime_percentage")
    private float parttimePercentage;
    @Column(name = "francise_actueel")
    private float franciseActueel;
    @Column(name = "percentage_beschikbare_premie")
    private float percentageBeschikbarePremie;

    @Transient
    private float waardeHuidigeBeleggingen;

    public PremieDeelnemer() {
        // Todo: waardeHuidigebelegging ophalen uit database
//        this.waardeHuidigeBeleggingen = .....;
    }


    // Getters en Setters
    public Deelnemer getDeelnemr() {
        return this.deelnemer;
    }
    public void setDeelnemer(Deelnemer deelnemer) {
        this.deelnemer = deelnemer;
        this.participantId = deelnemer.getId();
    }

    public float getFullTimeSalaris() {
        return fullTimeSalaris;
    }
    public void setFullTimeSalaris(float fullTimeSalaris) {
        this.fullTimeSalaris = fullTimeSalaris;
    }

    public float getParttimePercentage() {
        return parttimePercentage;
    }
    public void setParttimePercentage(float parttimePercentage) {
        this.parttimePercentage = parttimePercentage;
    }

    public float getFranciseActueel() {
        return franciseActueel;
    }
    public void setFranciseActueel(float franciseActueel) {
        this.franciseActueel = franciseActueel;
    }

    public float getPercentageBeschikbarePremie() {
        return percentageBeschikbarePremie;
    }
    public void setPercentageBeschikbarePremie(float percentageBeschikbarePremie) {
        this.percentageBeschikbarePremie = percentageBeschikbarePremie;
    }

    // Todo: Vaststellen werknemerschap anders dan alleen op basis van parttime-percentage.
    /**
     * <strong>berekenPremie()</strong><br>
     * Berekend de premieafdracht op basis van de volgende elementen:
     * <ul>
     *     <li>Full-time bruto jaarsalaris</li>
     *     <li>Parttime percentage</li>
     *     <li>Franchise in de regeling </li>
     *     <li>Beschikbare premie percentage</li>
     *     <li>Huidige waarde van de beleggingen</li>
     * </ul><br>
     * <i>Indien het parttime percentage op 0 is gezet, dan impliceert dit dat er geen dienstveband meer is en dat de
     * premiestorting is gestaakt.</i>
     */
    public float berekenPremie() {
        float premie = 0;
        if (this.parttimePercentage == 0f) {
            // Todo: Implementeer logging en log 'Geen premieafdracht i.v.m. actief werk.'
            return 0;
        }

        return premie;
    }

    /**
     *
     *
     */
    private boolean premieCompleet() {
        return true;
    }
}

