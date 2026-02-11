package de.unimarburg.diz.kafkagenetictomtbxml.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import de.unimarburg.diz.kafkagenetictomtbxml.configuration.HgncConfigurationProperties;
import de.unimarburg.diz.kafkagenetictomtbxml.configuration.MetadataConfigurationProperties;
import de.unimarburg.diz.kafkagenetictomtbxml.model.MtbPatientInfo;
import de.unimarburg.diz.kafkagenetictomtbxml.model.mhGuide.MHGuide;
import org.approvaltests.Approvals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;

@SpringBootTest(classes = {
        PatientMapper.class,
        ErkrankungMapper.class,
        TudokMapper.class,
        UnterformularSVMapper.class,
        UnterformularCNVMapper.class,
        UnterformularRNAFusionMapper.class,
        UnterformularKomplexBiomarkerMapperMSI.class,
        UnterformularKomplexBiomarkerMapperTMB.class,
        MetadataConfigurationProperties.class,
        MetadataConfigurationProperties.NgsReports.class,
        HgncConfigurationProperties.class
})
@TestPropertySource(locations = "classpath:application-test.yml")
class OnkostarDataMapperTest {

    OnkostarDataMapper mapper;
    ObjectMapper jsonMapper;
    XmlMapper xmlMapper;

    @BeforeEach
    void setUp(
            @Autowired final PatientMapper patientMapper,
            @Autowired final ErkrankungMapper erkrankungMapper,
            @Autowired final TudokMapper tudokMapper
    ) {
        this.mapper = new OnkostarDataMapper(
                patientMapper,
                erkrankungMapper,
                tudokMapper
        );
        this.jsonMapper = new ObjectMapper();
        this.xmlMapper = new XmlMapper();
        xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    private static MtbPatientInfo defaultMtbPatientInfo() {
        MtbPatientInfo info = new MtbPatientInfo();
        info.setPatientenId("0123456");
        info.setTumorId("1");
        info.setSid("2");
        info.setGuid("00000000-0001-9999-ffff-000000000001");
        info.setRevision("3");
        info.setMigReferenzTumorId("4");
        info.setEinsendennummer("H/2026/10000");
        info.setDiagnoseDatum(Date.from(Instant.parse("2026-01-01T00:00:00.000Z")));
        return info;
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "empty-mhguide.json",
            "sv-mhguide.json",
            "sv_del-mhguide.json"
    })
    void shouldMapToOnkostarXml(String mhGuideFile) throws IOException {
        var inputStream = Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("mhguide/" + mhGuideFile)).readAllBytes();
        var mhGuide = this.jsonMapper.readValue(inputStream, MHGuide.class);

        var onkostarDaten = this.mapper.createOnkostarDaten(mhGuide, defaultMtbPatientInfo());
        // Fixed
        onkostarDaten.setSendeDatum("2026-01-01T12:00:00.000Z");
        var output = this.xmlMapper.writeValueAsString(onkostarDaten);

        Approvals.verify(output, Approvals.NAMES.withParameters(mhGuideFile).forFile().withExtension(".xml"));
    }

}
