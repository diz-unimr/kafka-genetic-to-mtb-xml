package de.unimarburg.diz.kafkagenetictomtbxml.model.mhGuide;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeneralInfo implements Serializable {
    @JsonProperty("OU_CUSTOMER")
    private String oUCustomer;
    @JsonProperty("ORG_UNIT_NAME")
    private String oUUnitName;
    @JsonProperty("CASE_UUID")
    private String caseUuid;
    @JsonProperty("ANALYSIS_TYPE")
    private String analysisType;
    @JsonProperty("ORDER_NUMBER")
    private String orderNummer;
    @JsonProperty("ORDER_DATE")
    private String orderDate;
    @JsonProperty("DISEASE_DESCRIPTION")
    private String diseaseDescription;
    @JsonProperty("PT_DISEASE_NAME")
    private String patientIdentifier;
    @JsonProperty("LABTEST_DISPLAY_NAME")
    private String lastestDisplayName;
}
