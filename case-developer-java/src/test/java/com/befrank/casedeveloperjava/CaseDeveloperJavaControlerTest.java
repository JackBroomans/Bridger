package com.befrank.casedeveloperjava;

import com.befrank.casedeveloperjava.model.Deelnemer;
import com.befrank.casedeveloperjava.repository.DeelnemerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CaseDeveloperJavaControlerTest {

    @Autowired
    DeelnemerRepository repository;

    @Test
    public void berekenJaarlijksePremieTest() {

        Deelnemer deelnemer = new Deelnemer();
        deelnemer = repository.findByDeelnemersnummer("20220416-00001");
        double waardeHuidigeBeleggingen = repository.getSomBeleggingenDeelnemer(deelnemer.getId());
        System.out.println();
        System.out.println("Waarde huidige beleggingen\t\t: " + waardeHuidigeBeleggingen);
        System.out.println("\nBruto jaarsalaris\t\t\t\t: " + deelnemer.getPremieDeelnemer().getFullTimeSalaris());
        System.out.println("Francise\t\t\t\t\t\t: " + deelnemer.getPremieDeelnemer().getFranciseActueel());
        System.out.println("Deeltijd percentage\t\t\t\t: " + deelnemer.getPremieDeelnemer().getParttimePercentage());
        System.out.println
                ("Percentage beschikbare premie\t: " + deelnemer.getPremieDeelnemer().getPercentageBeschikbarePremie());

        DeelnemerController controller = new DeelnemerController(repository);
        System.out.println
                ("\nJaarlijkse premie\t\t\t\t: "
                        + controller.berekenJaarlijksePremieDeelnemer("20220416-00001"));
        System.out.println();

    }
}
