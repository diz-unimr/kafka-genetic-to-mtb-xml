package de.unimarburg.diz.kafkagenetictomtbxml.model.onkostarXml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class PatientenDaten {
    @JacksonXmlProperty(localName = "Patient")
    private Patient patient;
    @JacksonXmlProperty(localName = "Dokumentation")
    private Dokumentation dokumentation;

}
