package com.befrank.casedeveloperjava.repository;

import com.befrank.casedeveloperjava.model.Deelnemer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeelnemerRepository extends CrudRepository<Deelnemer, Long> {
    List<Deelnemer> findAll();
    Deelnemer findById(long id);

    Deelnemer findByEmail(String email);

    /**
     * <strong>findByDeelnemersnummer(<i>String</i>)</strong><br>
     * Zoek een deelnemer op basis van diens deelnemersnummers.<br>
     * @param deelnemersnummer Het deelnemersnummer van de deelnemer die wordt gezocht.
     * @return De deelnemer met het betreffende deelnemersnummer
     */
    Deelnemer findByDeelnemersnummer(String deelnemersnummer);

    @Procedure(procedureName = "PSomBeleggingenDeelnemer")
    Float getSomBeleggingenDeelnemer(long id);
}
