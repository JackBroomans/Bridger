package com.befrank.casedeveloperjava;

import com.befrank.casedeveloperjava.model.Geslacht;
import org.aspectj.lang.annotation.Before;
import org.hibernate.tool.schema.ast.GeneratedSqlScriptParserTokenTypes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootTest
class CaseDeveloperJavaApplicationTests {

	// ToDo: Variable opnemen in resources bestand om externe configuratie mogelijk te maken
	private static final String STANDAARD_OPTIE_GESLACHT = "Onbekend";

	@Test
	void contextLoads() {
	}

	@Test
	public void enumGeslachtTest() {
		// Test zoeken en selevteren als standaard ingestelde optie
		Geslacht geslacht = Geslacht.getStandaardOptie();
		Assertions.assertEquals(geslacht.getTekst(), STANDAARD_OPTIE_GESLACHT);

		// Test zoeken van een gelsacht op basis van haar code
		Assertions.assertEquals(Geslacht.zoekOpCode(null), null);
		Assertions.assertEquals(Geslacht.zoekOpCode(""), null);
		Assertions.assertEquals(Geslacht.zoekOpCode("XYZ"), null);

		Assertions.assertEquals(Geslacht.zoekOpCode("X"), null);
		Assertions.assertEquals(Geslacht.zoekOpCode("O"), Geslacht.ONBEKEND);

		// Todo: Implementeer logging en schrijf teksten naar het logbestand i.p.v. naar de console
		System.out.println(geslacht.toString());
	}

}
