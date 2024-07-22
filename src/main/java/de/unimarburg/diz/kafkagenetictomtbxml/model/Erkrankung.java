package de.unimarburg.diz.kafkagenetictomtbxml.model;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Erkrankung {
    private int tumorId;
    private String erkrankungICD10;
    private String erkrankungICD10VersionOID;
    private String erkrankungLokalisation;
    private String erkrankungLokalisationVersionOID;
    private String erkrankungHistologie;
    private String erkrankungHistologieVersionOID;
    private String diagnosedatum;
    private String diagnosedatumAcc;
    private String diagnosestatus;
    private String diagnosestatusVersionOID;
    private String diagnosetext;
    private int revision;
}
