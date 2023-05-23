package com.befrank.casedeveloperjava;

import com.befrank.casedeveloperjava.configuration.AppVariablesParticipant;
import com.befrank.casedeveloperjava.model.Address;
import com.befrank.casedeveloperjava.model.Participant;
import com.befrank.casedeveloperjava.model.enums.Gender;
import com.befrank.casedeveloperjava.repository.ParticipantRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.befrank.casedeveloperjava.util.DatumTijdFuncties.getLeeftijd;
import static com.befrank.casedeveloperjava.util.Validaties.valideerTekenreeks;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CaseDeveloperJavaApplicationTests {

	@Autowired
	private ParticipantRepository repository;

	@Autowired
	private AppVariablesParticipant appVar;

	@Test
	void contextLoads() {
		assertNotNull(appVar.SHORT_DATE_FORMAT);
		assertNotNull(appVar.REGEX_EMAIL_ADDRESS);
	}

	@Test
	public void enumGenderTest() {

		/* Searching and selecting the default gender */
		Gender gender = Gender.getByCode(appVar.DEFAULT_GENDER);
		assertEquals(true, gender.getCode().equals(appVar.DEFAULT_GENDER));

		// Searching and selecting a gender based on it's code
		assertNull(Gender.getByCode(null));
		assertNull(Gender.getByCode(""));
		assertNull(Gender.getByCode("XYZ"));
		assertNull(Gender.getByCode("X"));
		assertEquals(Gender.getByCode("M"), Gender.MALE);
	}

	@Test
	public void classParticipantTest() {
		DateTimeFormatter shortDateFormat = DateTimeFormatter.ofPattern(appVar.SHORT_DATE_FORMAT);
		final LocalDate validBirthdate = LocalDate.parse("16-12-1978", shortDateFormat);
		final LocalDate invalidFormatBirthdate = LocalDate.parse("31-02-1978", shortDateFormat);
		final LocalDate birthdateBeforeAgeRule = LocalDate.now().minusYears(16);
		final LocalDate birthdateAfterAgeRule = LocalDate.now().plusYears(121);

		// Todo: Testen op parse errors van datums en tijden.


		// Todo: Testen toekennnen van standaard geslacht bij initialisatie;
		Participant participant = appVar.participant();

		// Todo: Adequaat testen van geboortedatum bij initialiatie
//		assertNull(deelnemer.getGeboortedatum());
		participant.setBirthdate(birthdateBeforeAgeRule);
		assertNull(participant.getBirthdate());
		participant.setBirthdate(birthdateAfterAgeRule);
		assertNull(participant.getBirthdate());
		participant.setBirthdate(validBirthdate);
		assertEquals(participant.getBirthdate(), validBirthdate);

		/* Calculating the age */
		assertEquals(getLeeftijd(null), 0);
		assertTrue(participant.calculateAge() > 0 && participant.calculateAge() < 120);

		participant.setFamilyName(null);
		assertNull(participant.getFamilyName());

		/* Happy flow assigning properties of a participant */
		participant.setFamilyName("Duck");
		participant.setSurNames("Donald");
		participant.setInitials("D.");
		participant.setPrefixTitles("dhr.");
		participant.setEmail("d.duck@duckstad.nl");

		assertEquals(participant.composeFullName(), "dhr. D. Duck");

		/* Erasing a mandatory property value results in keeping the original value */
		participant.setFamilyName(null);
		assertEquals("Duck", participant.getFamilyName());
		participant.setGender(null);
		assertEquals(appVar.DEFAULT_GENDER, participant.getGender().getCode());
		participant.setEmail("");
		assertEquals("d.duck@duckstad.nl", participant.getEmail());

		/* Synchronizing the code of the gender with the gender itself */
		participant.setGender(Gender.MALE);
		assertEquals(participant.getGender().getCode(), "M");

		/* Todo: Calculating the yearly premium of a participant */

	}

	@Test
	public void participantControllerTest() {

	}

	@Test
	public void classAddressTest() {
		/* Setting the default chosen country on instantiation of an address */
		Address address = new Address();
		address.setCountry(appVar.DEFAULT_COUNTRY);
		assertEquals(address.getCountry(), appVar.DEFAULT_COUNTRY);
		assertFalse(address.getCurrentActive());
	}

	// Todo: Verplaats unit-test naar test voor utiliteitsklassen
	@Test
	public void validateStringTest() {
		StringBuilder text = new StringBuilder();
		assertFalse(valideerTekenreeks(text.toString()));
		text.append("     ");
		assertFalse(valideerTekenreeks(text.toString()));
		text.delete(0, 255).append("Abc");
		assertTrue(valideerTekenreeks(text.toString()));
	}

	@Test
	public void classInvestmentTest() {

		/* Fetching and processing data  concerning a participant */
		long valsId = 0L;
		assertNull(repository.findById(valsId));
		assertNull(repository.findByEmail("email.adres.bestaat@niet"));
		assertNull(repository.getSumInvestmentsParticipant(valsId));

		long validId = 2L;
		Participant participant = repository.findByEmail("petteflet@flatgebouw.nl");
		assertEquals(participant.getId(), validId);

		assertEquals(40441, repository.getSumInvestmentsParticipant(1));
	}

}
