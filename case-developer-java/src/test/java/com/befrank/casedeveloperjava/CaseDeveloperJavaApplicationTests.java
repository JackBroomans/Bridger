package com.befrank.casedeveloperjava;

import com.befrank.casedeveloperjava.configuration.AppVariabelenDeelnemer;
import com.befrank.casedeveloperjava.model.Adres;
import com.befrank.casedeveloperjava.model.Deelnemer;
import com.befrank.casedeveloperjava.model.enums.Gender;
import com.befrank.casedeveloperjava.repository.DeelnemerRepository;
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
	private DeelnemerRepository repository;

	@Autowired
	private AppVariabelenDeelnemer appVar;

	@Test
	void contextLoads() {
		assertNotNull(appVar.korteDatumNotatie);
		assertNotNull(appVar.regexEmail);
	}

	@Test
	public void enumGeslachtTest() {

		// Test zoeken en selecteren als standaard ingestelde optie
		Gender gender = Gender.getByCode(appVar.standaardGeslacht);
		assertTrue(gender.getCode().equals(appVar.standaardGeslacht));

		// Test zoeken van een geslacht op basis van haar code
		assertNull(Gender.getByCode(null));
		assertNull(Gender.getByCode(""));
		assertNull(Gender.getByCode("XYZ"));
		assertNull(Gender.getByCode("X"));
		// Happy flow zoeken op code
		assertEquals(Gender.getByCode("M"), Gender.MALE);
	}

	@Test
	public void classDeelnemerTest() {
		DateTimeFormatter KORTE_DATUM_FORMAAT = DateTimeFormatter.ofPattern(appVar.korteDatumNotatie);
		final LocalDate geboortedatumGeldig = LocalDate.parse("16-12-1978", KORTE_DATUM_FORMAAT);
		final LocalDate geboortedatumOngeldigFormaat = LocalDate.parse("31-02-1978", KORTE_DATUM_FORMAAT);
		final LocalDate geboortedatumTeJong = LocalDate.now().minusYears(16);
		final LocalDate geboortedatumTeOud = LocalDate.now().plusYears(121);

		// Todo: Testen op parse errors van datums en tijden.

		// Todo: Testen toekennnen van standaard geslacht bij initialisatie;
		Deelnemer deelnemer = appVar.deelnemer();

		// Todo: Adequaat testen van geboortedatum bij initialiatie
//		assertNull(deelnemer.getGeboortedatum());
		deelnemer.setGeboortedatum(geboortedatumTeJong);
		assertNull(deelnemer.getGeboortedatum());
		deelnemer.setGeboortedatum(geboortedatumTeOud);
		assertNull(deelnemer.getGeboortedatum());
		// Happy flow toekennen geboortedatum
		deelnemer.setGeboortedatum(geboortedatumGeldig);
		assertEquals(deelnemer.getGeboortedatum(), geboortedatumGeldig);

		// Test bepalen van de leeftijd op basis van geboortedatum
		assertEquals(getLeeftijd(null), 0);
		assertTrue(deelnemer.getLeeftijd() > 0 && deelnemer.getLeeftijd() < 120);

		deelnemer.setFamilienaam(null);
		assertNull(deelnemer.getFamilienaam());

		// Test happy flow specificaties deelnemer
		deelnemer.setFamilienaam("Duck");
		deelnemer.setVoornamen("Donald");
		deelnemer.setInitialen("D.");
		deelnemer.setPrefixTitels("dhr.");
		deelnemer.setEmail("d.duck@duckstad.nl");

		assertEquals(deelnemer.getCorrespondentieRegel(), "dhr. D. Duck");

		// Test wissen van verplichte velden is onmogelijk, de oude waarden blijven behouden
		deelnemer.setFamilienaam(null);
		assertEquals("Duck", deelnemer.getFamilienaam());
		deelnemer.setGeslacht(null);
		assertEquals(appVar.standaardGeslacht, deelnemer.getGeslacht().getCode());
		deelnemer.setEmail("");
		assertEquals("d.duck@duckstad.nl", deelnemer.getEmail());

		// Test de synchronisatie van de code voor het geslacht met het actueel ingestelde geslacht
		deelnemer.setGeslacht(Gender.MALE);
		assertEquals(deelnemer.getGeslacht().getCode(), "M");


		// Test het berekenen van de jaarlijkse premie
//		berekenJaarlijksePremieDeelnemer()

	}

	@Test
	public void DeelnemerControllerTest() {

	}

	@Test
	public void classAdresTest() {
		// Test of standaard land is ingesteld bij instantieëren van een adres
		Adres adres = new Adres();
		adres.setLand(appVar.standaardLand);
		assertTrue(adres.getLand().equals(appVar.standaardLand));
		assertFalse(adres.getActief());
	}

	// Todo: Verplaats unit-test naar test voor utiliteitsklassen
	@Test
	public void valideerTekenreeksTest() {
		StringBuilder testTekst = new StringBuilder();
		assertFalse(valideerTekenreeks(testTekst.toString()));
		testTekst.append("     ");
		assertFalse(valideerTekenreeks(testTekst.toString()));
		testTekst.delete(0, 255).append("Abc");
		assertTrue(valideerTekenreeks(testTekst.toString()));
	}

	@Test
	public void classBeleggingTest() {

		// Test ophalen van gegevens van en bewerkingen op gebruikers
		long valsId = 0L;
		assertNull(repository.findById(valsId));
		assertNull(repository.findByEmail("email.adres.bestaat@niet"));
		assertNull(repository.getSomBeleggingenDeelnemer(valsId));

		long geldigId = 2L;
		Deelnemer deelnemer = repository.findByEmail("petteflet@flatgebouw.nl");
		assertEquals(deelnemer.getId(), geldigId);

		assertEquals(40441, repository.getSomBeleggingenDeelnemer(1));
	}
}
