package com.befrank.casedeveloperjava;

import com.befrank.casedeveloperjava.model.Deelnemer;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

        DateTimeFormatter DATEFORMATTER= DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate geboortedatum = LocalDate.parse("16-02-1966", DATEFORMATTER);
        deelnemer.setGeboortedatum(geboortedatum.format(DATEFORMATTER));

        // Happy flow toevoegen deelnemer
        repository.save(deelnemer);



        repository.delete(deelnemer);






    }

}
