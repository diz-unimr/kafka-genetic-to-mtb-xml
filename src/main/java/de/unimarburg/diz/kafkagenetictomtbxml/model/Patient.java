package de.unimarburg.diz.kafkagenetictomtbxml.model;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter

public class Patient {
    @JacksonXmlProperty(localName = "ExportID")
    private int exportID;
    @JacksonXmlProperty(localName = "PatientenId")
    private int patientenId;
    @JacksonXmlProperty(localName = "Personendaten")
    private Personendaten personendaten;
    @JacksonXmlProperty(localName = "LetzteInformation")
    private String letzteInformation;
    @JacksonXmlProperty(localName = "AngelegtAm")
    private String angelegtAm;
    @JacksonXmlProperty(localName = "ZuletztBearbeitetAm")
    private String zuletztBearbeitetAm;

}
