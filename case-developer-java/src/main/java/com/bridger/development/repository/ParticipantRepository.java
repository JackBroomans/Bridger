package com.bridger.development.repository;

import com.bridger.development.model.Participant;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipantRepository extends CrudRepository<Participant, Long> {
    List<Participant> findAll();


    Participant findById(long id);

    Participant findByEmail(String email);

    /**
     * <strong>findByParticipantNumber(<i>String</i>)</strong><br>
     * Searches a participant based on the participant number.
     * @param participantNumber The number vof the participant search for.
     * @return A participant instance
     */
    Participant findByParticipantNumber(String participantNumber);

    @Procedure(procedureName = "sp_SumInvestmentsParticipant")
    Float getSumInvestmentsParticipant(long id);
}
