package com.befrank.casedeveloperjava.model;

import java.security.InvalidParameterException;
import java.util.InvalidPropertiesFormatException;

public enum Geslacht {
    MAN ("M","Man"),
    VROUW ("V", "Vrouw"),
    NIET_RELEVANT ("N", "Niet Relevant"),
    ONBEKEND ("O", "Onbekend");

    // ToDo: Variable opnemen in resources bestand om externe configuratie mogelijk te maken
    private static final String STANDAARD_OPTIE_GESLACHT = "Onbekend";

    private String code;
    private String tekst;

    Geslacht(String code, String tekst){
        this.code = code;
        this.tekst = tekst;
    }

    public String getCode() { return this.code; }
    public String getTekst() { return this.tekst; }

    /**
     * <strong>getStandaardOptie()</strong><br>
     * Selecteert het als standaard ingestelde element.<br>
     * @return Het als standaard ingestelde geslacht. Indien dit nit is ingesteld dan wordt de waarde <i>null</i>
     * geretourneerd.
     */
    public static Geslacht getStandaardOptie() {
        for (Geslacht geslacht : Geslacht.values()) {
            if (geslacht.getTekst().equals(STANDAARD_OPTIE_GESLACHT)) {
                return geslacht;
            }
        }
        // Todo: Implementeer logger en log 'geen standaard element ingesteld'
        return null;
    }

    /**
     * <strong>zoekOpCode(<i>String</i></strong><br>
     * zoekt en selecteert op basis van de gegeven code het geslacht.<br>
     */
    public static Geslacht zoekOpCode(String code) {
        try {
            if (code == null  || code.isBlank() || code.length() > 1) {
                throw new InvalidParameterException();
            }
            for (Geslacht geslacht : Geslacht.values()) {
                if (geslacht.getCode().equals(code)) {
                    return geslacht;
                }
            }
            throw new InvalidParameterException();
        }
        catch (InvalidParameterException ex) {
            // Todo: Implementeer logger en log 'ongeldige code'
        }
        return null;
    }


    /**
     * <strong>toString()</strong> <i>override</i><br>
     * Presenteert het geslacht in een meer leesbare vorm.
     */
    @Override
    public String toString() {
        StringBuilder tekst = new StringBuilder().append("Geslacht");
        tekst.append("\n\tOptie: ").append(this.name());
        tekst.append("\n\tCode: ").append(this.code);
        tekst.append("\n\tTekst: ").append(this.tekst);

        return tekst.toString();
    }


}
