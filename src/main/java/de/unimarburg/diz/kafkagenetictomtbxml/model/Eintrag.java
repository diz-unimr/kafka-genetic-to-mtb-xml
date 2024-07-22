package de.unimarburg.diz.kafkagenetictomtbxml.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Eintrag {
    @JacksonXmlProperty(isAttribute = true, localName = "Feldname")
    private String feldname;
    @JacksonXmlElementWrapper(localName = "Unterformular")
    @JacksonXmlProperty(localName = "Unterformular")
    private List<Unterformular> unterformularList;

    @JacksonXmlProperty(localName = "Ergebnis")
    private Ergebnis ergebnis;
}