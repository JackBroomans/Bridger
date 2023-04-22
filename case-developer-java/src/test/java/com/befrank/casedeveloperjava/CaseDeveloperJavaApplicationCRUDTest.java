package com.befrank.casedeveloperjava;

import com.befrank.casedeveloperjava.model.Deelnemer;
import com.befrank.casedeveloperjava.model.PremieDeelnemer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CaseDeveloperJavaApplicationCRUDTest {

    @Autowired
    DeelnemerRepository repository;

    @Test
    void deelnemerCRUDtest() {

        Deelnemer deelnemer = new Deelnemer();
        deelnemer.setFamilienaam("Duck");
        deelnemer.setVoornamen("Donald");
        deelnemer.setInitialen("D.");
        deelnemer.setPrefixTitels("dhr.");


        // Niet bestaand of ongeldig deelnemersnummer
        assertThrows(DataIntegrityViolationException.class, () -> { repository.save(deelnemer); });
        deelnemer.setDeelnemersnummer("20220416-00003");
        assertThrows(DataIntegrityViolationException.class, () -> { repository.save(deelnemer); });
        // Niet bestaand emailadres
        assertThrows(DataIntegrityViolationException.class, () -> { repository.save(deelnemer); });

        deelnemer.setDeelnemersnummer("20230420");
        deelnemer.setEmail("d.duck@duckstad.nl");
        deelnemer.setTelefoonMobiel("06 88990011");

        // Todo: Variabele opnemen in resources-bestand om externe configuratie mogelijk te maken
        DateTimeFormatter KORTE_DATUM_FORMAAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate geboortedatum = LocalDate.parse("16-02-1966", KORTE_DATUM_FORMAAT);
        deelnemer.setGeboortedatum(geboortedatum.format(KORTE_DATUM_FORMAAT));

        // Happy flow toevoegen deelnemer
        repository.save(deelnemer);
        assertEquals(repository.findByDeelnemersnummer("20230420").getEmail(), "d.duck@duckstad.nl");

        // Lees gegevens van de nieuwe deelnemer terug
        Deelnemer nieuweDeelnemer = new Deelnemer();
        nieuweDeelnemer = repository.findByEmail("d.duck@duckstad.nl");
        assertEquals(nieuweDeelnemer.getEmail(), "d.duck@duckstad.nl");

        // corrigeer het deelnemersnummer
        nieuweDeelnemer.setDeelnemersnummer("20230420-00001");
        repository.save(nieuweDeelnemer);
        assertEquals(repository.findById(nieuweDeelnemer.getId()).getDeelnemersnummer(), "20230420-00001");
        assertEquals(repository.findByDeelnemersnummer(nieuweDeelnemer.getDeelnemersnummer()).getFamilienaam(), "Duck");


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
        premie.setDeelnemer(nieuweDeelnemer);
        nieuweDeelnemer.setPremieDeelnemer(premie);


        repository.save(nieuweDeelnemer);

        assertEquals(repository.findByDeelnemersnummer(nieuweDeelnemer.getDeelnemersnummer()).getPremieDeelnemer().getFullTimeSalaris(), 51770) ;


        repository.delete(deelnemer);
    }

}
