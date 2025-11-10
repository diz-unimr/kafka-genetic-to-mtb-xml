package de.unimarburg.diz.kafkagenetictomtbxml.mapper;

import de.unimarburg.diz.kafkagenetictomtbxml.configuration.HgncConfigurationProperties;
import de.unimarburg.diz.kafkagenetictomtbxml.hgnc.GeneList;
import de.unimarburg.diz.kafkagenetictomtbxml.model.DnaChange;
import de.unimarburg.diz.kafkagenetictomtbxml.model.MtbPatientInfo;
import de.unimarburg.diz.kafkagenetictomtbxml.model.mhGuide.Variant;
import de.unimarburg.diz.kafkagenetictomtbxml.model.onkostarXml.DokumentierendeFachabteilung;
import de.unimarburg.diz.kafkagenetictomtbxml.model.onkostarXml.Eintrag;
import de.unimarburg.diz.kafkagenetictomtbxml.model.onkostarXml.UnterformularSV;
import de.unimarburg.diz.kafkagenetictomtbxml.util.CurrentDateFormatter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.Arrays;

@Component
public class UnterformularSVMapper {

    private String interpolationSystem;
    private HgncConfigurationProperties hgncConfigurationProperties;

    public  UnterformularSVMapper(
            @Value("${metadata.ngsReports.interpolationSystem}") String interpolationSystem,
            HgncConfigurationProperties hgncConfigurationProperties
    ){
        this.interpolationSystem = interpolationSystem;
        this.hgncConfigurationProperties = hgncConfigurationProperties;
    }

    public String parseStartEnd(String chromosomalModi){
        if (null == chromosomalModi) {
            return null;
        }

        String regex = "g\\.(\\d+)([A-Za-z])>([A-Za-z])";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(chromosomalModi);
        String startEnd = "";
        if (matcher.find()) {
            startEnd = matcher.group(1);

        }
        return startEnd;
    }

    public String parserRefAlle(String chromosomalModi){
        if (null == chromosomalModi) {
            return null;
        }

        String regex = "g\\.(\\d+)([A-Za-z])>([A-Za-z])";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(chromosomalModi);
        String refAlle = "";
        if (matcher.find()) {
            refAlle = matcher.group(2);

        }
        return refAlle;
    }

    public String parseAltAlle(String chromosomalModi){
        if (null == chromosomalModi) {
            return null;
        }

        String regex = "g\\.(\\d+)([A-Za-z])>([A-Za-z])";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(chromosomalModi);
        String altAlle = "";
        if (matcher.find()) {
            altAlle = matcher.group(3);
        }
        return altAlle;
    }

    public String parseDnaChange(String chromosomalModi){
        if (null == chromosomalModi) {
            return null;
        }

        String regex = "g\\.(\\d+)([A-Za-z].*)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(chromosomalModi);
        String dnaChange = "";
        if (matcher.find()) {
            dnaChange = matcher.group(1);

        }
       return dnaChange;
    }

    public String parseAminoAcidChange(String chromosomalModi){
        if (null == chromosomalModi) {
            return null;
        }

        String regex = "p\\.([A-Za-z].*)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(chromosomalModi);
        String aminoChange = "";
        if (matcher.find()) {
            aminoChange = matcher.group(1);
        }
        return aminoChange;
    }

