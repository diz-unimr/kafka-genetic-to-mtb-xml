package de.unimarburg.diz.kafkagenetictomtbxml.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class Adresse {
    private String strasse;
    private String hausnummer;
    private String plz;
    private String wohnort;
    private String adresszusatz;
    private String land;
    private String bundesland;
    private String postfix;
    private String gkz;
}
