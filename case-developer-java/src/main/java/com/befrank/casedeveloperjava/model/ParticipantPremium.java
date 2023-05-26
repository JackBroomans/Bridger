package com.befrank.casedeveloperjava.model;

import jakarta.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static jakarta.persistence.GenerationType.IDENTITY;

/**
 * <strong>ParticipantPremium</strong><br>
 * This entity class describes the properties of the actual premium the participant must contribute in the pension
 * fund according to the associated database table.<br>
 * This premium ia calculated from the property values by a (non persistable) method:
 * <ul>
 *     <li>
 *         <strong>composeFullName()</strong>
 *     </li>
 * </ul>
 */
@Component
@Entity
@Table(name = "participantpremium")
public class ParticipantPremium {

    private static final Logger logger = LoggerFactory.getLogger(ParticipantPremium.class);

    // Todo: Vaste (actueel) franchise elders vastleggen voor externe premie configuratie
    @Transient
    private static final float FRANCISE_ACTUEEL = 15.599f;
    // Todo: Vaste (actueel) bechikbare premie percentage elders vastleggen voor externe premie configuratie
    @Transient
    private static final float BESCHIBARE_PREMIE_PERCENTAGE_ACTUEEL = 5f;


    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn()
//    @JoinColumn(name = "id", nullable = false, unique = true)
    private Participant participant;
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

    public ParticipantPremium() {
        // Todo: waardeHuidigebelegging ophalen uit database
//        this.waardeHuidigeBeleggingen = .....;
    }


    // Getters en Setters
    public Participant getParticipant() {
        return this.participant;
    }
    public void setParticipant(Participant participant) {
        this.participant = participant;
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
    public float calculatePremium() {
        float premie = 0;
        if (this.parttimePercentage == 0f) {
            logger.info("Contribution not applicable because no active employer assigned.");
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

