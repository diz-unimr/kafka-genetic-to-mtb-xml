package de.unimarburg.diz.kafkagenetictomtbxml.model;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JacksonXmlRootElement(localName = "OnkostarDaten")
public class MtbGeneticXml {

    private String pid;
    private String tumorId;
    // Getters and setters

}
