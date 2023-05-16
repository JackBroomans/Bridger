package com.befrank.casedeveloperjava;

import com.befrank.casedeveloperjava.configuration.AppVariabelenDeelnemer;
import com.befrank.casedeveloperjava.model.Deelnemer;
import com.befrank.casedeveloperjava.model.PremieDeelnemer;
import com.befrank.casedeveloperjava.repository.ContributionRepository;
import com.befrank.casedeveloperjava.repository.DeelnemerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <ol>
 *     <li>
 *         Toevoegen deelnemer
 *          <ol>
 *              <li>
 *                  Ontbrekende verplichte velden
 *                  <ol>
 *                      <li>Deelnemersnummer</li>
 *                      <li>Familienaam</li>
 *                      <li>Voornamen</li>
 *                      <li>Geboortedatum</li>
 *                      <li>Geslacht</li>
 *                      <li>Emailadres</li>
 *                      <li>Mobiel telefoonnummer</li>
 *                  </ol>
 *              </li>
 *              <li>
 *                  Ongeldig gespecificeerde velden
 *                  <ol>
 *                      <li>Formaat deelnemersnummer</li>
 *                      <li>Formaat emailadres</li>
 *                      <li>
 *                          Formaat telefoonnummer
 *                          <ol>
 *                              <li>Vast telefoonnummer</li>
 *                              <li>Mobiel telefoonnummer</li>
 *                          </ol>
 *                      </li>
 *                  </ol>
 *              </li>
 *              <li>Integriteit en kwaliteit data</li>
 *          </ol>
 *      </li>
 *
 *      <li>
 *          Ophalen persoonsgegevens uit database
 *      </li>
 *
 *      <li>
 *          Wijzigen van de gegevens van de deelnemer
 *      </li>
 *
 *      <li>
 *          Toevoegen van gerelateerde gegevenscomponenten
 *          <ol>
 *              <li>Adres</li>
 *              <li>Premiegegevens</li>
 *          </ol>
 *      </li>
 * </ol>
 */

@SpringBootTest
public class CaseDeveloperJavaApplicationRepositoryTest {

    @Autowired
    DeelnemerRepository repoParticipant;

    @Autowired
    ContributionRepository repoContribution;

    @Autowired

    AppVariabelenDeelnemer appVar;

    @Test
    void toevoegenNieuweDeelnemerTest() {

    }

    @Test
    // 2. Ophalen bestaande deelnemer uit database
    void ophalenBestaandeDeelnemerTest() {
        // 1.

    }


    @Test
    void toevoegenGerelateerdeInformatie() {
        // 1. Adres
        // 2. Premiegegevens
    }


    @Test
    void deelnemerRepositorytest() {

        Deelnemer nieuweDeelnemer = appVar.deelnemer();
        assertEquals(nieuweDeelnemer.getGeslacht(), appVar.deelnemer().getGeslacht());
        nieuweDeelnemer.setFamilienaam("Duck");
        nieuweDeelnemer.setVoornamen("Donald");
        nieuweDeelnemer.setInitialen("D.");
        nieuweDeelnemer.setPrefixTitels("dhr.");


        // Niet bestaand of ongeldig deelnemersnummer
        assertThrows(DataIntegrityViolationException.class, () -> { repoParticipant.save(nieuweDeelnemer); });
        nieuweDeelnemer.setDeelnemersnummer("20220416-00003");
        assertThrows(DataIntegrityViolationException.class, () -> { repoParticipant.save(nieuweDeelnemer); });
        // Niet bestaand emailadres
        assertThrows(DataIntegrityViolationException.class, () -> { repoParticipant.save(nieuweDeelnemer); });

        nieuweDeelnemer.setDeelnemersnummer("20230420");
        nieuweDeelnemer.setEmail("d.duck@duckstad.nl");
        nieuweDeelnemer.setTelefoonMobiel("06 88990011");

        // Todo: Variabele opnemen in resources-bestand om externe configuratie mogelijk te maken
        DateTimeFormatter KORTE_DATUM_FORMAAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate geboortedatum = LocalDate.parse("16-02-1966", KORTE_DATUM_FORMAAT);
        nieuweDeelnemer.setGeboortedatum(geboortedatum);

        // Happy flow toevoegen deelnemer
        repoParticipant.save(nieuweDeelnemer);
        assertEquals(repoParticipant.findByDeelnemersnummer("20230420").getEmail(), "d.duck@duckstad.nl");

        // ToDo: Different CRUD-operation to seperate tests.
        // Lees gegevens van de nieuwe deelnemer terug
        Deelnemer bestaandeDeelnemer = appVar.deelnemer();
        bestaandeDeelnemer = repoParticipant.findByEmail("d.duck@duckstad.nl");
        assertEquals(bestaandeDeelnemer.getEmail(), "d.duck@duckstad.nl");

        // corrigeer het deelnemersnummer
        bestaandeDeelnemer.setDeelnemersnummer("20230420-00001");
        repoParticipant.save(bestaandeDeelnemer);
        assertEquals(repoParticipant.findById(bestaandeDeelnemer.getId()).getDeelnemersnummer(), "20230420-00001");
        assertEquals(repoParticipant.findByDeelnemersnummer(bestaandeDeelnemer.getDeelnemersnummer()).getFamilienaam(), "Duck");


        // Test zoeken op niet bestaande of ongeldige argumenten
        assertDoesNotThrow( () -> repoParticipant.findByDeelnemersnummer("Ik-bestaa-niet") );
        // Todo: Test uitbreiden voor andere attributen

        // Todo: test bij het dubbel opvoeren van unieke velden


        // Voeg premiegegevens toe aan de nieuwe deelnemer
        PremieDeelnemer premie = new PremieDeelnemer();
        premie.setDeelnemer(bestaandeDeelnemer);
        premie.setFullTimeSalaris(51770);
        premie.setParttimePercentage(100);
        premie.setFranciseActueel(7354);
        premie.setPercentageBeschikbarePremie(5);
        premie.setDeelnemer(bestaandeDeelnemer);
        bestaandeDeelnemer.setPremieDeelnemer(premie);
        repoContribution.save(premie);
        repoParticipant.save(bestaandeDeelnemer);

        assertEquals(repoParticipant.findByDeelnemersnummer(bestaandeDeelnemer.getDeelnemersnummer()).getPremieDeelnemer().getFullTimeSalaris(), 51770) ;


        repoParticipant.delete(nieuweDeelnemer);
    }

}
