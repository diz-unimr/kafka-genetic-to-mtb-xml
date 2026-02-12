package de.unimarburg.diz.kafkagenetictomtbxml.model.mhGuide;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NotableBiomarker implements Serializable {

  @JsonProperty("BIOMARKERS")
  private List<BioMarker> biomarkers;
}
