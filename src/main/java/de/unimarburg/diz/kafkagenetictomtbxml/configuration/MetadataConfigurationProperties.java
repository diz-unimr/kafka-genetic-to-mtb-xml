package de.unimarburg.diz.kafkagenetictomtbxml.configuration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "metadata")
@Data
@Builder
public class MetadataConfigurationProperties {

    private NgsReports ngsReports;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class NgsReports {
        private String kitManufacturer;
        private String sequencer;
        private String referenceGenome;
        private String sequencingType;
        private String sequencingTypeShort;
        private String interpolationSystem;
        private String reportedFocality;
        private String panelVP;
        private String panelVPCP;
        private String seqKitTypVP;
        private String seqPipelineVP;
        private String seqKitTypVPCP;
        private String seqPipelineVPCP;
        private String fachabteilungKennung;
        private String einrichtungKennung;
        private String durchfuehrendeOE;
        private String durchfuehrendeOEKurzText;
        private String internExternWert;
        private String internExternKurztext;
        private String analyseMethoden;
        private String classificationName;
        private String oncogenicClassificationName;
    }
}