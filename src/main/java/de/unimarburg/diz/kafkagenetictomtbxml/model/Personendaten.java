package de.unimarburg.diz.kafkagenetictomtbxml.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Personendaten {
    @JacksonXmlProperty(localName = "Nachname")
    private String nachname;
    @JacksonXmlProperty(localName = "Vorname")
    private String vorname;
    @JacksonXmlProperty(localName = "Geburtsdatum")
    private String geburtsdatum;
    @JacksonXmlProperty(localName = "Geschlecht")
    private String geschlecht;

    private String titel;
    private String namenzusatz;
    private String namensvorsatz;
    private String geburtsname;
    private String andereNamen;
    private String geburtsland;
    private String geburtsort;
    private Adresse aktuelleAdresse;
    private String telefonnummer;
    private String telefonnummer2;
    private String emailadresse;
    private String nachsorgepassnr;

}