    public UnterformularSV createXmlUnterformularSV (MtbPatientInfo mtbPatientInfo, Variant variant, DokumentierendeFachabteilung dokumentierendeFachabteilung, int startExportIDUNterformular) {
        UnterformularSV unterformularSV = new UnterformularSV();
        // To find the export ID a function need to implement, that track the number of unterformular and add the number TODO
        unterformularSV.setExportID(startExportIDUNterformular);
        unterformularSV.setTumorId(mtbPatientInfo.getTumorId());
        unterformularSV.setErkrankungExportID(2);
        unterformularSV.setDokumentierendeFachabteilung(dokumentierendeFachabteilung);
        unterformularSV.setStartDatum(CurrentDateFormatter.formatCurrentDate());
        unterformularSV.setFormularName("OS.Molekulargenetische Untersuchung");
        unterformularSV.setFormularVersion(1);
        unterformularSV.setProzedurtyp("");

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

        // SV-Unterformular: Eintrag: Datum
        // Oder_date
        Eintrag datumSV = new Eintrag();
        datumSV.setFeldname("Datum");
        datumSV.setWert("");

        // SV-Unterformular: Eintrag: EVCOSMICID
        // NA
        Eintrag eVCOSMICID = new Eintrag();
        eVCOSMICID.setFeldname("EVCOSMICID");
        eVCOSMICID.setWert("");

        // SV-Unterformular: Eintrag: EVChromosom
        Eintrag eVChromosom = new Eintrag();
        eVChromosom.setFeldname("EVChromosom");
        eVChromosom.setWert(variant.getChromosomeModifiedObject());
        eVChromosom.setFilterkategorie("{}");

        // SV-Unterformular: Eintrag: EVENSEMBLID
        // Wird automatisch aus der EVHGNCSymbol zugeordnet
        Eintrag eVENSEMBLID = new Eintrag();
        eVENSEMBLID.setFeldname("EVENSEMBLID");
        eVENSEMBLID.setWert("");
        if (this.hgncConfigurationProperties.isEnabled()) {
            GeneList.findBySymbol(variant.getGeneSymbol()).ifPresent(value -> {
                eVENSEMBLID.setWert(value.getEnsemblId());
            });
        }

        // SV-Unterformular: Eintrag: EVHGNCID
        Eintrag eVHGNCID = new Eintrag();
        eVHGNCID.setFeldname("EVHGNCID");
        eVHGNCID.setWert("");
        if (this.hgncConfigurationProperties.isEnabled()) {
            GeneList.findBySymbol(variant.getGeneSymbol()).ifPresent(value -> {
                eVHGNCID.setWert(value.getHgncId());
            });
        }

        // SV-Unterformular: Eintrag: EVHGNCName
        Eintrag eVHGNCName = new Eintrag();
        eVHGNCName.setFeldname("EVHGNCName");
        if (this.hgncConfigurationProperties.isEnabled()) {
            GeneList.findBySymbol(variant.getGeneSymbol()).ifPresent(value -> {
                eVHGNCName.setWert(value.getName());
            });
        }

        // SV-Unterformular: Eintrag: EVHGNCSymbol
        Eintrag eVHGNCSymbol = new Eintrag();
        eVHGNCSymbol.setFeldname("EVHGNCSymbol");
        eVHGNCSymbol.setWert(variant.getGeneSymbol());

        // SV-Unterformular: Eintrag: EVNMNummer
        Eintrag eVNMNummer = new Eintrag();
        eVNMNummer.setFeldname("EVNMNummer");

        // SV-Unterformular: Eintrag: EVReadDepth
        Eintrag eVReadDepth = new Eintrag();
        eVReadDepth.setFeldname("EVReadDepth");
        eVReadDepth.setWert(variant.getTotalReadsInTumor());

        // SV-Unterformular: Eintrag: EVRefNucleotide
        Eintrag eVRefNucleotide = new Eintrag();
        eVRefNucleotide.setFeldname("EVRefNucleotide");

        // SV-Unterformular: Eintrag: "EVAltNucleotide
        Eintrag eVAltNucleotide  = new Eintrag();
        eVAltNucleotide.setFeldname("EVAltNucleotide");

        // SV-Unterformular: Eintrag: EVStart
        Eintrag eVStart = new Eintrag();
        eVStart.setFeldname("EVStart");

        // SV-Unterformular: Eintrag: EVEnde
        Eintrag eVEnde = new Eintrag();
        eVEnde.setFeldname("EVEnde");

        // Parse value into DNA change
        final var dnaChange = DnaChange.parse(variant.getChromosomeModification());
        if (null != dnaChange) {
            // Set missing values
            eVRefNucleotide.setWert(dnaChange.getRefAllele());
            eVAltNucleotide.setWert(dnaChange.getAltAllele());

            eVStart.setWert(dnaChange.getStart());
            eVEnde.setWert(dnaChange.getEnd());
        }

        // SV-Unterformular: Eintrag: Untersucht
        Eintrag untersucht = new Eintrag();
        untersucht.setFeldname("Untersucht");
        untersucht.setWert(variant.getGeneSymbol());
        untersucht.setFilterkategorie("{}");
        untersucht.setVersion("OS.Molekulargenetik.v1");
        untersucht.setKurztext(variant.getGeneSymbol());

        // SV-Unterformular: EVdbSNPID
        Eintrag eVdbSNPID = new Eintrag();
        eVdbSNPID.setFeldname("EVdbSNPID");
        eVdbSNPID.setWert(variant.getDbsnp());

        Eintrag ergebnisEintragSV = new Eintrag();
        ergebnisEintragSV.setFeldname("Ergebnis");
        ergebnisEintragSV.setWert("P");
        ergebnisEintragSV.setFilterkategorie("{&quot;OS.MolGenErgebnis.v1&quot;:&quot;Standard&quot;}");
        ergebnisEintragSV.setVersion("OS.MolGenErgebnis.v1");
        ergebnisEintragSV.setKurztext("Einfache Variante (Mutation / positiv)");


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
        interpretation.setWert(variant.getClassificationName());

        // TODO:Common-Attribute-Unterformular: Eintrag: METLevel
        // Unterschied neue und alte MetLevel - METLevel
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


        // Common-Attribute-Unterformular: Eintrag: Pathogenitaetsklasse
        Eintrag pathogenitaetsklasse = new Eintrag();
        pathogenitaetsklasse.setFeldname("Pathogenitaetsklasse");
        pathogenitaetsklasse.setWert(variant.getVariantClassification());
        pathogenitaetsklasse.setFilterkategorie("{}");
        pathogenitaetsklasse.setVersion("OS.MolGenPathogenitätsklasse.v1");
        pathogenitaetsklasse.setKurztext(variant.getClassificationName());

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
        if (null != variant.getTranscriptHgvsModifiedObject() && !variant.getTranscriptHgvsModifiedObject().isBlank()) {
            cDNANomenklatur.setWert(variant.getTranscriptHgvsModifiedObject());
        } else if (null != variant.getTranscriptHgvs() &&  !variant.getTranscriptHgvs().isBlank()) {
            cDNANomenklatur.setWert(extractCDnaNomenklatur(variant.getTranscriptHgvs()));
        }


        unterformularSV.setEintraege(Arrays.asList(aktivierend,
                allelfrequenz, allelzahl, analysemethode, bemerkung, datumSV, eVAltNucleotide,
                eVCOSMICID, eVChromosom, eVENSEMBLID,
                eVEnde, eVHGNCID, eVHGNCName, eVHGNCSymbol, eVNMNummer, eVReadDepth, eVRefNucleotide, eVStart, untersucht, eVdbSNPID,
                ergebnisEintragSV, exon, exonInt, exonText, expressionStoma, expressionTumor, genomposition, interpretation, mETLevel, mutation,
                neuanlage, pathogenitaetsklasse, proteinebeneNomenklatur, translation, zygositaet,cDNANomenklatur));

        unterformularSV.setHauptTudokEintragExportID(3);
        unterformularSV.setRevision(1);
        unterformularSV.setBearbeitungStatus(2);
        return unterformularSV;
    }

    private static String extractCDnaNomenklatur(String input) {
        if (null == input) {
            return null;
        }
        var pattern = Pattern.compile("\\s(?<cdna>.+)$");
        var matcher = pattern.matcher(input);
        if (matcher.find()) {
            return matcher.group("cdna");
        }
        return null;
    }
}
