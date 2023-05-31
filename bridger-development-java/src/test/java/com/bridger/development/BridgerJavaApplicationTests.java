package com.bridger.development;

import com.bridger.development.model.entity_utility_classes.UtilityParticipant;
import com.bridger.development.model.Address;
import com.bridger.development.model.Participant;
import com.bridger.development.model.enums.Gender;
import com.bridger.development.repository.ParticipantRepository;
import com.bridger.development.util.Validations;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Random;

import static com.bridger.development.util.DatumTijdFuncties.getLeeftijd;
import static com.bridger.development.util.Validations.validateString;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BridgerJavaApplicationTests {

	@Autowired
	private ParticipantRepository repository;

	@Autowired
	private UtilityParticipant appVar;

	@Test
	public void classParticipantTest() {

		Participant participant = appVar.participant();

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
		participant.setEmail("");
		assertEquals("d.duck@duckstad.nl", participant.getEmail());

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
		assertFalse(Validations.validateBirthdate(birthdateBeforeAgeRule));
		participant.setBirthdate(birthdateBeforeAgeRule);
		assertNull(participant.getBirthdate());

		assertFalse(Validations.validateBirthdate(birthdateAfterAgeRule));
		participant.setBirthdate(birthdateAfterAgeRule);
		assertNull(participant.getBirthdate());

		/* Validation of a valid birthdate is successful and the birthdate is changed in the participant's property. */
		assertTrue(Validations.validateBirthdate(validBirthdate));
		participant.setBirthdate(validBirthdate);
		assertEquals(validBirthdate, participant.getBirthdate());
	}

	@Test
	public void calculateAgeValidationTest() {
		DateTimeFormatter shortDateFormat = DateTimeFormatter.ofPattern(appVar.SHORT_DATE_FORMAT);
		Participant participant = appVar.participant();

		/* Calculating the age without a specified birthdate */
		assertEquals(getLeeftijd(null), 0);
		assertFalse(Validations.validateBirthdate(null));

		/* Calculating the age with an invalid birthdate */
		participant.setBirthdate(LocalDate.parse("01-01-1900", shortDateFormat));
		assertFalse(Validations.validateBirthdate(participant.getBirthdate()));

		/* Calculating the age with an valid birthdate */
		participant.setBirthdate(LocalDate.parse("16-12-1958", shortDateFormat));
		assertTrue(Validations.validateBirthdate(participant.getBirthdate()));
	}

	@Test
	public void genderTest() {
		/* Searching and selecting the default gender */
		Gender gender = Gender.getByCode(appVar.DEFAULT_GENDER);
		assertEquals(true, gender.getCode().equals(appVar.DEFAULT_GENDER));

		/* Searching and selecting a gender based on it's code */
		ArrayList<String> invalidCodes =new ArrayList<>();
		invalidCodes.add(null);
		invalidCodes.add("");
		invalidCodes.add("XYZ");
		invalidCodes.add("X");
		invalidCodes.forEach(address -> {
			assertNull(Gender.getByCode(null));
		});
		assertEquals(Gender.getByCode("M"), Gender.MALE);

		/* On instantiation by conventional way the gender isn't set to the default setting. */
		assertNull(new Participant().getGender());

		/* On instantiation by configuration method the gender is set according the external default setting. */
		Participant particpant = appVar.participant();
		assertEquals(appVar.DEFAULT_GENDER, particpant.getGenderCode());

		/* When erasing the gender by assigning null to it, its refused and the gender remains the same. */
		particpant.setGender(null);
		assertEquals(appVar.DEFAULT_GENDER, particpant.getGenderCode());

		/* When assigning a new valid gender of a participant, the code of the gender is synchronized */
		Gender newGender = Gender.FEMALE;
		particpant.setGender(newGender);
		assertEquals(newGender, particpant.getGender());
		assertEquals(newGender.getCode(), particpant.getGenderCode());
	}

	@Test
	public void emailFormatValidationTest() {
		Participant participant = appVar.participant();

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
	public void participantNumberTest() {
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyMMdd-ssSSS");
		StringBuilder builder = new StringBuilder()
				.append(LocalDateTime.now().format(dateFormat))
				.append("-");
		assertFalse(builder.toString().matches(appVar.REGEX_PARTICIPANT_NUMBER));
		builder.append(new Random().nextInt(1000));
		assertTrue(builder.toString().matches(appVar.REGEX_PARTICIPANT_NUMBER));
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
		assertFalse(validateString(text.toString()));
		text.append("     ");
		assertFalse(validateString(text.toString()));
		text.delete(0, 255).append("Abc");
		assertTrue(validateString(text.toString()));
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
