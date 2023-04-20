package com.befrank.casedeveloperjava;

import com.befrank.casedeveloperjava.model.Deelnemer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface DeelnemerRepository extends JpaRepository<Deelnemer, Long> {
    List<Deelnemer> findAll();
    Deelnemer findById(long id);

    /**
     * <strong>findByEmail(<i>String</i>)</strong><br>
     * Zoek een deelnemer op basis van het email-adress.<br>
     * @param email Het email-adres van de deelnemer die wordt gezocht.
     * @return De deelnemer met het betreffende email-adres
     */
    Deelnemer findByEmail(String email);

    /**
     * <strong>findByEmail(<i>String</i>)</strong><br>
     * Zoek een deelnemer op basis van diens deelnemersnummers.<br>
     * @param deelnemersnummer Het deelnemersnummer van de deelnemer die wordt gezocht.
     * @return De deelnemer met het betreffende deelnemersnummer
     */
    Deelnemer findByDeelnemersnummer(String deelnemersnummer);

    /**
     * <strong>getSomBeleggingenDeelnemer(<i>long</i>)</strong><br>
     * Bepaald de waarde van alle actuele beleggingen van een bepaalde deelnemer gebaseerd op diens id.<br>
     * @param id Het unieke identificatienummer van de deelnemer.
     * @return Het totale actuele waarde van alle beleggening van de deelnemer
     */
    @Procedure(procedureName = "PSomBeleggingenDeelnemer")
    Float getSomBeleggingenDeelnemer(long id);
}
