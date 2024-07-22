package de.unimarburg.diz.kafkagenetictomtbxml.model;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Ergebnis {
    @JacksonXmlProperty(localName = "Wert")
    private String wert;
    @JacksonXmlProperty(localName = "Filterkategorie")
    private String filterkategorie;
    @JacksonXmlProperty(localName = "Version")
    private String version;
    @JacksonXmlProperty(localName = "Kurztext")
    private String kurztext;

}
