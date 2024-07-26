package de.unimarburg.diz.kafkagenetictomtbxml.model.onkostarXml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class Adresse {

    @JacksonXmlProperty(localName = "Strasse")
    private String strasse;
    @JacksonXmlProperty(localName = "Hausnummer")
    private String hausnummer;
    @JacksonXmlProperty(localName = "PLZ")
    private String plz;
    @JacksonXmlProperty(localName = "Wohnort")
    private String wohnort;
    @JacksonXmlProperty(localName = "Adresszusatz")
    private String adresszusatz;
    @JacksonXmlProperty(localName = "Land")
    private String land;
    @JacksonXmlProperty(localName = "Bundesland")
    private String bundesland;
    @JacksonXmlProperty(localName = "Postfix")
    private String postfix;
    @JacksonXmlProperty(localName = "GKZ")
    private String gkz;
}
