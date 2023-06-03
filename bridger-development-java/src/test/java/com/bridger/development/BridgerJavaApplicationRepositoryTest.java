package com.bridger.development;

import com.bridger.development.model.Participant;
import com.bridger.development.model.UserAccount;

import com.bridger.development.model.entity_utility_classes.UtilityGeneral;
import com.bridger.development.model.entity_utility_classes.UtilityParticipant;
import com.bridger.development.model.entity_utility_classes.UtilityUserAccount;

import com.bridger.development.repository.ContributionRepository;
import com.bridger.development.repository.ParticipantRepository;
import com.bridger.development.repository.UseraccountRepository;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@SpringBootTest
public class BridgerJavaApplicationRepositoryTest {
    @Autowired
    UseraccountRepository repoUserAccount;

    @Autowired
    ParticipantRepository repoParticipant;

    @Autowired
    ContributionRepository repoContribution;

    @Autowired
    UtilityGeneral appVarGeneral;

    @Autowired
    UtilityUserAccount appVarUserAccount;

    @Autowired
    UtilityParticipant appVarParticipant;

    @Test
    void addNewParticipantTest() {

    }

    @Test
    // 2. Search and get the participant from the database, based on different arguments.
    void getParticipantFromDatabaseTest() {
        // 1.

    }


    @Test
    void addRelatedInformation() {
        // 1. Address
    }


    @Test
    void participantRepositoryTest() {

        /* First we need a user account to link the participant to and fill te required properties. */
        UserAccount userAccount = appVarUserAccount.userAccount();
        userAccount.setUserName(appVarUserAccount.PREDEFINED_USER_NAME);
        userAccount.setPassword(appVarUserAccount.PREDEFINED_USER_PASSWORD);

        Participant nieuweParticipant = appVarParticipant.participant();
        assertEquals(nieuweParticipant.getGender(), appVarParticipant.participant().getGender());
        assertTrue(nieuweParticipant.getParticipantNumber().matches(appVarParticipant.REGEX_PARTICIPANT_NUMBER));

        nieuweParticipant.setFamilyName("Duck");
        nieuweParticipant.setSurNames("Donald");
        nieuweParticipant.setInitials("D.");
        nieuweParticipant.setPrefixTitles("dhr.");


        /* Non-existing or invalid content in the participant instance, will throw exceptions. */
        assertThrows(DataIntegrityViolationException.class, () -> { repoParticipant.save(nieuweParticipant); });
        nieuweParticipant.setParticipantNumber("20220416-00003");
        assertThrows(DataIntegrityViolationException.class, () -> { repoParticipant.save(nieuweParticipant); });
        // Niet bestaand emailadres
        assertThrows(DataIntegrityViolationException.class, () -> { repoParticipant.save(nieuweParticipant); });

        nieuweParticipant.setParticipantNumber("20230420");
        nieuweParticipant.setEmail("d.duck@duckstad.nl");
        nieuweParticipant.setCellphone("06 88990011");


        DateTimeFormatter shortDateFormat = DateTimeFormatter.ofPattern(appVarGeneral.SHORT_DATE_FORMAT);
        LocalDate birthdate = LocalDate.parse("16-02-1966", shortDateFormat);
        nieuweParticipant.setBirthdate(birthdate);

        /* Before we save the participant we must set the save the user account and link the participant */
        repoUserAccount.save(userAccount);
        nieuweParticipant.setUseracccountId(userAccount.getId());
        repoParticipant.save(nieuweParticipant);
        assertEquals(repoParticipant.findByParticipantNumber("20230420").getEmail(), "d.duck@duckstad.nl");

        /* Search and get the newly added participant from the database on the email-address of the participant */
        Participant bestaandeParticipant = appVarParticipant.participant();
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

        final long countBefore = repoParticipant.count();
        repoUserAccount.delete(userAccount);

        /* As a result of the cascaded delete, the participant entry is also removed */
        assertTrue(repoParticipant.count() < countBefore);
    }

    @Test
    public void resolveSumOfPower2NumbersTest() {
        ArrayList<Integer> factors = new ArrayList<>();
        int runningSum = 564;
        int power = 0;

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
