package de.unimarburg.diz.kafkagenetictomtbxml.model.mhGuide;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MHGuide implements Serializable {

    @JsonProperty("DATASET_VERSION_STRING")
    private String datasetVersionString;

    @JsonProperty("GENERAL")
    private GeneralInfo generalInfo;

    @JsonProperty("VARIANT_LONG_LIST")
    private List<Variant> variant;
    
}
