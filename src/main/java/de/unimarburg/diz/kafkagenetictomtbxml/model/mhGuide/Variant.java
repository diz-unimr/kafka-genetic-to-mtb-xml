package de.unimarburg.diz.kafkagenetictomtbxml.model.mhGuide;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Variant implements Serializable {
    @JsonProperty("DETECTED_VAR_ID")
    private String detectedVarId;

    @JsonProperty("VARIANT_SYMBOL")
    private String variantSymbol;

    @JsonProperty("DISPLAY_VARIANT_TYPE")
    private String displayVariantType;

    @JsonProperty("GENOMIC_EXTRA_DATA")
    private String genomicExtraData;

    @JsonProperty("TMB_VARIANT_COUNT_PER_MEGABASE")
    private String tmbVariantCountPerMegabase;

    @JsonProperty("CHROMOSOMAL_MODIFIED_OBJECT")
    private String chromosomeModifiedObject;

    @JsonProperty("GENE_SYMBOL")
    private String geneSymbol;

    @JsonProperty("CHROMOSOMAL_MODIFICATION")
    private String chromosomeModification;

    @JsonProperty("PROTEIN_MODIFICATION")
    private String proteinModification;

    @JsonProperty("TOTAL_READS_IN_TUMOR")
    private String totalReadsInTumor;

    @JsonProperty("VARIANT_ALLELE_FREQUENCY_IN_TUMOR")
    private String variantAlleleFrequencyInTumor;

    @JsonProperty("DBSNP")
    private String dbsnp;

    @JsonProperty("VARIANT_CLASSIFICATION")
    private String variantClassification;

    @JsonProperty("ANNOTATION_JSON")
    private String annotationJson;

    @JsonProperty("COPY_NUMBER")
    private String copyNumber;

    @JsonProperty("DISPLAY_MODIFIED_OBJECT")
    private String displayModifiedObject;

    @JsonProperty("VARIANT_EFFECT")
    private String variantEffect;

    @JsonProperty("SUPPORTING_READ_PAIRS_IN_FUSION_GENE")
    private String supportingReadPairsInFusionGene;

    @JsonProperty("CLASSIFICATION_NAME")
    private String classificationName;

    // Exist only for new specimen
    @Nullable
    @JsonProperty("ONCOGENIC_CLASSIFICATION_NAME")
    private String oncogenicClassificationName;

}
