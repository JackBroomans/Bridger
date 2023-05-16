package com.befrank.casedeveloperjava.configuration;

import com.befrank.casedeveloperjava.model.Deelnemer;
import com.befrank.casedeveloperjava.model.enums.Gender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:appVar.yml")
@ConfigurationProperties(prefix = "deelnemer")
public class AppVariabelenDeelnemer {

    @Value("${regex_deelnemersnummer}")
    public String regexDeelnemersnummer;

    @Value("${regex_emailadres}")
    public String regexEmail;

    @Value("${korte_datumNotatie}")
    public String korteDatumNotatie;

    @Value("${standaard_geslacht}")
    public String standaardGeslacht;

    @Value("${minimum_leeftijd_deelnemer}")
    public int minLeeftijdDeelnemer;

    @Value("${maximum_leeftijd_deelnemer}")
    public int maxLeeftijdDeelnemer;

    /* Standaardopties van enumeraties */
    @Value("${standaard_land}")
    public String standaardLand;

    public Deelnemer deelnemer() {
        Deelnemer deelnemer = new Deelnemer();
        deelnemer.setGeslacht(Gender.getByCode(this.standaardGeslacht));
        return deelnemer;
    }
}
