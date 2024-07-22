package de.unimarburg.diz.kafkagenetictomtbxml.model;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Unterformular {
    @JacksonXmlProperty(localName = "ErkrankungExportID")
    private int erkrankungExportID;

    @JacksonXmlProperty(localName = "TumorId")
    private int tumorId;

    @JacksonXmlProperty(localName = "DokumentierendeFachabteilung")
    private DokumentierendeFachabteilung dokumentierendeFachabteilung;

    @JacksonXmlProperty(localName = "StartDatum")
    private String startDatum;

    @JacksonXmlProperty(localName = "FormularName")
    private String formularName;

    @JacksonXmlProperty(localName = "FormularVersion")
    private String formularVersion;

    @JacksonXmlProperty(localName = "Eintrag")
    private Eintrag eintrag;

    @JacksonXmlProperty(localName = "HauptTudokEintragExportID")
    private int hauptTudokEintragExportID;

    @JacksonXmlProperty(localName = "Revision")
    private int revision;

    @JacksonXmlProperty(localName = "BearbeitungStatus")
    private int bearbeitungStatus;
}
