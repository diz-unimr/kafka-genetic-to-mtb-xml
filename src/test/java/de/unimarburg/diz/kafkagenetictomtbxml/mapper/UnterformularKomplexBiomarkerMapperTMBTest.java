package de.unimarburg.diz.kafkagenetictomtbxml.mapper;

import de.unimarburg.diz.kafkagenetictomtbxml.configuration.MetadataConfigurationProperties;
import de.unimarburg.diz.kafkagenetictomtbxml.model.MtbPatientInfo;
import de.unimarburg.diz.kafkagenetictomtbxml.model.mhGuide.Variant;
import de.unimarburg.diz.kafkagenetictomtbxml.model.onkostarXml.DokumentierendeFachabteilung;
import de.unimarburg.diz.kafkagenetictomtbxml.model.onkostarXml.UnterformularKomplexBiomarker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UnterformularKomplexBiomarkerMapperTMBTest {

    UnterformularKomplexBiomarkerMapperTMB mapper;

    @BeforeEach
    void setUp() {
        var metadataConfigProperties = MetadataConfigurationProperties.builder()
                .ngsReports(MetadataConfigurationProperties.NgsReports.builder()
                        .analyseMethoden("TestAnalyseMethode")
                        .build())
                .build();
        this.mapper = new UnterformularKomplexBiomarkerMapperTMB(metadataConfigProperties);
    }

    @Test
    void shouldMapUnterformularKomplexBiomarkerMSI() {

        var actual = this.mapper.createXmlUnterformularKBiomarkerTMB(
                defaultMtbPatientInfo(),
                createVariant("TEST"),
                defaultDokumentierendeFachabteilung(),
                42
        );

        var analyseMethoden = actual.getEintraege().stream().filter(eintrag -> eintrag.getFeldname().equals("AnalyseMethoden")).findFirst();

        assertThat(actual).isInstanceOf(UnterformularKomplexBiomarker.class);
        assertThat(analyseMethoden).isPresent().hasValueSatisfying(eintrag -> assertThat(eintrag.getWert()).isEqualTo("TestAnalyseMethode"));
    }

    private static MtbPatientInfo defaultMtbPatientInfo() {
        MtbPatientInfo info = new MtbPatientInfo();
        // add additional default values here
        return info;
    }

    private static DokumentierendeFachabteilung defaultDokumentierendeFachabteilung() {
        DokumentierendeFachabteilung fa = new DokumentierendeFachabteilung();
        // add additional default values here
        return fa;
    }

    private static Variant createVariant(
            final String symbol
    ) {
        Variant variant = new Variant();
        variant.setGeneSymbol(symbol);
        variant.setChromosomeModification("");
        variant.setGenomicExtraData("1.0");
        return variant;
    }

}
