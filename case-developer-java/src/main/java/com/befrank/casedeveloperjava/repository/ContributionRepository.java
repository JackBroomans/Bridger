package com.befrank.casedeveloperjava.repository;

import com.befrank.casedeveloperjava.model.Deelnemer;
import com.befrank.casedeveloperjava.model.PremieDeelnemer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContributionRepository extends CrudRepository<PremieDeelnemer, Long> {
    List<PremieDeelnemer> findAll();
    PremieDeelnemer findById(long id);
    PremieDeelnemer findByParticipantId(long id);
}
