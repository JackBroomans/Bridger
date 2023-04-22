package com.befrank.casedeveloperjava;

import com.befrank.casedeveloperjava.model.Adres;
import com.befrank.casedeveloperjava.model.Deelnemer;
import com.befrank.casedeveloperjava.model.Geslacht;
import com.befrank.casedeveloperjava.model.PremieDeelnemer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.befrank.casedeveloperjava.util.DatumTijdFuncties.getLeeftijd;
import static com.befrank.casedeveloperjava.util.interfaces.IValidaties.valideerTekenreeks;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CaseDeveloperJavaApplicationTests {

	// Todo: Variabelen opnemen in resources-bestand om externe configuratie mogelijk te maken
	private static final String STANDAARD_OPTIE_GESLACHT = "Onbekend";
	private static final DateTimeFormatter KORTE_DATUM_FORMAAT = DateTimeFormatter.ofPattern("d-MM-yyyy");
	private static final String STANDAARD_LAND = "Nederland";

	@Autowired
	DeelnemerRepository repository;

	@Test
	void contextLoads() {
	}

	@Test
	public void enumGeslachtTest() {
		// Test zoeken en selecteren als standaard ingestelde optie
		Geslacht geslacht = Geslacht.getStandaardOptie();
		assertEquals(geslacht.getTekst(), STANDAARD_OPTIE_GESLACHT);

		// Test zoeken van een geslacht op basis van haar code
		assertEquals(Geslacht.zoekOpCode(null), null);
		assertEquals(Geslacht.zoekOpCode(""), null);
		assertEquals(Geslacht.zoekOpCode("XYZ"), null);
		assertEquals(Geslacht.zoekOpCode("X"), null);
		// Happy flow zoeken op code
		assertEquals(Geslacht.zoekOpCode("M"), Geslacht.MAN);
	}

	@Test
	public void classDeelnemerTest() {
		final String geboortedatumGeldig = "16-12-1978";
		final String geboortedatumOngeldigFormaat = "31-2-1978";
		final String geboortedatumTeJong =
				LocalDate.now().minusYears(16).format(KORTE_DATUM_FORMAAT);
		final String geboortedatumTeOud =
				LocalDate.now().plusYears(121).format(KORTE_DATUM_FORMAAT);

		Deelnemer deelnemer = new Deelnemer();

		// Test toekennen geboortedatum
		deelnemer.setGeboortedatum(geboortedatumOngeldigFormaat);
		assertEquals(deelnemer.getGeboortedatum(), null);
		deelnemer.setGeboortedatum(geboortedatumTeJong);
		assertEquals(deelnemer.getGeboortedatum(), null);
		deelnemer.setGeboortedatum(geboortedatumTeOud);
		assertEquals(deelnemer.getGeboortedatum(), null);
		// Happy flow toekennen geboortedatum
		deelnemer.setGeboortedatum(geboortedatumGeldig);
		assertEquals(deelnemer.getGeboortedatum(), LocalDate.parse(geboortedatumGeldig, KORTE_DATUM_FORMAAT));

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
		assertTrue(deelnemer.getFamilienaam().equals("Duck"));
		deelnemer.setGeslacht(null);
		assertTrue(deelnemer.getGeslacht().equals(Geslacht.ONBEKEND));
		deelnemer.setEmail("");
		assertTrue(deelnemer.getEmail().equals("d.duck@duckstad.nl"));

		// Test de synchronisatie van de code voor het geslacht met het actueel ingestelde geslacht
		deelnemer.setGeslacht(Geslacht.MAN);
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
		assertEquals(adres.getLand(), STANDAARD_LAND);
		assertFalse(adres.getActief());
	}

	// Todo: Verplaats unit-test naar test voor utiliteitsklassen
	@Test
	public void valideerTekenreeksTest() {
		StringBuilder testTekst = new StringBuilder();
		assertFalse(valideerTekenreeks(testTekst.toString()));
		testTekst.append("");
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
		assertEquals(repository.getSomBeleggingenDeelnemer(valsId), null);

		long geldigId = 2L;
		Deelnemer deelnemer = repository.findByEmail("petteflet@flatgebouw.nl");
		assertEquals(deelnemer.getId(), geldigId);

		assertEquals(40441, repository.getSomBeleggingenDeelnemer(1));

	}

}
