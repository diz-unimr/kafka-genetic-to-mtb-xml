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

class UnterformularSVMapperTest {

    private UnterformularSVMapper mapper;

    @BeforeEach
    void setUp() {
        var hgncConfigProperties = new HgncConfigurationProperties();
        this.mapper = new UnterformularSVMapper("", hgncConfigProperties);
    }

    @Test
    void shouldParseStartEndWithNullValue() {
        assertThat(mapper.parseStartEnd(null)).isNull();
    }

    @Test
    void shouldParseRefAlleWithNullValue() {
        assertThat(mapper.parserRefAlle(null)).isNull();
    }

    @Test
    void shouldParseAltAlleWithNullValue() {
        assertThat(mapper.parseAltAlle(null)).isNull();
    }

    @Test
    void shouldParseDnaChangeWithNullValue() {
        assertThat(mapper.parseDnaChange(null)).isNull();
    }

    @Test
    void shouldParseAminoAcidChangeWithNullValue() {
        assertThat(mapper.parseAminoAcidChange(null)).isNull();
    }

    @Nested
    @DisplayName("Tests with disabled HGNC list")
    class DisabledHgncList {
        private UnterformularSVMapper mapper;

        @BeforeEach
        void setUp() {
            var hgncConfigProperties = new HgncConfigurationProperties();
            hgncConfigProperties.setEnabled(false);

            this.mapper = new UnterformularSVMapper("", hgncConfigProperties);
        }

        @Test
        @DisplayName("Mapping should not use values from HGNC list if disabled")
        void mappingShouldNotUseGeneList() {
            var actual = mapper.createXmlUnterformularSV(
                    defaultMtbPatientInfo(),
                    createVariant("BRAF"),
                    defaultDokumentierendeFachabteilung(),
                    1
            );

            var hgncIdEintrag = actual.getEintraege().stream().filter(eintrag -> eintrag.getFeldname().equals("EVHGNCID")).findFirst();
            var ensemblIdEintrag = actual.getEintraege().stream().filter(eintrag -> eintrag.getFeldname().equals("EVENSEMBLID")).findFirst();
            var hgncNameEintrag = actual.getEintraege().stream().filter(eintrag -> eintrag.getFeldname().equals("EVHGNCName")).findFirst();

            assertThat(hgncIdEintrag).isPresent().hasValueSatisfying(eintrag -> assertThat(eintrag.getWert()).isEqualTo(""));
            assertThat(ensemblIdEintrag).isPresent().hasValueSatisfying(eintrag -> assertThat(eintrag.getWert()).isEqualTo(""));
            assertThat(hgncNameEintrag).isPresent().hasValueSatisfying(eintrag -> assertThat(eintrag.getWert()).isEqualTo(null));
        }
    }

    @Nested
    @DisplayName("Tests with enabled HGNC list")
    class EnabledHgncList {
        private UnterformularSVMapper mapper;

        @BeforeEach
        void setUp() {
            var hgncConfigProperties = new HgncConfigurationProperties();
            hgncConfigProperties.setEnabled(true);

            this.mapper = new UnterformularSVMapper("", hgncConfigProperties);
        }

        @Test
        @DisplayName("Mapping should use values from HGNC list")
        void mappingShouldUseGeneList() {
            var actual = mapper.createXmlUnterformularSV(
                    defaultMtbPatientInfo(),
                    createVariant("BRAF"),
                    defaultDokumentierendeFachabteilung(),
                    1
            );

            var hgncIdEintrag = actual.getEintraege().stream().filter(eintrag -> eintrag.getFeldname().equals("EVHGNCID")).findFirst();
            var ensemblIdEintrag = actual.getEintraege().stream().filter(eintrag -> eintrag.getFeldname().equals("EVENSEMBLID")).findFirst();
            var hgncNameEintrag = actual.getEintraege().stream().filter(eintrag -> eintrag.getFeldname().equals("EVHGNCName")).findFirst();

            assertThat(hgncIdEintrag).isPresent().hasValueSatisfying(eintrag -> assertThat(eintrag.getWert()).isEqualTo("HGNC:1097"));
            assertThat(ensemblIdEintrag).isPresent().hasValueSatisfying(eintrag -> assertThat(eintrag.getWert()).isEqualTo("ENSG00000157764"));
            assertThat(hgncNameEintrag).isPresent().hasValueSatisfying(eintrag -> assertThat(eintrag.getWert()).isEqualTo("B-Raf proto-oncogene, serine/threonine kinase"));
        }

