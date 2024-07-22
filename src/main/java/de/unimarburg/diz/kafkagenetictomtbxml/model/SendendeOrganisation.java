package de.unimarburg.diz.kafkagenetictomtbxml.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendendeOrganisation {
    @JacksonXmlProperty(localName = "Bezeichnung")
    private String bezeichnung;
    @JacksonXmlProperty(localName = "Kennung")
    private String kennung;

}
