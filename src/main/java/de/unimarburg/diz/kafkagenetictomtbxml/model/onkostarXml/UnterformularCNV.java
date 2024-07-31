package de.unimarburg.diz.kafkagenetictomtbxml.model.onkostarXml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class UnterformularCNV extends Unterformular {

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Eintrag")
    private List<Eintrag> eintraege;

    public UnterformularCNV() {}
    public UnterformularCNV(int exportID, String tumorId, DokumentierendeFachabteilung dokumentierendeFachabteilung,
                            String startDatum, String formularName, int formularVersion,  String prozedurtyp, List<Eintrag> eintraege) {
        super(exportID,tumorId,dokumentierendeFachabteilung,startDatum,formularName,formularVersion,prozedurtyp);
        this.eintraege = eintraege;
    }
    public List<Eintrag> getEintraege() {
        return eintraege;
    }
    public void setEintraege(List<Eintrag> eintraegee) {
        this.eintraege = eintraegee;
    }
}
