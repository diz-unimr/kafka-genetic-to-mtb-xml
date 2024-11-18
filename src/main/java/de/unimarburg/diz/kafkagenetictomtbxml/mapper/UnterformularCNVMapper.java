package de.unimarburg.diz.kafkagenetictomtbxml.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.unimarburg.diz.kafkagenetictomtbxml.model.MtbPatientInfo;
import de.unimarburg.diz.kafkagenetictomtbxml.model.mhGuide.VariantLongList;
import de.unimarburg.diz.kafkagenetictomtbxml.model.onkostarXml.DokumentierendeFachabteilung;
import de.unimarburg.diz.kafkagenetictomtbxml.model.onkostarXml.Eintrag;
import de.unimarburg.diz.kafkagenetictomtbxml.model.onkostarXml.UnterformularCNV;
import de.unimarburg.diz.kafkagenetictomtbxml.util.CurrentDateFormatter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class UnterformularCNVMapper {


    private String  reportedFocality;


    public  UnterformularCNVMapper (@Value("${metadata.ngsReports.reportedFocality}") String reportedFocality){

        this.reportedFocality = reportedFocality;
    }

    public  String extractStringFromJson(String jsonAnotation, String keyToExtract) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        var jsonNode = objectMapper.readTree(jsonAnotation);
        return jsonNode.get(keyToExtract).asText();
    }
    public UnterformularCNV createXmlUnterformularCNV (MtbPatientInfo mtbPatientInfo, VariantLongList variantLongList, DokumentierendeFachabteilung dokumentierendeFachabteilung, int exportIDUnterformular) throws JsonProcessingException {
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
        allelfrequenz.setWert(variantLongList.getVariantAlleleFrequencyInTumor());

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
        cNVBetroffeneGene.setWert(variantLongList.getGeneSymbol());

        // CNV-Unterformular: Eintrag: CNVCNA
        Eintrag cNVCNA = new Eintrag();
        cNVCNA.setFeldname("CNVCNA");

        // CNV-Unterformular: Eintrag: CNVCNB
        Eintrag cNVCNB = new Eintrag();
        cNVCNB.setFeldname("CNVCNB");


        // CNV-Unterformular: Eintrag: CNVChromosom
        Eintrag cNVChromosom = new Eintrag();
        cNVChromosom.setFeldname("CNVChromosom");
        cNVChromosom.setWert(variantLongList.getChromosomeModifiedObject());
        cNVChromosom.setFilterkategorie("{}");

        // CNV-Unterformular: Eintrag: CNVENSEMBLID
        Eintrag cNVENSEMBLID = new Eintrag();
        cNVENSEMBLID.setFeldname("CNVENSEMBLID");

        // CNV-Unterformular: Eintrag: CNVEndRange
        Eintrag cNVEndRange = new Eintrag();
        cNVEndRange.setFeldname("CNVEndRange");
        cNVEndRange.setWert(variantLongList.getChromosomeModification());

        // CNV-Unterformular: Eintrag: CNVHGNCID
        Eintrag cNVHGNCID = new Eintrag();
        cNVHGNCID.setFeldname("CNVHGNCID");

        // CNV-Unterformular: Eintrag: CNVHGNCName
        Eintrag cNVHGNCName = new Eintrag();
        cNVHGNCName.setFeldname("CNVHGNCName");

        // CNV-Unterformular: Eintrag: CNVHGNCSymbol
        Eintrag cNVHGNCSymbol = new Eintrag();
        cNVHGNCSymbol.setFeldname("CNVHGNCSymbol");

        // CNV-Unterformular: Eintrag: CNVNeutralLoH
        Eintrag cNVNeutralLoH = new Eintrag();
        cNVNeutralLoH.setFeldname("CNVNeutralLoH");

        // CNV-Unterformular: Eintrag: CNVRelativeCN
        Eintrag cNVRelativeCN = new Eintrag();
        cNVRelativeCN.setFeldname("CNVRelativeCN");
        var relCopyNumber = variantLongList.getCopyNumber();
        cNVRelativeCN.setWert(relCopyNumber);

        // CNV-Unterformular: Eintrag: CNVReportedFocality
        Eintrag cNVReportedFocality = new Eintrag();
        cNVReportedFocality.setFeldname("CNVReportedFocality");
        cNVReportedFocality.setWert("complete");

        // CNV-Unterformular: Eintrag: CNVStartRange
        Eintrag cNVStartRange = new Eintrag();
        cNVStartRange.setFeldname("CNVStartRange");
        cNVStartRange.setWert(variantLongList.getChromosomeModification());

        // CNV-Unterformular: Eintrag: CNVTotalCN
        Eintrag cNVTotalCN = new Eintrag();
        cNVTotalCN.setFeldname("CNVTotalCN");
        cNVTotalCN.setWert(extractStringFromJson(variantLongList.getAnnotationJson(), "GSP2 Count:" ));

        // CNV-Unterformular: Eintrag: CNVTotalCNDouble
        Eintrag cNVTotalCNDouble = new Eintrag();
        cNVTotalCNDouble.setFeldname("CNVTotalCNDouble");

        // CNV-Unterformular: Eintrag: Codon
        Eintrag codon = new Eintrag();
        codon.setFeldname("Codon");

        // CNV-Unterformular: Eintrag: CopyNumberVariation
        Eintrag copyNumberVariation = new Eintrag();
        copyNumberVariation.setFeldname("CopyNumberVariation");
        if (Integer.parseInt(relCopyNumber) > 1) {
            copyNumberVariation.setWert("high-level gain");
        }
        else if (Integer.parseInt(relCopyNumber) < 1) {
            copyNumberVariation.setWert("high-level loss");
        }

        // Unterformular: Eintrag: Coverage
        Eintrag coverage = new Eintrag();
        coverage.setFeldname("Coverage");
        coverage.setWert("");


        // Unterformular: Eintrag: Datum
        Eintrag datumCNV = new Eintrag();
        datumCNV.setFeldname("Datum");
        datumCNV.setWert("");

        // Unterformular: Eintrag: Dokumentation
        Eintrag dokumentationUnterformular = new Eintrag();
        dokumentationUnterformular.setFeldname("Dokumentation");
        dokumentationUnterformular.setWert("ERW");
        dokumentationUnterformular.setFilterkategorie("{}");
        dokumentationUnterformular.setVersion("OS.MolDokumentation.v1");
        dokumentationUnterformular.setKurztext("Erweitert");


        // CNV-Unterformular: Eintrag: Untersucht
        Eintrag untersucht = new Eintrag();
        untersucht.setFeldname("Untersucht");
        untersucht.setWert(variantLongList.getGeneSymbol());
        untersucht.setFilterkategorie("{}");
        untersucht.setVersion("OS.Molekulargenetik.v1");
        untersucht.setKurztext(variantLongList.getGeneSymbol());

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
        pathogenitaetsklasse.setWert("1");
        pathogenitaetsklasse.setFilterkategorie("{}");
        pathogenitaetsklasse.setVersion("OS.MolGenPathogenitätsklasse.v1");
        pathogenitaetsklasse.setKurztext("1= Normvariante ohne klinische Relevanz (benign)");

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
                copyNumberVariation, coverage, datumCNV, dokumentationUnterformular, untersucht, ergebnisEintragCNV,
                exon, exonInt, exonText, expressionStoma, expressionTumor, genomposition, interpretation, mETLevel, mutation,
                neuanlage, pathogenitaetsklasse, proteinebeneNomenklatur, translation, zygositaet,cDNANomenklatur));

        unterformularCNV.setHauptTudokEintragExportID(3);
        unterformularCNV.setRevision(1);
        unterformularCNV.setBearbeitungStatus(2);
        return unterformularCNV;
    }
}
