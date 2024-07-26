package de.unimarburg.diz.kafkagenetictomtbxml.model.onkostarXml;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class DokumentierendeFachabteilung {

    @JacksonXmlProperty(localName = "FachabteilungKennung")
    private String fachabteilungKennung;

    @JacksonXmlProperty(localName = "EinrichtungKennung")
    private String einrichtungKennung;

}
