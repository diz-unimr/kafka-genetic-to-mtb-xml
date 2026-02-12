package de.unimarburg.diz.kafkagenetictomtbxml.model.onkostarXml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
public class Erkrankung {
  @JacksonXmlProperty(localName = "ExportID")
  private int exportId;

  @JacksonXmlProperty(localName = "TumorId")
  private String tumorId;

  @JacksonXmlProperty(localName = "SID")
  private String sid;

  @JacksonXmlProperty(localName = "GUID")
  private String guid;

  @JacksonXmlProperty(localName = "Revision")
  private String revision;

  @JacksonXmlProperty(localName = "MigReferenzTumorId")
  private String migReferenzTumorId;

  /* @JacksonXmlProperty(localName = "ErkrankungICD10")
  private String erkrankungICD10;
  @JacksonXmlProperty(localName = "ErkrankungICD10VersionOID")
  private String erkrankungICD10VersionOID;
  @JacksonXmlProperty(localName = "ErkrankungLokalisation")
  private String erkrankungLokalisation;
  @JacksonXmlProperty(localName = "ErkrankungLokalisationVersionOID")
  private String erkrankungLokalisationVersionOID;
  @JacksonXmlProperty(localName = "ErkrankungHistologie")
  private String erkrankungHistologie;
  @JacksonXmlProperty(localName = "ErkrankungHistologieVersionOID")
  private String erkrankungHistologieVersionOID;
  @JacksonXmlProperty(localName = "Diagnosedatum")
  private String diagnosedatum;
  @JacksonXmlProperty(localName = "DiagnosedatumAcc")
  private String diagnosedatumAcc;
  @JacksonXmlProperty(localName = "Diagnosestatus")
  private String diagnosestatus;
  @JacksonXmlProperty(localName = "DiagnosestatusVersionOID")
  private String diagnosestatusVersionOID;
  @JacksonXmlProperty(localName = "Diagnosetext")
  private String diagnosetext;*/

}
