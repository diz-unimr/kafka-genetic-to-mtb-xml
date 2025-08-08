package de.unimarburg.diz.kafkagenetictomtbxml.model.mhGuide;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import lombok.Data;

import java.io.Serializable;
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BioMarker implements Serializable {
    
    @JsonProperty("DETECTED_VAR_ID")
    private Integer detectedVarId;
}
