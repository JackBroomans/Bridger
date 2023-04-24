package com.befrank.casedeveloperjava;

import com.befrank.casedeveloperjava.model.Deelnemer;
import com.befrank.casedeveloperjava.model.PremieDeelnemer;
import com.befrank.casedeveloperjava.repository.DeelnemerRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
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
    DeelnemerRepository repository;

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

        Deelnemer nieuweDeelnemer = new Deelnemer();
        nieuweDeelnemer.setFamilienaam("Duck");
        nieuweDeelnemer.setVoornamen("Donald");
        nieuweDeelnemer.setInitialen("D.");
        nieuweDeelnemer.setPrefixTitels("dhr.");


        // Niet bestaand of ongeldig deelnemersnummer
        assertThrows(DataIntegrityViolationException.class, () -> { repository.save(nieuweDeelnemer); });
        nieuweDeelnemer.setDeelnemersnummer("20220416-00003");
        assertThrows(DataIntegrityViolationException.class, () -> { repository.save(nieuweDeelnemer); });
        // Niet bestaand emailadres
        assertThrows(DataIntegrityViolationException.class, () -> { repository.save(nieuweDeelnemer); });

        nieuweDeelnemer.setDeelnemersnummer("20230420");
        nieuweDeelnemer.setEmail("d.duck@duckstad.nl");
        nieuweDeelnemer.setTelefoonMobiel("06 88990011");

        // Todo: Variabele opnemen in resources-bestand om externe configuratie mogelijk te maken
        DateTimeFormatter KORTE_DATUM_FORMAAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate geboortedatum = LocalDate.parse("16-02-1966", KORTE_DATUM_FORMAAT);
        nieuweDeelnemer.setGeboortedatum(geboortedatum.format(KORTE_DATUM_FORMAAT));

        // Happy flow toevoegen deelnemer
        repository.save(nieuweDeelnemer);
        assertEquals(repository.findByDeelnemersnummer("20230420").getEmail(), "d.duck@duckstad.nl");

        // Lees gegevens van de nieuwe deelnemer terug
        Deelnemer bestaandeDeelnemer = new Deelnemer();
        bestaandeDeelnemer = repository.findByEmail("d.duck@duckstad.nl");
        assertEquals(bestaandeDeelnemer.getEmail(), "d.duck@duckstad.nl");

        // corrigeer het deelnemersnummer
        bestaandeDeelnemer.setDeelnemersnummer("20230420-00001");
        repository.save(bestaandeDeelnemer);
        assertEquals(repository.findById(bestaandeDeelnemer.getId()).getDeelnemersnummer(), "20230420-00001");
        assertEquals(repository.findByDeelnemersnummer(bestaandeDeelnemer.getDeelnemersnummer()).getFamilienaam(), "Duck");


        // Test zoeken op niet bestaande of ongeldige argumenten
        assertDoesNotThrow( () -> repository.findByDeelnemersnummer("Ik-bestaa-niet") );
        // Todo: Test uitbreiden voor andere attributen

        // Todo: test bij het dubbel opvoeren van unieke velden


        // Voeg premiegegevens toe aan de nieuwe deelnemer
        PremieDeelnemer premie = new PremieDeelnemer();
        premie.setFullTimeSalaris(51770);
        premie.setParttimePercentage(100);
        premie.setFranciseActueel(7354);
        premie.setPercentageBeschikbarePremie(5);
        premie.setDeelnemer(bestaandeDeelnemer);
        bestaandeDeelnemer.setPremieDeelnemer(premie);


        repository.save(bestaandeDeelnemer);

        assertEquals(repository.findByDeelnemersnummer(bestaandeDeelnemer.getDeelnemersnummer()).getPremieDeelnemer().getFullTimeSalaris(), 51770) ;


        repository.delete(nieuweDeelnemer);
    }

}
