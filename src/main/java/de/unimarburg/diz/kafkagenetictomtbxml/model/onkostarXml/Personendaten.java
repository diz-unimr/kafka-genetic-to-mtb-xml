package de.unimarburg.diz.kafkagenetictomtbxml.model.onkostarXml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Personendaten {
/*    @JacksonXmlProperty(localName = "Nachname")
    private String nachname;
    @JacksonXmlProperty(localName = "Vorname")
    private String vorname;
    @JacksonXmlProperty(localName = "Geburtsdatum")
    private String geburtsdatum;
    @JacksonXmlProperty(localName = "Geschlecht")
    private String geschlecht;
    @JacksonXmlProperty(localName = "Titel")
    private String titel;
    @JacksonXmlProperty(localName = "Namenzusatz")
    private String namenzusatz;
    @JacksonXmlProperty(localName = "Namensvorsatz")
    private String namensvorsatz;
    @JacksonXmlProperty(localName = "Geburtsname")
    private String geburtsname;
    @JacksonXmlProperty(localName = "AndereNamen")
    private String andereNamen;
    @JacksonXmlProperty(localName = "Geburtsland")
    private String geburtsland;
    @JacksonXmlProperty(localName = "Geburtsort")
    private String geburtsort;
    @JacksonXmlProperty(localName = "AktuelleAdresse")
    private Adresse aktuelleAdresse;
    @JacksonXmlProperty(localName = "Telefonnummer")
    private String telefonnummer;
    @JacksonXmlProperty(localName = "Telefonnummer2")
    private String telefonnummer2;
    @JacksonXmlProperty(localName = "Emailadresse")
    private String emailadresse;
    @JacksonXmlProperty(localName = "Nachsorgepassnr")
    private String nachsorgepassnr;*/

    @JacksonXmlProperty(localName = "AktuelleAdresse")
    private Adresse aktuelleAdresse;
}
