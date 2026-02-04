package de.unimarburg.diz.kafkagenetictomtbxml.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.unimarburg.diz.kafkagenetictomtbxml.configuration.HgncConfigurationProperties;
import de.unimarburg.diz.kafkagenetictomtbxml.hgnc.GeneList;
import de.unimarburg.diz.kafkagenetictomtbxml.model.MtbPatientInfo;
import de.unimarburg.diz.kafkagenetictomtbxml.model.mhGuide.Variant;
import de.unimarburg.diz.kafkagenetictomtbxml.model.onkostarXml.DokumentierendeFachabteilung;
import de.unimarburg.diz.kafkagenetictomtbxml.model.onkostarXml.Eintrag;
import de.unimarburg.diz.kafkagenetictomtbxml.model.onkostarXml.UnterformularCNV;
import de.unimarburg.diz.kafkagenetictomtbxml.util.CurrentDateFormatter;
import de.unimarburg.diz.kafkagenetictomtbxml.util.JsonUtils;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class UnterformularCNVMapper {

    private final HgncConfigurationProperties hgncConfigurationProperties;


    public UnterformularCNVMapper (
            final HgncConfigurationProperties hgncConfigurationProperties
    ){
        this.hgncConfigurationProperties = hgncConfigurationProperties;
    }

    public String extractFromJsonString(String jsonString, String keyToExtract) throws JsonProcessingException {
        return JsonUtils.extractFromJson(jsonString, keyToExtract, String.class);
    }

    public UnterformularCNV createXmlUnterformularCNV(MtbPatientInfo mtbPatientInfo, Variant variant, DokumentierendeFachabteilung dokumentierendeFachabteilung, int exportIDUnterformular) throws JsonProcessingException {
        // MolekulargenetischeUntersuchung: List: Unterformular
        // CNV
        UnterformularCNV unterformularCNV = new UnterformularCNV();
        // To find the export ID a function need to implement, that track the number of unterformular and add the number TODO
        unterformularCNV.setExportID(exportIDUnterformular);
        unterformularCNV.setTumorId(mtbPatientInfo.getTumorId());
        unterformularCNV.setErkrankungExportID(2);
        unterformularCNV.setDokumentierendeFachabteilung(dokumentierendeFachabteilung);
        unterformularCNV.setStartDatum(CurrentDateFormatter.formatCurrentDate());
        unterformularCNV.setFormularName("OS.Molekulargenetische Untersuchung");
        unterformularCNV.setFormularVersion(1);
        unterformularCNV.setProzedurtyp("");

        // SV-Unterformular: Eintrag: Aktivierend
        Eintrag aktivierend = new Eintrag();
        aktivierend.setFeldname("Aktivierend");
        aktivierend.setWert("");

        // SV-Unterformular: Eintrag: Allelfrequenz
        Eintrag allelfrequenz = new Eintrag();
        allelfrequenz.setFeldname("Allelfrequenz");
        allelfrequenz.setWert(variant.getVariantAlleleFrequencyInTumor());

        // SV-Unterformular: Eintrag: Allelzahl
        Eintrag allelzahl = new Eintrag();
        allelzahl.setFeldname("Allelzahl");
        allelzahl.setWert("");

        // SV-Unterformular: Eintrag: Analysemethode
        Eintrag analysemethode = new Eintrag();
        analysemethode.setFeldname("Analysemethode");
        analysemethode.setWert("");
        analysemethode.setFilterkategorie("{}");

        // SV-Unterformular: Eintrag: Bemerkung
        Eintrag bemerkung = new Eintrag();
        bemerkung.setFeldname("Bemerkung");
        bemerkung.setWert("");

        // CNV-Unterformular: Eintrag: CNVBetroffeneGene
        Eintrag cNVBetroffeneGene = new Eintrag();
        cNVBetroffeneGene.setFeldname("CNVBetroffeneGene");
        cNVBetroffeneGene.setWert(variant.getGeneSymbol());

        // CNV-Unterformular: Eintrag: CNVCNA
        Eintrag cNVCNA = new Eintrag();
        cNVCNA.setFeldname("CNVCNA");

        // CNV-Unterformular: Eintrag: CNVCNB
        Eintrag cNVCNB = new Eintrag();
        cNVCNB.setFeldname("CNVCNB");


        // CNV-Unterformular: Eintrag: CNVChromosom
        Eintrag cNVChromosom = new Eintrag();
        cNVChromosom.setFeldname("CNVChromosom");
        cNVChromosom.setWert(variant.getChromosomeModifiedObject());
        cNVChromosom.setFilterkategorie("{}");

        // CNV-Unterformular: Eintrag: CNVENSEMBLID
        Eintrag cNVENSEMBLID = new Eintrag();
        cNVENSEMBLID.setFeldname("CNVENSEMBLID");
        if (this.hgncConfigurationProperties.isEnabled()) {
            GeneList.findBySymbol(variant.getGeneSymbol()).ifPresent(value -> {
                cNVENSEMBLID.setWert(value.getEnsemblId());
            });
        }

        // CNV-Unterformular: Eintrag: CNVEndRange
        Eintrag cNVEndRange = new Eintrag();
        cNVEndRange.setFeldname("CNVEndRange");
        cNVEndRange.setWert(variant.getChromosomeModification());

        // CNV-Unterformular: Eintrag: CNVHGNCID
        Eintrag cNVHGNCID = new Eintrag();
        cNVHGNCID.setFeldname("CNVHGNCID");
        if (this.hgncConfigurationProperties.isEnabled()) {
            GeneList.findBySymbol(variant.getGeneSymbol()).ifPresent(value -> {
                cNVHGNCID.setWert(value.getHgncId());
            });
        }

        // CNV-Unterformular: Eintrag: CNVHGNCName
        Eintrag cNVHGNCName = new Eintrag();
        cNVHGNCName.setFeldname("CNVHGNCName");
        if (this.hgncConfigurationProperties.isEnabled()) {
            GeneList.findBySymbol(variant.getGeneSymbol()).ifPresent(value -> {
                cNVHGNCName.setWert(value.getName());
            });
        }

        // CNV-Unterformular: Eintrag: CNVHGNCSymbol
        Eintrag cNVHGNCSymbol = new Eintrag();
        cNVHGNCSymbol.setFeldname("CNVHGNCSymbol");
        cNVHGNCSymbol.setWert(variant.getGeneSymbol());

        // CNV-Unterformular: Eintrag: CNVNeutralLoH
        Eintrag cNVNeutralLoH = new Eintrag();
        cNVNeutralLoH.setFeldname("CNVNeutralLoH");

        // CNV-Unterformular: Eintrag: CNVRelativeCN
        Eintrag cNVRelativeCN = new Eintrag();
        cNVRelativeCN.setFeldname("CNVRelativeCN");
        var relCopyNumber = variant.getCopyNumber();
        cNVRelativeCN.setWert(relCopyNumber);

        // CNV-Unterformular: Eintrag: CNVReportedFocality
        Eintrag cNVReportedFocality = new Eintrag();
        cNVReportedFocality.setFeldname("CNVReportedFocality");
        cNVReportedFocality.setWert("complete");

        // CNV-Unterformular: Eintrag: CNVStartRange
        Eintrag cNVStartRange = new Eintrag();
        cNVStartRange.setFeldname("CNVStartRange");
        cNVStartRange.setWert(variant.getChromosomeModification());

        // CNV-Unterformular: Eintrag: CNVTotalCN
        Eintrag cNVTotalCN = new Eintrag();
        cNVTotalCN.setFeldname("CNVTotalCN");
        cNVTotalCN.setWert(extractFromJsonString(variant.getAnnotationJson(), "GSP2 Count"));

        // CNV-Unterformular: Eintrag: CNVTotalCNDouble
        Eintrag cNVTotalCNDouble = new Eintrag();
        cNVTotalCNDouble.setFeldname("CNVTotalCNDouble");

        // CNV-Unterformular: Eintrag: Codon
        Eintrag codon = new Eintrag();
        codon.setFeldname("Codon");

        // CNV-Unterformular: Eintrag: CopyNumberVariation
        Eintrag copyNumberVariation = new Eintrag();
        copyNumberVariation.setFeldname("CopyNumberVariation");
        if (null != variant.getVariantEffect() && variant.getVariantEffect().equalsIgnoreCase("copy gain") && Integer.parseInt(relCopyNumber) >= 3) {
            copyNumberVariation.setWert("high-level gain");
        } else if (null != variant.getVariantEffect() && variant.getVariantEffect().equalsIgnoreCase("copy gain") && Integer.parseInt(relCopyNumber) < 3) {
            copyNumberVariation.setWert("low-level gain");
        } else if (null != variant.getVariantEffect() && variant.getVariantEffect().equalsIgnoreCase("copy loss")) {
            copyNumberVariation.setWert("loss");
        }

        // Unterformular: Eintrag: Coverage
        Eintrag coverage = new Eintrag();
        coverage.setFeldname("Coverage");
        coverage.setWert("");


        // Unterformular: Eintrag: Datum
        Eintrag datumCNV = new Eintrag();
        datumCNV.setFeldname("Datum");
        datumCNV.setWert("");

        // CNV-Unterformular: Eintrag: Untersucht
        Eintrag untersucht = new Eintrag();
        untersucht.setFeldname("Untersucht");
        untersucht.setWert(variant.getGeneSymbol());
        untersucht.setFilterkategorie("{}");
        untersucht.setVersion("OS.Molekulargenetik.v1");
        untersucht.setKurztext(variant.getGeneSymbol());

        // Unterformular: Eintrag: Feldname = Ergebnis
        Eintrag ergebnisEintragCNV = new Eintrag();
        ergebnisEintragCNV.setFeldname("Ergebnis");
        ergebnisEintragCNV.setWert("CNV");
        ergebnisEintragCNV.setFilterkategorie("{&quot;OS.MolGenErgebnis.v1&quot;:&quot;Standard&quot;}");
        ergebnisEintragCNV.setVersion("OS.MolGenErgebnis.v1");
        ergebnisEintragCNV.setKurztext("Copy number variation (CNV)");


        // Newly added on 07102024
        // Common-Attribute-Unterformular: Eintrag: Exon
        Eintrag exon = new Eintrag();
        exon.setFeldname("Exon");

        // Common-Attribute-Unterformular: Eintrag: ExonInt
        Eintrag exonInt = new Eintrag();
        exonInt.setFeldname("ExonInt");

        // Common-Attribute-Unterformular: Eintrag: ExonText
        Eintrag exonText = new Eintrag();
        exonText.setFeldname("ExonText");

        // Common-Attribute-Unterformular: Eintrag: ExpressionStoma
        Eintrag expressionStoma = new Eintrag();
        expressionStoma.setFeldname("ExpressionStoma");

        // Common-Attribute-Unterformular: Eintrag: ExpressionTumor
        Eintrag expressionTumor = new Eintrag();
        expressionTumor.setFeldname("ExpressionTumor");

        // Common-Attribute-Unterformular: Eintrag: Genomposition
        Eintrag genomposition = new Eintrag();
        genomposition.setFeldname("Genomposition");

        // Common-Attribute-Unterformular: Eintrag: Interpretation
        Eintrag interpretation = new Eintrag();
        interpretation.setFeldname("Interpretation");

        // TODO: Common-Attribute-Unterformular: Eintrag: METLevel
        // Unterschied neue und alte Version MetLevel - METLevel
        Eintrag mETLevel = new Eintrag();
        mETLevel.setFeldname("METLevel");

        // Common-Attribute-Unterformular: Eintrag: Mutation
        Eintrag mutation = new Eintrag();
        mutation.setFeldname("Mutation");

        // Nicht sicher mit Wert
        // Common-Attribute-Unterformular: Eintrag: Neuanlage
        Eintrag neuanlage = new Eintrag();
        neuanlage.setFeldname("Neuanlage");
        neuanlage.setWert("0");
        neuanlage.setFilterkategorie("{}");
        neuanlage.setVersion("OS.JaNein.v1");
        neuanlage.setKurztext("Nein");

        // Nicht Sicher mit Wert
        // Common-Attribute-Unterformular: Eintrag: Pathogenitaetsklasse
        Eintrag pathogenitaetsklasse = new Eintrag();
        pathogenitaetsklasse.setFeldname("Pathogenitaetsklasse");

        // Common-Attribute-Unterformular: Eintrag: ProteinebeneNomenklatur
        Eintrag proteinebeneNomenklatur = new Eintrag();
        proteinebeneNomenklatur.setFeldname("ProteinebeneNomenklatur");

        // Common-Attribute-Unterformular: Eintrag: Translation
        Eintrag translation = new Eintrag();
        translation.setFeldname("Translation");

        // Common-Attribute-Unterformular: Eintrag: Zygositaet
        Eintrag zygositaet = new Eintrag();
        zygositaet.setFeldname("Zygositaet");

        // Common-Attribute-Unterformular: Eintrag: cDNANomenklatur
        Eintrag cDNANomenklatur = new Eintrag();
        cDNANomenklatur.setFeldname("cDNANomenklatur");

        unterformularCNV.setEintraege(Arrays.asList(aktivierend,
                allelfrequenz, allelzahl, analysemethode, bemerkung, cNVBetroffeneGene, cNVCNA,
                cNVCNB, cNVChromosom, cNVENSEMBLID, cNVEndRange, cNVHGNCID, cNVHGNCName,
                cNVHGNCSymbol, cNVNeutralLoH, cNVRelativeCN, cNVReportedFocality, cNVStartRange, cNVTotalCN, cNVTotalCNDouble, codon,
                copyNumberVariation, coverage, datumCNV, untersucht, ergebnisEintragCNV,
                exon, exonInt, exonText, expressionStoma, expressionTumor, genomposition, interpretation, mETLevel, mutation,
                neuanlage, pathogenitaetsklasse, proteinebeneNomenklatur, translation, zygositaet, cDNANomenklatur));

        unterformularCNV.setHauptTudokEintragExportID(3);
        unterformularCNV.setRevision(1);
        unterformularCNV.setBearbeitungStatus(2);
        return unterformularCNV;
    }
}
