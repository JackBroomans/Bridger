package com.befrank.casedeveloperjava;

import com.befrank.casedeveloperjava.configuration.AppVariablesParticipant;
import com.befrank.casedeveloperjava.model.Participant;
import com.befrank.casedeveloperjava.repository.ParticipantRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CaseDeveloperJavaControlerTest {

    @Autowired
    ParticipantRepository repository;

    @Autowired
    AppVariablesParticipant appVar;

    @Test
    public void berekenJaarlijksePremieTest() {

        Participant participant = appVar.participant();
        participant = repository.findByParticipantNumber("20220416-00001");
        double waardeHuidigeBeleggingen = repository.getSumInvestmentsParticipant(participant.getId());
        System.out.println();
        System.out.println("Waarde huidige beleggingen\t\t: " + waardeHuidigeBeleggingen);
        System.out.println("\nBruto jaarsalaris\t\t\t\t: " + participant.getParticipantPremium().getFullTimeSalaris());
        System.out.println("Francise\t\t\t\t\t\t: " + participant.getParticipantPremium().getFranciseActueel());
        System.out.println("Deeltijd percentage\t\t\t\t: " + participant.getParticipantPremium().getParttimePercentage());
        System.out.println
                ("Percentage beschikbare premie\t: " + participant.getParticipantPremium().getPercentageBeschikbarePremie());

        DeelnemerController controller = new DeelnemerController(repository);
        System.out.println
                ("\nJaarlijkse premie\t\t\t\t: "
                        + controller.berekenJaarlijksePremieDeelnemer("20220416-00001"));
        System.out.println();

    }
}
