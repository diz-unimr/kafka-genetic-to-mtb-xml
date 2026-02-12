package de.unimarburg.diz.kafkagenetictomtbxml.model.onkostarXml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Dokumentation {
  @JacksonXmlProperty(localName = "Erkrankung")
  private Erkrankung erkrankung;

  @JacksonXmlProperty(localName = "TudokEintrag")
  private TudokEintrag tudokEintrag;
}
