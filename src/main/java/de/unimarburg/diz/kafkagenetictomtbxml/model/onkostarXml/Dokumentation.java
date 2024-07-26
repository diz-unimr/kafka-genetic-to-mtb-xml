package de.unimarburg.diz.kafkagenetictomtbxml.model.onkostarXml;

import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
@Getter
@Setter
public class Dokumentation {
    @JacksonXmlProperty(localName = "Erkrankung")
    private Erkrankung erkrankung;
    @JacksonXmlProperty(localName = "TudokEintrag")
    private TudokEintrag tudokEintrag;
}