        @Test
        @DisplayName("Mapping should not use unknown gene")
        void mappingShouldUseUnknownGeneValues() {
            var actual = mapper.createXmlUnterformularSV(
                    defaultMtbPatientInfo(),
                    createVariant("UNKNOWNGENEFORTEST"),
                    defaultDokumentierendeFachabteilung(),
                    1
            );

            var hgncIdEintrag = actual.getEintraege().stream().filter(eintrag -> eintrag.getFeldname().equals("EVHGNCID")).findFirst();
            var ensemblIdEintrag = actual.getEintraege().stream().filter(eintrag -> eintrag.getFeldname().equals("EVENSEMBLID")).findFirst();
            var hgncNameEintrag = actual.getEintraege().stream().filter(eintrag -> eintrag.getFeldname().equals("EVHGNCName")).findFirst();

            assertThat(hgncIdEintrag).isPresent().hasValueSatisfying(eintrag -> assertThat(eintrag.getWert()).isEqualTo(""));
            assertThat(ensemblIdEintrag).isPresent().hasValueSatisfying(eintrag -> assertThat(eintrag.getWert()).isEqualTo(""));
            assertThat(hgncNameEintrag).isPresent().hasValueSatisfying(eintrag -> assertThat(eintrag.getWert()).isEqualTo(null));
        }
    }

    @Test
    @DisplayName("Mapping should include cDNA Nomenklatur")
    void mappingShouldUseCDnaNomenklatur() {
        var variant = createVariant("UNKNOWNGENEFORTEST");
        variant.setTranscriptHgvsModifiedObject("c.123A>G");

        var actual = mapper.createXmlUnterformularSV(
                defaultMtbPatientInfo(),
                variant,
                defaultDokumentierendeFachabteilung(),
                1
        );

        var cDnaEintrag = actual.getEintraege().stream().filter(eintrag -> eintrag.getFeldname().equals("cDNANomenklatur")).findFirst();

        assertThat(cDnaEintrag).isPresent().hasValueSatisfying(eintrag -> assertThat(eintrag.getWert()).isEqualTo("c.123A>G"));
    }

    @Test
    @DisplayName("Mapping should include cDNA Nomenklatur")
    void mappingShouldUseCDnaNomenklaturTranscriptHgvsModifiedObject() {
        var variant = createVariant("UNKNOWNGENEFORTEST");
        variant.setTranscriptHgvsModifiedObject("c.123A>G");

        var actual = mapper.createXmlUnterformularSV(
                defaultMtbPatientInfo(),
                variant,
                defaultDokumentierendeFachabteilung(),
                1
        );

        var cDnaEintrag = actual.getEintraege().stream().filter(eintrag -> eintrag.getFeldname().equals("cDNANomenklatur")).findFirst();

        assertThat(cDnaEintrag).isPresent().hasValueSatisfying(eintrag -> assertThat(eintrag.getWert()).isEqualTo("c.123A>G"));
    }

    @Test
    @DisplayName("Mapping should include cDNA Nomenklatur")
    void mappingShouldUseCDnaNomenklaturFromTranscriptHgvs() {
        var variant = createVariant("UNKNOWNGENEFORTEST");
        variant.setTranscriptHgvs("ENST000012345.6 c.123A>G");

        var actual = mapper.createXmlUnterformularSV(
                defaultMtbPatientInfo(),
                variant,
                defaultDokumentierendeFachabteilung(),
                1
        );

        var cDnaEintrag = actual.getEintraege().stream().filter(eintrag -> eintrag.getFeldname().equals("cDNANomenklatur")).findFirst();

        assertThat(cDnaEintrag).isPresent().hasValueSatisfying(eintrag -> assertThat(eintrag.getWert()).isEqualTo("c.123A>G"));
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
        return variant;
    }

}
