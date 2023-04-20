package com.befrank.casedeveloperjava;

import com.befrank.casedeveloperjava.model.Deelnemer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

        // Lees gegevens van de nieuwe delnemer terug
        Deelnemer nieuweDeelnemer = new Deelnemer();
        nieuweDeelnemer = repository.findByEmail("d.duck@duckstad.nl");
        assertEquals(nieuweDeelnemer.getEmail(), "d.duck@duckstad.nl");

        // corrigeer het deelnemersnummer
        nieuweDeelnemer.setDeelnemersnummer("20230420-00001");
        repository.save(nieuweDeelnemer);
        assertEquals(repository.findById(deelnemer.getId()).getDeelnemersnummer(), "20230420-00001");
        assertEquals(repository.findByDeelnemersnummer(nieuweDeelnemer.getDeelnemersnummer()).getFamilienaam(), "Duck");

        repository.delete(deelnemer);
    }

}
