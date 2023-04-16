package com.befrank.casedeveloperjava;

import com.befrank.casedeveloperjava.model.Deelnemer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface DeelnemerRepository extends CrudRepository<Deelnemer, Long> {
    List<Deelnemer> findAll();
    Deelnemer findById(long id);
}
