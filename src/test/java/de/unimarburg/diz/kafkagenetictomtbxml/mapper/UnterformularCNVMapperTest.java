package de.unimarburg.diz.kafkagenetictomtbxml.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import de.unimarburg.diz.kafkagenetictomtbxml.configuration.HgncConfigurationProperties;
import de.unimarburg.diz.kafkagenetictomtbxml.model.MtbPatientInfo;
import de.unimarburg.diz.kafkagenetictomtbxml.model.mhGuide.Variant;
import de.unimarburg.diz.kafkagenetictomtbxml.model.onkostarXml.DokumentierendeFachabteilung;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class UnterformularCNVMapperTest {

  private UnterformularCNVMapper mapper;

  @BeforeEach
  void setUp() {
    var hgncConfigProperties = new HgncConfigurationProperties();
    hgncConfigProperties.setEnabled(false);

    this.mapper = new UnterformularCNVMapper(hgncConfigProperties);
  }

  @ParameterizedTest(
      name = "Map CNV type ''{3}'' and Copy Number ''{2}'' based on ''{0}'' and ''{1}''")
  @CsvSource({
    "0.10,copy loss,'0,10',L",
    "0.50,copy gain,'0,50',LLG",
    "2.99,copy gain,'2,99',LLG",
    "3.00,copy gain,'3,00',G",
    "87.00,copy gain,'87,00',G",
  })
  void mappingShouldMapCnvTypeAndCopyNumber(
      final String copyNumber,
      final String variantEffect,
      final String expectedCopyNumber,
      final String expectedType)
      throws Exception {
    var variant = createVariant("UNKNOWNGENEFORTEST");
    variant.setCopyNumber(copyNumber);
    variant.setVariantEffect(variantEffect);

    var actual =
        mapper.createXmlUnterformularCNV(
            defaultMtbPatientInfo(), variant, defaultDokumentierendeFachabteilung(), 1);

    var copyNumberVariation =
        actual.getEintraege().stream()
            .filter(eintrag -> eintrag.getFeldname().equals("CopyNumberVariation"))
            .findFirst();
    var cnvTotalCNDouble =
        actual.getEintraege().stream()
            .filter(eintrag -> eintrag.getFeldname().equals("CNVTotalCNDouble"))
            .findFirst();

    assertThat(copyNumberVariation)
        .isPresent()
        .hasValueSatisfying(eintrag -> assertThat(eintrag.getWert()).isEqualTo(expectedType));
    assertThat(cnvTotalCNDouble)
        .isPresent()
        .hasValueSatisfying(eintrag -> assertThat(eintrag.getWert()).isEqualTo(expectedCopyNumber));
  }

  @Nested
  @DisplayName("Tests with disabled HGNC list")
  class DisabledHgncList {
    private UnterformularCNVMapper mapper;

    @BeforeEach
    void setUp() {
      var hgncConfigProperties = new HgncConfigurationProperties();
      hgncConfigProperties.setEnabled(false);

      this.mapper = new UnterformularCNVMapper(hgncConfigProperties);
    }

    @Test
    @DisplayName("Mapping should not use values from HGNC list if disabled")
    void mappingShouldNotUseGeneList() throws Exception {
      var actual =
          mapper.createXmlUnterformularCNV(
              defaultMtbPatientInfo(),
              createVariant("BRAF"),
              defaultDokumentierendeFachabteilung(),
              1);

      var hgncIdEintrag =
          actual.getEintraege().stream()
              .filter(eintrag -> eintrag.getFeldname().equals("CNVHGNCID"))
              .findFirst();
      var ensemblIdEintrag =
          actual.getEintraege().stream()
              .filter(eintrag -> eintrag.getFeldname().equals("CNVENSEMBLID"))
              .findFirst();
      var hgncNameEintrag =
          actual.getEintraege().stream()
              .filter(eintrag -> eintrag.getFeldname().equals("CNVHGNCName"))
              .findFirst();

      assertThat(hgncIdEintrag)
          .isPresent()
          .hasValueSatisfying(eintrag -> assertThat(eintrag.getWert()).isEqualTo(null));
      assertThat(ensemblIdEintrag)
          .isPresent()
          .hasValueSatisfying(eintrag -> assertThat(eintrag.getWert()).isEqualTo(null));
      assertThat(hgncNameEintrag)
          .isPresent()
          .hasValueSatisfying(eintrag -> assertThat(eintrag.getWert()).isEqualTo(null));
    }
  }

  @Nested
  @DisplayName("Tests with enabled HGNC list")
  class EnabledHgncList {
    private UnterformularCNVMapper mapper;

    @BeforeEach
    void setUp() {
      var hgncConfigProperties = new HgncConfigurationProperties();
      hgncConfigProperties.setEnabled(true);

      this.mapper = new UnterformularCNVMapper(hgncConfigProperties);
    }

    @Test
    @DisplayName("Mapping should use values from HGNC list")
    void mappingShouldUseGeneList() throws Exception {
      var actual =
          mapper.createXmlUnterformularCNV(
              defaultMtbPatientInfo(),
              createVariant("BRAF"),
              defaultDokumentierendeFachabteilung(),
              1);

      var hgncIdEintrag =
          actual.getEintraege().stream()
              .filter(eintrag -> eintrag.getFeldname().equals("CNVHGNCID"))
              .findFirst();
      var ensemblIdEintrag =
          actual.getEintraege().stream()
              .filter(eintrag -> eintrag.getFeldname().equals("CNVENSEMBLID"))
              .findFirst();
      var hgncNameEintrag =
          actual.getEintraege().stream()
              .filter(eintrag -> eintrag.getFeldname().equals("CNVHGNCName"))
              .findFirst();

      assertThat(hgncIdEintrag)
          .isPresent()
          .hasValueSatisfying(eintrag -> assertThat(eintrag.getWert()).isEqualTo("HGNC:1097"));
      assertThat(ensemblIdEintrag)
          .isPresent()
          .hasValueSatisfying(
              eintrag -> assertThat(eintrag.getWert()).isEqualTo("ENSG00000157764"));
      assertThat(hgncNameEintrag)
          .isPresent()
          .hasValueSatisfying(
              eintrag ->
                  assertThat(eintrag.getWert())
                      .isEqualTo("B-Raf proto-oncogene, serine/threonine kinase"));
    }

    @Test
    @DisplayName("Mapping should not use unknown gene")
    void mappingShouldUseUnknownGeneValues() throws Exception {
      var actual =
          mapper.createXmlUnterformularCNV(
              defaultMtbPatientInfo(),
              createVariant("UNKNOWNGENEFORTEST"),
              defaultDokumentierendeFachabteilung(),
              1);

      var hgncIdEintrag =
          actual.getEintraege().stream()
              .filter(eintrag -> eintrag.getFeldname().equals("CNVHGNCID"))
              .findFirst();
      var ensemblIdEintrag =
          actual.getEintraege().stream()
              .filter(eintrag -> eintrag.getFeldname().equals("CNVENSEMBLID"))
              .findFirst();
      var hgncNameEintrag =
          actual.getEintraege().stream()
              .filter(eintrag -> eintrag.getFeldname().equals("CNVHGNCName"))
              .findFirst();

      assertThat(hgncIdEintrag)
          .isPresent()
          .hasValueSatisfying(eintrag -> assertThat(eintrag.getWert()).isEqualTo(null));
      assertThat(ensemblIdEintrag)
          .isPresent()
          .hasValueSatisfying(eintrag -> assertThat(eintrag.getWert()).isEqualTo(null));
      assertThat(hgncNameEintrag)
          .isPresent()
          .hasValueSatisfying(eintrag -> assertThat(eintrag.getWert()).isEqualTo(null));
    }
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

  private static Variant createVariant(final String symbol) {
    Variant variant = new Variant();
    variant.setGeneSymbol(symbol);
    variant.setAnnotationJson("{\"GSP2 Count\":\"\"}");
    variant.setChromosomeModification("");
    variant.setCopyNumber("1");
    return variant;
  }
}
