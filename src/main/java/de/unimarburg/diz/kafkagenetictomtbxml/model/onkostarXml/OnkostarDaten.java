package de.unimarburg.diz.kafkagenetictomtbxml.model.onkostarXml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JacksonXmlRootElement(localName = "OnkostarDaten")
public class OnkostarDaten {

  @JacksonXmlProperty(localName = "Titel")
  private String titel;

  @JacksonXmlProperty(localName = "SendendeOrganisation")
  private SendendeOrganisation sendendeOrganisation;

  @JacksonXmlProperty(localName = "SendeDatum")
  private String sendeDatum;

  @JacksonXmlProperty(localName = "DokumentId")
  private int dokumentId;

  @JacksonXmlProperty(localName = "DokumentVersion")
  private int dokumentVersion;

  @JacksonXmlProperty(localName = "Inhalt")
  private Inhalt inhalt;
}
