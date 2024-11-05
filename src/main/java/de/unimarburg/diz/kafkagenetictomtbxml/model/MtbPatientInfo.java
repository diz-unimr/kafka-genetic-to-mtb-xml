package de.unimarburg.diz.kafkagenetictomtbxml.model;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MtbPatientInfo implements Serializable {
    @JsonProperty("patienten_id")
    private String patientenId;

    @JsonProperty("pid_gesperrt")
    private int pidGesperrt;

    @JsonProperty("tumor_id")
    private String tumorId;

    @JsonProperty("erkrankung_sid")
    private String sid;

    @JsonProperty("erkrankung_guid")
    private String guid;

    @JsonProperty("erkrankung_revision")
    private String revision;

    @JsonProperty("einsendennummer")
    private String einsendennummer;

    @JsonProperty("diagnose_datum")
    private Date diagnoseDatum;

    // Need to be checked
    @JacksonXmlProperty(localName = "migReferenzTumorId")
    private String migReferenzTumorId ;
}
