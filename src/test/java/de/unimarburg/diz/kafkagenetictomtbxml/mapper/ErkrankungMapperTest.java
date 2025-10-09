package de.unimarburg.diz.kafkagenetictomtbxml.mapper;

import de.unimarburg.diz.kafkagenetictomtbxml.model.MtbPatientInfo;
import de.unimarburg.diz.kafkagenetictomtbxml.model.onkostarXml.Erkrankung;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ErkrankungMapperTest {

    private final ErkrankungMapper mapper = new ErkrankungMapper();

    @Test
    void testCreateErkrankung() {
        var mtbPatientInfo = new MtbPatientInfo();
        mtbPatientInfo.setTumorId("1");
        mtbPatientInfo.setSid("2");
        mtbPatientInfo.setGuid("00000000-0001-9999-ffff-000000000001");
        mtbPatientInfo.setRevision("3");
        mtbPatientInfo.setMigReferenzTumorId("4");

        var erkrankung = new Erkrankung();
        erkrankung.setExportId(2);
        erkrankung.setTumorId("1");
        erkrankung.setSid("2");
        erkrankung.setGuid("00000000-0001-9999-ffff-000000000001");
        erkrankung.setRevision("3");
        erkrankung.setMigReferenzTumorId("4");
        var actual = mapper.createErkrankung(null, mtbPatientInfo);
        assertThat(actual).isEqualTo(erkrankung);
    }

    @ParameterizedTest
    @MethodSource("testGuids")
    void testShouldThrowExceptionOnInvalidGuid(String invalidGuid) {
        var mtbPatientInfo = new MtbPatientInfo();
        mtbPatientInfo.setTumorId("1");
        mtbPatientInfo.setSid("2");
        mtbPatientInfo.setGuid(invalidGuid);
        mtbPatientInfo.setRevision("3");
        mtbPatientInfo.setMigReferenzTumorId("4");

        var exception = assertThrows(IllegalArgumentException.class, () -> mapper.createErkrankung(null, mtbPatientInfo));
        assertThat(exception.getMessage()).isEqualTo("Invalid GUID in MtbPatientInfo");
    }

    public static Stream<String> testGuids() {
        return Stream.of(null, "", "    ", "invalid-guid");
    }
}
