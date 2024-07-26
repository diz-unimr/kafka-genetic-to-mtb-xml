package de.unimarburg.diz.kafkagenetictomtbxml.model.onkostarXml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

@Getter
@Setter

public class TudokEintrag {

    @JacksonXmlProperty(localName = "ExportID")
    private int exportID;

    @JacksonXmlProperty(localName = "TumorId")
    private int tumorId;

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

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Eintrag")
    private List<Eintrag> eintraege;

    @JacksonXmlProperty(localName = "HauptTudokEintragExportID")
    private int hauptTudokEintragExportID;

    @JacksonXmlProperty(localName = "Revision")
    private int revision;

    @JacksonXmlProperty(localName = "BearbeitungStatus")
    private int bearbeitungStatus;

}
