package de.unimarburg.diz.kafkagenetictomtbxml.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.regex.Pattern;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
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
    @JsonProperty("migReferenzTumorId")
    private String migReferenzTumorId;

    public boolean isValid() {
        final var pattern1 = Pattern.compile("(?<prefix>[A-Z])/\\d{2}(?<year>\\d{2})/0*(?<number>\\d+)");
        final var pattern2 = Pattern.compile("(?<prefix>[A-Z])0*(?<number>\\d+)-(?<year>\\d{2})");

        return patientenId != null && !patientenId.isBlank()
                && tumorId != null && !tumorId.isBlank()
                && sid != null && !sid.isBlank()
                && guid != null && !guid.isBlank()
                && revision != null && !revision.isBlank()
                && einsendennummer != null && !einsendennummer.isBlank()
                && (pattern1.matcher(einsendennummer).find() || pattern2.matcher(einsendennummer).find())
                && diagnoseDatum != null
                && migReferenzTumorId != null && !migReferenzTumorId.isBlank();
    }
}
