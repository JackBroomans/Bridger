package com.bridger.development;

import com.bridger.development.model.entity_utility_classes.UtilityParticipant;
import com.bridger.development.repository.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CaseDeveloperJavaControlerTest {

    @Autowired
    ParticipantRepository repository;

    @Autowired
    UtilityParticipant appVar;

//    @Test
//    public void berekenJaarlijksePremieTest() {
//
//        Participant participant = appVar.participant();
//        participant = repository.findByParticipantNumber("20220416-00001");
//        double waardeHuidigeBeleggingen = repository.getSumInvestmentsParticipant(participant.getId());
//        System.out.println();
//        System.out.println("Waarde huidige beleggingen\t\t: " + waardeHuidigeBeleggingen);
//        System.out.println("\nBruto jaarsalaris\t\t\t\t: " + participant.getParticipantPremium().getFullTimeSalaris());
//        System.out.println("Francise\t\t\t\t\t\t: " + participant.getParticipantPremium().getFranciseActueel());
//        System.out.println("Deeltijd percentage\t\t\t\t: " + participant.getParticipantPremium().getParttimePercentage());
//        System.out.println
//                ("Percentage beschikbare premie\t: " + participant.getParticipantPremium().getPercentageBeschikbarePremie());
//
//        ParticipantController controller = new ParticipantController(repository);
//        System.out.println
//                ("\nJaarlijkse premie\t\t\t\t: "
//                        + controller.berekenJaarlijksePremieDeelnemer("20220416-00001"));
//        System.out.println();
//
//    }
}
