package de.unimarburg.diz.kafkagenetictomtbxml.mapper;

import de.unimarburg.diz.kafkagenetictomtbxml.configuration.HgncConfigurationProperties;
import de.unimarburg.diz.kafkagenetictomtbxml.configuration.MetadataConfigurationProperties;
import de.unimarburg.diz.kafkagenetictomtbxml.model.MtbPatientInfo;
import de.unimarburg.diz.kafkagenetictomtbxml.model.mhGuide.BiomarkerData;
import de.unimarburg.diz.kafkagenetictomtbxml.model.mhGuide.GeneralInfo;
import de.unimarburg.diz.kafkagenetictomtbxml.model.mhGuide.MHGuide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class TudokMapperTest {

    private TudokMapper mapper;

    @BeforeEach
    void setUp() {
        final var metadataConfigurationProperties = MetadataConfigurationProperties.builder().ngsReports(
                MetadataConfigurationProperties.NgsReports.builder().analyseMethoden("MSI").build()
        ).build();

        this.mapper = new TudokMapper(
                new UnterformularSVMapper(new HgncConfigurationProperties()),
                new UnterformularCNVMapper(new HgncConfigurationProperties()),
                new UnterformularRNAFusionMapper(),
                new UnterformularKomplexBiomarkerMapperMSI(metadataConfigurationProperties),
                new UnterformularKomplexBiomarkerMapperTMB(metadataConfigurationProperties),
                metadataConfigurationProperties
        );
    }

    @Test
    void shouldSetDokumentation() throws Exception {
        var actual = this.mapper.createTudokEintrag(defaultMhGuide(), defaultMtbPatientInfo());

        var dokumentation = actual.getEintraege().stream().filter(eintrag -> eintrag.getFeldname().equals("Dokumentation")).findFirst();

        assertThat(dokumentation).hasValueSatisfying(eintrag -> {
            assertThat(eintrag.getFeldname()).isEqualTo("Dokumentation");
            assertThat(eintrag.getWert()).isEqualTo("ERW");
        });
    }

    private static MHGuide defaultMhGuide() {
        var biomarkerData = new BiomarkerData();
        biomarkerData.setNotableBiomarkerList(Collections.emptyList());

        var mhGuideInfo = new MHGuide();
        mhGuideInfo.setGeneralInfo(new GeneralInfo());
        mhGuideInfo.setBiomarkerData(biomarkerData);
        mhGuideInfo.setVariant(Collections.emptyList());
        // add additional default values here
        return mhGuideInfo;
    }

    private static MtbPatientInfo defaultMtbPatientInfo() {
        MtbPatientInfo info = new MtbPatientInfo();
        // add additional default values here
        return info;
    }

}
