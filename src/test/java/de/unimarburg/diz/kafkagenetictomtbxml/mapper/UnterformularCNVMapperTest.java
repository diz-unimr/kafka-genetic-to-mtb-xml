package de.unimarburg.diz.kafkagenetictomtbxml.mapper;

import de.unimarburg.diz.kafkagenetictomtbxml.configuration.HgncConfigurationProperties;
import de.unimarburg.diz.kafkagenetictomtbxml.model.MtbPatientInfo;
import de.unimarburg.diz.kafkagenetictomtbxml.model.mhGuide.Variant;
import de.unimarburg.diz.kafkagenetictomtbxml.model.onkostarXml.DokumentierendeFachabteilung;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UnterformularCNVMapperTest {

    @Nested
    @DisplayName("Tests with disabled HGNC list")
    class DisabledHgncList {
        private UnterformularCNVMapper mapper;

        @BeforeEach
        void setUp() {
            var hgncConfigProperties = new HgncConfigurationProperties();
            hgncConfigProperties.setEnabled(false);

            this.mapper = new UnterformularCNVMapper("", hgncConfigProperties);
        }

        @Test
        @DisplayName("Mapping should not use values from HGNC list if disabled")
        void mappingShouldNotUseGeneList() throws Exception{
            var actual = mapper.createXmlUnterformularCNV(
                    defaultMtbPatientInfo(),
                    createVariant("BRAF"),
                    defaultDokumentierendeFachabteilung(),
                    1
            );

            var hgncIdEintrag = actual.getEintraege().stream().filter(eintrag -> eintrag.getFeldname().equals("CNVHGNCID")).findFirst();
            var ensemblIdEintrag = actual.getEintraege().stream().filter(eintrag -> eintrag.getFeldname().equals("CNVENSEMBLID")).findFirst();
            var hgncNameEintrag = actual.getEintraege().stream().filter(eintrag -> eintrag.getFeldname().equals("CNVHGNCName")).findFirst();

            assertThat(hgncIdEintrag).isPresent().hasValueSatisfying(eintrag -> assertThat(eintrag.getWert()).isEqualTo(null));
            assertThat(ensemblIdEintrag).isPresent().hasValueSatisfying(eintrag -> assertThat(eintrag.getWert()).isEqualTo(null));
            assertThat(hgncNameEintrag).isPresent().hasValueSatisfying(eintrag -> assertThat(eintrag.getWert()).isEqualTo(null));
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

            this.mapper = new UnterformularCNVMapper("", hgncConfigProperties);
        }

        @Test
        @DisplayName("Mapping should use values from HGNC list")
        void mappingShouldUseGeneList() throws Exception {
            var actual = mapper.createXmlUnterformularCNV(
                    defaultMtbPatientInfo(),
                    createVariant("BRAF"),
                    defaultDokumentierendeFachabteilung(),
                    1
            );

            var hgncIdEintrag = actual.getEintraege().stream().filter(eintrag -> eintrag.getFeldname().equals("CNVHGNCID")).findFirst();
            var ensemblIdEintrag = actual.getEintraege().stream().filter(eintrag -> eintrag.getFeldname().equals("CNVENSEMBLID")).findFirst();
            var hgncNameEintrag = actual.getEintraege().stream().filter(eintrag -> eintrag.getFeldname().equals("CNVHGNCName")).findFirst();

            assertThat(hgncIdEintrag).isPresent().hasValueSatisfying(eintrag -> assertThat(eintrag.getWert()).isEqualTo("HGNC:1097"));
            assertThat(ensemblIdEintrag).isPresent().hasValueSatisfying(eintrag -> assertThat(eintrag.getWert()).isEqualTo("ENSG00000157764"));
            assertThat(hgncNameEintrag).isPresent().hasValueSatisfying(eintrag -> assertThat(eintrag.getWert()).isEqualTo("B-Raf proto-oncogene, serine/threonine kinase"));
        }

        @Test
        @DisplayName("Mapping should not use unknown gene")
        void mappingShouldUseUnknownGeneValues() throws  Exception {
            var actual = mapper.createXmlUnterformularCNV(
                    defaultMtbPatientInfo(),
                    createVariant("UNKNOWNGENEFORTEST"),
                    defaultDokumentierendeFachabteilung(),
                    1
            );

            var hgncIdEintrag = actual.getEintraege().stream().filter(eintrag -> eintrag.getFeldname().equals("CNVHGNCID")).findFirst();
            var ensemblIdEintrag = actual.getEintraege().stream().filter(eintrag -> eintrag.getFeldname().equals("CNVENSEMBLID")).findFirst();
            var hgncNameEintrag = actual.getEintraege().stream().filter(eintrag -> eintrag.getFeldname().equals("CNVHGNCName")).findFirst();

            assertThat(hgncIdEintrag).isPresent().hasValueSatisfying(eintrag -> assertThat(eintrag.getWert()).isEqualTo(null));
            assertThat(ensemblIdEintrag).isPresent().hasValueSatisfying(eintrag -> assertThat(eintrag.getWert()).isEqualTo(null));
            assertThat(hgncNameEintrag).isPresent().hasValueSatisfying(eintrag -> assertThat(eintrag.getWert()).isEqualTo(null));
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

    private static Variant createVariant(
            final String symbol
    ) {
        Variant variant = new Variant();
        variant.setGeneSymbol(symbol);
        variant.setAnnotationJson("{\"GSP2 Count\":\"\"}");
        variant.setChromosomeModification("");
        variant.setCopyNumber("1");
        return variant;
    }

}
