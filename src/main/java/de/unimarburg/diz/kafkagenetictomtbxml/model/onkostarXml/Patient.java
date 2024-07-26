package de.unimarburg.diz.kafkagenetictomtbxml.model.onkostarXml;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter

public class Patient {
    @JacksonXmlProperty(localName = "PatientenId")
    private int patientenId;

    @JacksonXmlProperty(localName = "Personendaten")
    private Personendaten personendaten;

    @JacksonXmlProperty(localName = "LetzteInformation")
    private String letzteInformation;

    @JacksonXmlProperty(localName = "Sterbedatum")
    private String sterbedatum;

    @JacksonXmlProperty(localName = "SterbedatumGenauigkeit")
    private String sterbedatumGenauigkeit;

    @JacksonXmlProperty(localName = "Versichertennummer")
    private String versichertennummer;

    @JacksonXmlProperty(localName = "AHV-Nummer")
    private String ahvNummer;

    @JacksonXmlProperty(localName = "Familienangehoerigennummer")
    private String familienangehoerigennummer;

    @JacksonXmlProperty(localName = "Krankenkassennummer")
    private String krankenkassennummer;

    @JacksonXmlProperty(localName = "Geburtsort")
    private String geburtsort;

    @JacksonXmlProperty(localName = "Staatsangehoerigkeit")
    private String staatsangehoerigkeit;

    @JacksonXmlProperty(localName = "Familienstand")
    private String familienstand;

    @JacksonXmlProperty(localName = "Geburtsland")
    private String geburtsland;

    @JacksonXmlProperty(localName = "Anmerkung")
    private String anmerkung;

    @JacksonXmlProperty(localName = "AnmerkungLkr")
    private String anmerkungLkr;

    @JacksonXmlProperty(localName = "PidGesperrt")
    private boolean pidGesperrt;

    @JacksonXmlProperty(localName = "AngelegtAm")
    private String angelegtAm;

    @JacksonXmlProperty(localName = "ZuletztBearbeitetAm")
    private String zuletztBearbeitetAm;

}
