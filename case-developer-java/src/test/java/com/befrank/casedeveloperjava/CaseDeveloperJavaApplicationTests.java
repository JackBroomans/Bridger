package com.befrank.casedeveloperjava;

import com.befrank.casedeveloperjava.model.Deelnemer;
import com.befrank.casedeveloperjava.model.Geslacht;
import org.junit.jupiter.api.Test;
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

	@Test
	void contextLoads() {
	}

	@Test
	public void enumGeslachtTest() {
		// Test zoeken en selecteren als standaard ingestelde optie
		Geslacht geslacht = Geslacht.getStandaardOptie();
		assertEquals(geslacht.getTekst(), STANDAARD_OPTIE_GESLACHT);
		// Todo: Implementeer logging en schrijf teksten naar het logbestand i.p.v. naar de console
		System.out.println(geslacht.toString());

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
		deelnemer.setFamilienaam("Botje");
		deelnemer.setVoornamen("Berend");
		deelnemer.setInitialen("B.");
		deelnemer.setPrefixTitels("dhr.");
		deelnemer.setEmail("b.botje@zeilboot.nl");

		assertEquals(deelnemer.stelCorrespondentienaamSamen(), "dhr. B. Botje");

		// Test wissen van verplichte velden is onmogelijk, de oude waarde blijft behouden
		deelnemer.setFamilienaam(null);
		assertTrue(deelnemer.getFamilienaam().equals("Botje"));
		deelnemer.setGeslacht(null);
		assertTrue(deelnemer.getGeslacht().equals(Geslacht.ONBEKEND));
		deelnemer.setEmail("");
		assertTrue(deelnemer.getEmail().equals("b.botje@zeilboot.nl"));

		// Test de synchronisatie van de code voor het geslacht met het actueel ingestelde geslacht
		deelnemer.setGeslacht(Geslacht.MAN);
		assertEquals(deelnemer.getGeslacht().getCode(), "M");

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

}
