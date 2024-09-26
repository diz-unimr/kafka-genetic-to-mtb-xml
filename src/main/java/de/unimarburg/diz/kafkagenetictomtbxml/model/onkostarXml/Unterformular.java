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
    private int formularVersion;

    @JacksonXmlProperty(localName = "Prozedurtyp")
    private String prozedurtyp;

/*    @JacksonXmlProperty(localName = "HauptTudokEintragExportID")
    private int hauptTudokEintragExportID;

    @JacksonXmlProperty(localName = "Revision")
    private int revision;

    @JacksonXmlProperty(localName = "BearbeitungStatus")
    private int bearbeitungStatus;*/

    public Unterformular() {}

    public Unterformular(int exportID, int erkrankungExportID, int tumorId,
                         DokumentierendeFachabteilung dokumentierendeFachabteilung, String startDatum, String formularName,
                         int formularVersion, String prozedurtyp){
                         //int hauptTudokEintragExportID, int revision, int bearbeitungStatus) {
        this.exportID = exportID;
        this.erkrankungExportID = erkrankungExportID;
        this.tumorId = tumorId;
        this.dokumentierendeFachabteilung = dokumentierendeFachabteilung;
        this.formularName = formularName;
        this.formularVersion = formularVersion;
        this.startDatum = startDatum;
        this.prozedurtyp = prozedurtyp;
        //this.hauptTudokEintragExportID = hauptTudokEintragExportID;
        //this.revision = revision;
        //this.bearbeitungStatus = bearbeitungStatus;
    }
}
