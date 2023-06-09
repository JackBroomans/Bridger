package com.bridger.development;

import com.bridger.development.model.Address;
import com.bridger.development.model.Participant;
import com.bridger.development.model.entity_utility_classes.UtilityParticipant;
import com.bridger.development.repository.ParticipantRepository;
import com.bridger.development.util.DateTimeFunctions;
import com.bridger.development.util.validation.ParticipantValidation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BridgerJavaApplicationTests {

	@Autowired
	private ParticipantRepository repository;

	@Autowired
	private UtilityParticipant appVarUtilityParticipant;

	private final DateTimeFunctions dateTimeFunctions = new DateTimeFunctions();

	@Test
	public void classParticipantTest() {

		Participant participant = appVarUtilityParticipant.participant();

		participant.setFamilyName(null);
		assertNull(participant.getFamilyName());

		/* Happy flow assigning properties of a participant */
		participant.setFamilyName("Duck");
		participant.setSurNames("Donald");
		participant.setInitials("D.");
		participant.setPrefixTitles("dhr.");
		participant.setEmail("d.duck@duckstad.nl");

		assertEquals(appVarUtilityParticipant.composeFullName(participant), "dhr. D. Duck");

		/* Erasing a mandatory property value results in keeping the original value */
		participant.setFamilyName(null);
		assertEquals("Duck", participant.getFamilyName());
		participant.setEmail("");
		assertEquals("d.duck@duckstad.nl", participant.getEmail());

		System.out.println(participant);

	}

	@Test
	public void birthdateValidationTest() {
		DateTimeFormatter shortDateFormat = DateTimeFormatter.ofPattern(appVarUtilityParticipant.SHORT_DATE_FORMAT);
		final LocalDate validBirthdate = LocalDate.parse("16-12-1978", shortDateFormat);
		final LocalDate birthdateBeforeAgeRule = LocalDate.now().minusYears(16);
		final LocalDate birthdateAfterAgeRule = LocalDate.now().plusYears(121);

		Participant participant = appVarUtilityParticipant.participant();

		/* No date assigned on instantiation, birthdate is null */
		assertNull(participant.getBirthdate());

		/* Invalid date specification throws an DateTimeParseException */
		assertThrows(DateTimeParseException.class,
				()->{
					participant.setBirthdate(LocalDate.parse("31-16-1978", shortDateFormat));
					//ex : objectName.thisMethodShoulThrowNullPointerExceptionForNullParameter(null);
				});

		/* Insertion of external age boundaries is successful. */
		assertTrue(appVarUtilityParticipant.MIN_AGE_PARTICIPANT > 0 && appVarUtilityParticipant.MAX_AGE_PARTICIPANT > 0);

		/* A birthdate outside the age limitations is not validated. The birthdate remains null. */
		assertFalse(ParticipantValidation.validateBirthdate(birthdateBeforeAgeRule));
		participant.setBirthdate(birthdateBeforeAgeRule);
		assertNull(participant.getBirthdate());

		assertFalse(ParticipantValidation.validateBirthdate(birthdateAfterAgeRule));
		participant.setBirthdate(birthdateAfterAgeRule);
		assertNull(participant.getBirthdate());

		/* Validation of a valid birthdate is successful and the birthdate is changed in the participant's property. */
		assertTrue(ParticipantValidation.validateBirthdate(validBirthdate));
		participant.setBirthdate(validBirthdate);
		assertEquals(validBirthdate, participant.getBirthdate());
	}

	@Test
	public void calculateAgeValidationTest() {
		DateTimeFormatter shortDateFormat = DateTimeFormatter.ofPattern(appVarUtilityParticipant.SHORT_DATE_FORMAT);
		Participant participant = appVarUtilityParticipant.participant();

		/* Calculating the age without a specified birthdate */
		assertEquals(dateTimeFunctions.calculateAge(null), 0);
		assertFalse(ParticipantValidation.validateBirthdate(null));

		/* Calculating the age with an invalid birthdate */
		participant.setBirthdate(LocalDate.parse("01-01-1900", shortDateFormat));
		assertFalse(ParticipantValidation.validateBirthdate(participant.getBirthdate()));

		/* Calculating the age with an valid birthdate */
		participant.setBirthdate(LocalDate.parse("16-12-1958", shortDateFormat));
		assertTrue(ParticipantValidation.validateBirthdate(participant.getBirthdate()));
	}

	@Test
	public void emailFormatValidationTest() {
		Participant participant = appVarUtilityParticipant.participant();

		/* Invalid email-adresses are refused and nothing is changed. */
		ArrayList<String> invalidEmailAddresses = new ArrayList<>();
		invalidEmailAddresses.add("username.username");
		invalidEmailAddresses.add("@domain.com");
		invalidEmailAddresses.add("username.@domain.com");
		invalidEmailAddresses.add(".user.name@domain.com");
		invalidEmailAddresses.add("username@.com");
		invalidEmailAddresses.forEach(address -> {
			participant.setEmail(address);
			assertNull(participant.getEmail());
		});
	}

	@Test
	public void classAddressTest() {
		/* Setting the default chosen country on instantiation of an address */
		Address address = new Address();
		address.setCountry(appVarUtilityParticipant.DEFAULT_COUNTRY);
		assertEquals(address.getCountry(), appVarUtilityParticipant.DEFAULT_COUNTRY);
		assertFalse(address.getCurrentActive());
	}

	@Test
	public void classInvestmentTest() {

		Participant participant = appVarUtilityParticipant.participant();

		/* Fetching and processing data concerning a participant */
		long valsId = 0L;
		assertNull(repository.findById(valsId));
		assertNull(repository.findByEmail("email.adres.bestaat@niet"));
		assertNull(repository.getSumInvestmentsParticipant(valsId));

		long validId = 2L;
		participant = repository.findByEmail("petteflet@flatgebouw.nl");
		assertEquals(participant.getId(), validId);

		assertEquals(40441, repository.getSumInvestmentsParticipant(1));
	}

}
