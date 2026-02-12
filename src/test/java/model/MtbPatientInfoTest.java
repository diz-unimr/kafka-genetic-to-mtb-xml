package model;

import static org.assertj.core.api.Assertions.assertThat;

import de.unimarburg.diz.kafkagenetictomtbxml.model.MtbPatientInfo;
import java.util.Date;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class MtbPatientInfoTest {

  @ParameterizedTest
  @MethodSource("testData")
  void shouldValidateMtbPatientInfo(MtbPatientInfo mtbPatientInfo, boolean expected) {
    assertThat(mtbPatientInfo.isValid()).isEqualTo(expected);
  }

  public static Stream<Arguments> testData() {
    return Stream.of(
        Arguments.of(
            new MtbPatientInfo(
                "1000001",
                1,
                "1",
                "2001",
                "1a505be7-90a7-41da-b810-5ee308c75b7a",
                "2",
                "H1234-25",
                new Date(),
                "11"),
            true),
        Arguments.of(
            new MtbPatientInfo(
                "1000001",
                1,
                "1",
                "2001",
                "1a505be7-90a7-41da-b810-5ee308c75b7a",
                "2",
                "H/2025/1234",
                new Date(),
                "11"),
            true),
        Arguments.of(
            new MtbPatientInfo(
                "1000001",
                1,
                "1",
                "2001",
                "1a505be7-90a7-41da-b810-5ee308c75b7a",
                "2",
                "NXP.H/2025/12345.100",
                new Date(),
                "11"),
            true),
        Arguments.of(
            new MtbPatientInfo(
                null,
                1,
                "1",
                "2001",
                "1a505be7-90a7-41da-b810-5ee308c75b7a",
                "2",
                "H1234-25",
                new Date(),
                "11"),
            false),
        Arguments.of(
            new MtbPatientInfo(
                "",
                1,
                "1",
                "2001",
                "1a505be7-90a7-41da-b810-5ee308c75b7a",
                "2",
                "H1234-25",
                new Date(),
                "11"),
            false),
        Arguments.of(
            new MtbPatientInfo(
                "1000001",
                1,
                null,
                "2001",
                "1a505be7-90a7-41da-b810-5ee308c75b7a",
                "2",
                "H1234-25",
                new Date(),
                "11"),
            false),
        Arguments.of(
            new MtbPatientInfo(
                "1000001",
                1,
                "  ",
                "2001",
                "1a505be7-90a7-41da-b810-5ee308c75b7a",
                "2",
                "H1234-25",
                new Date(),
                "11"),
            false),
        Arguments.of(
            new MtbPatientInfo(
                "1000001",
                1,
                "1",
                null,
                "1a505be7-90a7-41da-b810-5ee308c75b7a",
                "2",
                "H1234-25",
                new Date(),
                "11"),
            false),
        Arguments.of(
            new MtbPatientInfo(
                "1000001",
                1,
                "1",
                "",
                "1a505be7-90a7-41da-b810-5ee308c75b7a",
                "2",
                "H1234-25",
                new Date(),
                "11"),
            false),
        Arguments.of(
            new MtbPatientInfo("1000001", 1, "1", "2001", null, "2", "H1234-25", new Date(), "11"),
            false),
        Arguments.of(
            new MtbPatientInfo("1000001", 1, "1", "2001", "  ", "2", "H1234-25", new Date(), "11"),
            false),
        Arguments.of(
            new MtbPatientInfo(
                "1000001",
                1,
                "1",
                "2001",
                "1a505be7-90a7-41da-b810-5ee308c75b7a",
                null,
                "H1234-25",
                new Date(),
                "11"),
            false),
        Arguments.of(
            new MtbPatientInfo(
                "1000001",
                1,
                "1",
                "2001",
                "1a505be7-90a7-41da-b810-5ee308c75b7a",
                " ",
                "H1234-25",
                new Date(),
                "11"),
            false),
        Arguments.of(
            new MtbPatientInfo(
                "1000001",
                1,
                "1",
                "2001",
                "1a505be7-90a7-41da-b810-5ee308c75b7a",
                "2",
                null,
                new Date(),
                "11"),
            false),
        Arguments.of(
            new MtbPatientInfo(
                "1000001",
                1,
                "1",
                "2001",
                "1a505be7-90a7-41da-b810-5ee308c75b7a",
                "2",
                "",
                new Date(),
                "11"),
            false),
        Arguments.of(
            new MtbPatientInfo(
                "1000001",
                1,
                "1",
                "2001",
                "1a505be7-90a7-41da-b810-5ee308c75b7a",
                "2",
                "H1234-25",
                null,
                "11"),
            false),
        Arguments.of(
            new MtbPatientInfo(
                "1000001",
                1,
                "1",
                "2001",
                "1a505be7-90a7-41da-b810-5ee308c75b7a",
                "2",
                "H1234-25",
                new Date(),
                null),
            false),
        Arguments.of(
            new MtbPatientInfo(
                "1000001",
                1,
                "1",
                "2001",
                "1a505be7-90a7-41da-b810-5ee308c75b7a",
                "2",
                "H1234-25",
                new Date(),
                ""),
            false),
        Arguments.of(
            new MtbPatientInfo(
                "1000001",
                1,
                "1",
                "2001",
                "1a505be7-90a7-41da-b810-5ee308c75b7a",
                "2",
                "Falsch",
                new Date(),
                "11"),
            false));
  }
}
