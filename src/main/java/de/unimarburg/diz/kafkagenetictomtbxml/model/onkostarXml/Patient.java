package de.unimarburg.diz.kafkagenetictomtbxml.model.onkostarXml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Patient {
  @JacksonXmlProperty(localName = "ExportID")
  private int exportID;

  @JacksonXmlProperty(localName = "PatientenId")
  private String patientenId;

  @JacksonXmlProperty(localName = "Personendaten")
  private Personendaten personendaten;

  @JacksonXmlProperty(localName = "PidGesperrt")
  private String pidGesperrt;
}
