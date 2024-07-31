package de.unimarburg.diz.kafkagenetictomtbxml.model.onkostarXml;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Unterformular {

    @JacksonXmlProperty(localName = "ExportID")
    private int exportID;

    @JacksonXmlProperty(localName = "TumorId")
    private String tumorId;

    @JacksonXmlProperty(localName = "DokumentierendeFachabteilung")
    private DokumentierendeFachabteilung dokumentierendeFachabteilung;

    @JacksonXmlProperty(localName = "StartDatum")
    private String startDatum;

    @JacksonXmlProperty(localName = "FormularName")
    private String formularName;

    @JacksonXmlProperty(localName = "FormularVersion")
    private int formularVersion;

    @JacksonXmlProperty(localName = "Prozedurtyp")
    private String prozedurtyp;

    public Unterformular() {}

    public Unterformular(int exportID, String tumorId, DokumentierendeFachabteilung dokumentierendeFachabteilung, String startDatum, String formularName, int formularVersion, String prozedurtyp) {
        this.exportID = exportID;
        this.tumorId = tumorId;
        this.dokumentierendeFachabteilung = dokumentierendeFachabteilung;
        this.formularName = formularName;
        this.formularVersion = formularVersion;
        this.startDatum = startDatum;
        this.prozedurtyp = prozedurtyp;
    }

}
