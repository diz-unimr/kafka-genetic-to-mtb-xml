package de.unimarburg.diz.kafkagenetictomtbxml.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class PatientenDaten {
    @JacksonXmlProperty(localName = "Patient")
    private Patient patient;

}
