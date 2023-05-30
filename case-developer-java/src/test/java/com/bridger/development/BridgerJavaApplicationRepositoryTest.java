package com.bridger.development;

import com.bridger.development.model.entity_utility_classes.UtilityParticipant;
import com.bridger.development.model.Participant;
import com.bridger.development.repository.ContributionRepository;
import com.bridger.development.repository.ParticipantRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class BridgerJavaApplicationRepositoryTest {

    @Autowired
    ParticipantRepository repoParticipant;

    @Autowired
    ContributionRepository repoContribution;

    @Autowired
    UtilityParticipant appVar;

    @Test
    void toevoegenNieuweDeelnemerTest() {

    }

    @Test
    // 2. Ophalen bestaande deelnemer uit database
    void ophalenBestaandeDeelnemerTest() {
        // 1.

    }


    @Test
    void toevoegenGerelateerdeInformatie() {
        // 1. Adres
        // 2. Premiegegevens
    }


    @Test
    void deelnemerRepositorytest() {

        Participant nieuweParticipant = appVar.participant();
        assertEquals(nieuweParticipant.getGender(), appVar.participant().getGender());
        assertTrue(nieuweParticipant.getParticipantNumber().matches(appVar.REGEX_PARTICIPANT_NUMBER));

        nieuweParticipant.setFamilyName("Duck");
        nieuweParticipant.setSurNames("Donald");
        nieuweParticipant.setInitials("D.");
        nieuweParticipant.setPrefixTitles("dhr.");


        // Niet bestaand of ongeldig deelnemersnummer
        assertThrows(DataIntegrityViolationException.class, () -> { repoParticipant.save(nieuweParticipant); });
        nieuweParticipant.setParticipantNumber("20220416-00003");
        assertThrows(DataIntegrityViolationException.class, () -> { repoParticipant.save(nieuweParticipant); });
        // Niet bestaand emailadres
        assertThrows(DataIntegrityViolationException.class, () -> { repoParticipant.save(nieuweParticipant); });

        nieuweParticipant.setParticipantNumber("20230420");
        nieuweParticipant.setEmail("d.duck@duckstad.nl");
        nieuweParticipant.setCellphone("06 88990011");

        // Todo: Variabele opnemen in resources-bestand om externe configuratie mogelijk te maken
        DateTimeFormatter KORTE_DATUM_FORMAAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate geboortedatum = LocalDate.parse("16-02-1966", KORTE_DATUM_FORMAAT);
        nieuweParticipant.setBirthdate(geboortedatum);

        // Happy flow toevoegen deelnemer
        repoParticipant.save(nieuweParticipant);
        assertEquals(repoParticipant.findByParticipantNumber("20230420").getEmail(), "d.duck@duckstad.nl");

        // ToDo: Different CRUD-operation to seperate tests.
        // Lees gegevens van de nieuwe deelnemer terug
        Participant bestaandeParticipant = appVar.participant();
        bestaandeParticipant = repoParticipant.findByEmail("d.duck@duckstad.nl");
        assertEquals(bestaandeParticipant.getEmail(), "d.duck@duckstad.nl");

        // corrigeer het deelnemersnummer
        bestaandeParticipant.setParticipantNumber("20230420-00001");
        repoParticipant.save(bestaandeParticipant);
        assertEquals(repoParticipant.findById(bestaandeParticipant.getId()).getParticipantNumber(), "20230420-00001");
        assertEquals(repoParticipant.findByParticipantNumber(bestaandeParticipant.getParticipantNumber()).getFamilyName(), "Duck");


        // Test zoeken op niet bestaande of ongeldige argumenten
        assertDoesNotThrow( () -> repoParticipant.findByParticipantNumber("Ik-bestaa-niet") );
        // Todo: Test uitbreiden voor andere attributen

        // Todo: test bij het dubbel opvoeren van unieke velden


        // Voeg premiegegevens toe aan de nieuwe deelnemer
//        ParticipantPremium premie = new ParticipantPremium();
//        premie.setDeelnemer(bestaandeParticipant);
//        premie.setFullTimeSalaris(51770);
//        premie.setParttimePercentage(100);
//        premie.setFranciseActueel(7354);
//        premie.setPercentageBeschikbarePremie(5);
//        premie.setDeelnemer(bestaandeParticipant);
//        bestaandeParticipant.setParticipantPremium(premie);
//        repoContribution.save(premie);
//        repoParticipant.save(bestaandeParticipant);

//        assertEquals(repoParticipant.findByParticipantNumber(bestaandeParticipant.getParticipantNumber()).getParticipantPremium().getFullTimeSalaris(), 51770) ;


        repoParticipant.delete(nieuweParticipant);
    }

    @Test
    public void resolveSumOfPower2NumbersTest() {
        ArrayList<Integer> factors = new ArrayList<Integer>();
        Integer runningSum = 564;
        Integer power = 0;

        while (runningSum > 0) {
            power = binaryLog2Base(runningSum);
            factors.add(power);
            runningSum -= (int) Math.pow(2, power);
        }

        System.out.println(factors);
    }

    private static int binaryLog2Base( int bits ) // returns 0 for bits=0
    {

        int log = 0;

        if( ( bits & 0xffff0000 ) != 0 ) { bits >>>= 16; log = 16; }
        if( bits >= 256 ) { bits >>>= 8; log += 8; }
        if( bits >= 16  ) { bits >>>= 4; log += 4; }
        if( bits >= 4   ) { bits >>>= 2; log += 2; }
        return log + ( bits >>> 1 );
    }

}
