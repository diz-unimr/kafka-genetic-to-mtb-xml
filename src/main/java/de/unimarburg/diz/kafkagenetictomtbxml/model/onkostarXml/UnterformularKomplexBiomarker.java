package de.unimarburg.diz.kafkagenetictomtbxml.model.onkostarXml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import java.util.List;

public class UnterformularKomplexBiomarker extends Unterformular {

  @JacksonXmlElementWrapper(useWrapping = false)
  @JacksonXmlProperty(localName = "Eintrag")
  private List<Eintrag> eintraege;

  @JacksonXmlProperty(localName = "HauptTudokEintragExportID")
  private int hauptTudokEintragExportID;

  @JacksonXmlProperty(localName = "Revision")
  private int revision;

  @JacksonXmlProperty(localName = "BearbeitungStatus")
  private int bearbeitungStatus;

  public UnterformularKomplexBiomarker() {}

  public UnterformularKomplexBiomarker(
      int exportID,
      int erkrankungExportID,
      String tumorId,
      DokumentierendeFachabteilung dokumentierendeFachabteilung,
      String startDatum,
      String formularName,
      int formularVersion,
      String prozedurtyp,
      List<Eintrag> eintraege,
      int hauptTudokEintragExportID,
      int revision,
      int bearbeitungStatus) {
    super(
        exportID,
        erkrankungExportID,
        tumorId,
        dokumentierendeFachabteilung,
        startDatum,
        formularName,
        formularVersion,
        prozedurtyp);

    this.eintraege = eintraege;
    this.hauptTudokEintragExportID = hauptTudokEintragExportID;
    this.revision = revision;
    this.bearbeitungStatus = bearbeitungStatus;
  }

  public List<Eintrag> getEintraege() {
    return eintraege;
  }

  public void setEintraege(List<Eintrag> eintraegee) {
    this.eintraege = eintraegee;
  }

  public int getHauptTudokEintragExportID() {
    return hauptTudokEintragExportID;
  }

  public void setHauptTudokEintragExportID(int hauptTudokEintragExportID) {
    this.hauptTudokEintragExportID = hauptTudokEintragExportID;
  }

  public int getRevision() {
    return revision;
  }

  public void setRevision(int revision) {
    this.revision = revision;
  }

  public int getBearbeitungStatus() {
    return bearbeitungStatus;
  }

  public void setBearbeitungStatus(int bearbeitungStatus) {
    this.bearbeitungStatus = bearbeitungStatus;
  }
}
