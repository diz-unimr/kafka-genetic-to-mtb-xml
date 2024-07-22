package de.unimarburg.diz.kafkagenetictomtbxml.model;

import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
@Getter
@Setter

public class TudokEintrag {
    private int erkrankungExportID;
    private int tumorId;
    private DokumentierendeFachabteilung dokumentierendeFachabteilung;
    private String startDatum;
    private String formularName;
    private int formularVersion;
    private Eintrag eintrag;
    private int revision;
    private int bearbeitungStatus;

}
