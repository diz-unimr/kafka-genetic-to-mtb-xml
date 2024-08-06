package de.unimarburg.diz.kafkagenetictomtbxml.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.unimarburg.diz.kafkagenetictomtbxml.model.MtbPidInfo;
import de.unimarburg.diz.kafkagenetictomtbxml.model.mhGuide.MHGuide;
import de.unimarburg.diz.kafkagenetictomtbxml.model.mhGuide.VariantLongList;
import de.unimarburg.diz.kafkagenetictomtbxml.model.onkostarXml.DokumentierendeFachabteilung;
import de.unimarburg.diz.kafkagenetictomtbxml.model.onkostarXml.Eintrag;
import de.unimarburg.diz.kafkagenetictomtbxml.model.onkostarXml.UnterformularCNV;
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
        var value = jsonNode.get(keyToExtract).asText();
        return value;
    }
    public UnterformularCNV createXmlUnterformularCNV (VariantLongList variantLongList, DokumentierendeFachabteilung dokumentierendeFachabteilung) throws JsonProcessingException {
        // MolekulargenetischeUntersuchung: List: Unterformular
        // CNV
        UnterformularCNV unterformularCNV = new UnterformularCNV();
        unterformularCNV.setExportID(1);
        unterformularCNV.setTumorId("1");
        unterformularCNV.setDokumentierendeFachabteilung(dokumentierendeFachabteilung);
        unterformularCNV.setStartDatum("2023-08-10");
        unterformularCNV.setFormularName("OS.Molekulargenetische Untersuchung");
        unterformularCNV.setFormularVersion(1);
        unterformularCNV.setProzedurtyp("Beobachtung");

        // SV-Unterformular: Eintrag: Aktivierend
        Eintrag aktivierend = new Eintrag();
        aktivierend.setFeldname("Aktivierend");
        aktivierend.setWert("");

        // SV-Unterformular: Eintrag: Allelfrequenz
        Eintrag allelfrequenz = new Eintrag();
        allelfrequenz.setFeldname("Allelfrequenz");
        allelfrequenz.setWert("");

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

        // SV-Unterformular: Eintrag: Datum
        Eintrag datumCNV = new Eintrag();
        datumCNV.setFeldname("Datum");
        datumCNV.setWert("");

        // SV-Unterformular: Eintrag: Dokumentation
        Eintrag dokumentationUnterformular = new Eintrag();
        dokumentationUnterformular.setFeldname("Dokumentation");
        dokumentationUnterformular.setWert("ERW");
        dokumentationUnterformular.setFilterkategorie("{}");
        dokumentationUnterformular.setVersion("OS.MolDokumentation.v1");
        dokumentationUnterformular.setKurztext("Erweitert");

        // Unterformular: Eintrag: Feldname = Ergebnis
        Eintrag ergebnisEintragCNV = new Eintrag();
        ergebnisEintragCNV.setFeldname("Ergebnis");
        ergebnisEintragCNV.setWert("CNV");
        ergebnisEintragCNV.setFilterkategorie("{&quot;OS.MolGenErgebnis.v1&quot;:&quot;Standard&quot;}");
        ergebnisEintragCNV.setVersion("OS.MolGenErgebnis.v1");
        ergebnisEintragCNV.setKurztext("Copy number variation (CNV)");

        // CNV-Unterformular: Eintrag: CNVBetroffeneGene
        Eintrag cNVBetroffeneGene = new Eintrag();
        cNVBetroffeneGene.setFeldname("BetroffeneGene");
        cNVBetroffeneGene.setWert(variantLongList.getGeneSymbol());

        // CNV-Unterformular: Eintrag: CNVCNA
        Eintrag cNVCNA = new Eintrag();
        cNVCNA.setFeldname("CNVCNA");

        // CNV-Unterformular: Eintrag: CNVCNB
        Eintrag cNVCNB = new Eintrag();
        cNVCNB.setFeldname("CNVCNB");

        // CNV-Unterformular: Eintrag: Untersucht
        Eintrag untersucht = new Eintrag();
        untersucht.setFeldname("Untersucht");
        untersucht.setWert("MSH3");
        untersucht.setFilterkategorie("{}");
        untersucht.setVersion("OS.Molekulargenetik.v1");
        untersucht.setKurztext("MSH3");

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
        unterformularCNV.setEintraege(Arrays.asList(dokumentationUnterformular, ergebnisEintragCNV,aktivierend,
                allelfrequenz, allelzahl, analysemethode, bemerkung, datumCNV, cNVBetroffeneGene,
                cNVCNB, cNVCNA, untersucht, cNVChromosom, cNVENSEMBLID, cNVEndRange, cNVHGNCID, cNVHGNCName,
                cNVHGNCSymbol, cNVNeutralLoH, cNVRelativeCN, cNVReportedFocality, cNVStartRange, cNVTotalCN, cNVTotalCNDouble, codon,
                copyNumberVariation));

        return unterformularCNV;
    }

}
