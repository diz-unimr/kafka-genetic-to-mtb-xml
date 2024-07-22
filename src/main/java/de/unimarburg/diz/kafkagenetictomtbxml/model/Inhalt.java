package de.unimarburg.diz.kafkagenetictomtbxml.model;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Inhalt {
    @JacksonXmlProperty(localName = "PatientenDaten")
    private PatientenDaten patientenDaten;
}
