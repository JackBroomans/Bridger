package com.bridger.development.repository;

import com.bridger.development.model.ParticipantPremium;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContributionRepository extends CrudRepository<ParticipantPremium, Long> {
    List<ParticipantPremium> findAll();
    ParticipantPremium findById(long id);
    ParticipantPremium findByParticipantId(long id);
}
