package com.befrank.casedeveloperjava;

import com.befrank.casedeveloperjava.configuration.AppVariablesParticipant;
import com.befrank.casedeveloperjava.model.Address;
import com.befrank.casedeveloperjava.model.Participant;
import com.befrank.casedeveloperjava.model.enums.Gender;
import com.befrank.casedeveloperjava.repository.ParticipantRepository;
import com.befrank.casedeveloperjava.util.Validations;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static com.befrank.casedeveloperjava.util.DatumTijdFuncties.getLeeftijd;
import static com.befrank.casedeveloperjava.util.Validations.valideerTekenreeks;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CaseDeveloperJavaApplicationTests {

	@Autowired
	private ParticipantRepository repository;

	@Autowired
	private AppVariablesParticipant appVar;

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


		// Todo: Testen op parse errors van datums en tijden.


		// Todo: Testen toekennnen van standaard geslacht bij initialisatie;
		Participant participant = appVar.participant();

		// Todo: Adequaat testen van geboortedatum bij initialiatie

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
	public void birthdateValidationTest() {
		DateTimeFormatter shortDateFormat = DateTimeFormatter.ofPattern(appVar.SHORT_DATE_FORMAT);
		final LocalDate validBirthdate = LocalDate.parse("16-12-1978", shortDateFormat);
		final LocalDate birthdateBeforeAgeRule = LocalDate.now().minusYears(16);
		final LocalDate birthdateAfterAgeRule = LocalDate.now().plusYears(121);

		Participant participant = appVar.participant();

		/* No date assigned on instantiation, birthdate is null */
		assertNull(participant.getBirthdate());

		/* Invalid date specification throws an DateTimeParseException */
		assertThrows(DateTimeParseException.class,
				()->{
					participant.setBirthdate(LocalDate.parse("31-16-1978", shortDateFormat));
					//ex : objectName.thisMethodShoulThrowNullPointerExceptionForNullParameter(null);
				});

		/* Insertion of external age boundaries is successful. */
		assertTrue(appVar.MIN_AGE_PARTICIPANT > 0 && appVar.MAX_AGE_PARTICIPANT > 0);

		/* A birthdate outside the age limitations is not validated. The birthdate remains null. */
		assertFalse(Validations.valideerGeboortedatum(birthdateBeforeAgeRule));
		participant.setBirthdate(birthdateBeforeAgeRule);
		assertNull(participant.getBirthdate());

		assertFalse(Validations.valideerGeboortedatum(birthdateAfterAgeRule));
		participant.setBirthdate(birthdateAfterAgeRule);
		assertNull(participant.getBirthdate());

		/* Validation of a valid birthdate is successful and the birthdate is changed in the participant's property. */
		assertTrue(Validations.valideerGeboortedatum(validBirthdate));
		participant.setBirthdate(validBirthdate);
		assertEquals(validBirthdate, participant.getBirthdate());
	}

	@Test
	public void calculateAgeValidationTest() {
		DateTimeFormatter shortDateFormat = DateTimeFormatter.ofPattern(appVar.SHORT_DATE_FORMAT);
		Participant participant = appVar.participant();

		/* Calculating the age without a specified birthdate */
		assertEquals(getLeeftijd(null), 0);
		assertFalse(Validations.valideerGeboortedatum(null));

		/* Calculating the age with an invalid birthdate */
		participant.setBirthdate(LocalDate.parse("01-01-1900", shortDateFormat));
		assertFalse(Validations.valideerGeboortedatum(participant.getBirthdate()));

		/* Calculating the age with an valid birthdate */
		participant.setBirthdate(LocalDate.parse("16-12-1958", shortDateFormat));
		assertTrue(Validations.valideerGeboortedatum(participant.getBirthdate()));
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
