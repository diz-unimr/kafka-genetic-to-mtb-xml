package de.unimarburg.diz.kafkagenetictomtbxml.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.unimarburg.diz.kafkagenetictomtbxml.model.MtbPatientInfo;
import de.unimarburg.diz.kafkagenetictomtbxml.model.mhGuide.VariantLongList;
import de.unimarburg.diz.kafkagenetictomtbxml.model.onkostarXml.DokumentierendeFachabteilung;
import de.unimarburg.diz.kafkagenetictomtbxml.model.onkostarXml.Eintrag;
import de.unimarburg.diz.kafkagenetictomtbxml.model.onkostarXml.UnterformularRNAFusion;
import de.unimarburg.diz.kafkagenetictomtbxml.util.CurrentDateFormatter;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UnterformularRANFusionMapper {

    public String extractStringFromJson(String jsonAnotation, String keyToExtract) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        var jsonNode = objectMapper.readTree(jsonAnotation);
        return jsonNode.get(keyToExtract).asText();
    }

    public String parseFusionPart5(String modifiedObj){
        String regex = "^(.*?)/";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(modifiedObj);
        String startPart = "";
        if (matcher.find()) {
            startPart = matcher.group(1);

        }
        return startPart;
    }

    public String parseFusionPart3(String modifiedObj){

        String regex = "/(.*)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(modifiedObj);
        String endPart = "";
        if (matcher.find()) {
            endPart = matcher.group(1);

        }
        return endPart;
    }


    public UnterformularRNAFusion createXmlUnterformularRANFusion (MtbPatientInfo mtbPatientInfo, VariantLongList variantLongList, DokumentierendeFachabteilung dokumentierendeFachabteilung, int startExportIDUNterformular) throws JsonProcessingException {
        UnterformularRNAFusion unterformularRNAFusion = new UnterformularRNAFusion();
        // To find the export ID a function need to implement, that track the number of unterformular and add the number TODO
        unterformularRNAFusion.setExportID(startExportIDUNterformular);
        unterformularRNAFusion.setTumorId(mtbPatientInfo.getTumorId());
        unterformularRNAFusion.setErkrankungExportID(2);
        unterformularRNAFusion.setDokumentierendeFachabteilung(dokumentierendeFachabteilung);
        unterformularRNAFusion.setStartDatum(CurrentDateFormatter.formatCurrentDate());
        unterformularRNAFusion.setFormularName("OS.Molekulargenetische Untersuchung");
        unterformularRNAFusion.setFormularVersion(1);
        unterformularRNAFusion.setProzedurtyp("Beobachtung");
        Eintrag dokumentationUnterformular = new Eintrag();
        dokumentationUnterformular.setFeldname("Dokumentation");
        dokumentationUnterformular.setWert("ERW");
        dokumentationUnterformular.setFilterkategorie("{}");
        dokumentationUnterformular.setVersion("OS.MolDokumentation.v1");
        dokumentationUnterformular.setKurztext("Erweitert");

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
        Eintrag datumFusion = new Eintrag();
        datumFusion.setFeldname("Datum");
        datumFusion.setWert("");

        // Unterformular: Eintrag: Feldname = Ergebnis
        Eintrag ergebnisEintragRNAFusion = new Eintrag();
        ergebnisEintragRNAFusion.setFeldname("Ergebnis");
        ergebnisEintragRNAFusion.setWert("F");
        ergebnisEintragRNAFusion.setFilterkategorie("{&quot;OS.MolGenErgebnis.v1&quot;:&quot;Standard&quot;}");
        ergebnisEintragRNAFusion.setVersion("OS.MolGenErgebnis.v1");
        ergebnisEintragRNAFusion.setKurztext("Fusion (Translokation Inversion Insertion)");

        // Unterformular: Eintrag: Feldname = FusionArt
        Eintrag fusionArt = new Eintrag();
        fusionArt.setFeldname("FusionArt");
        fusionArt.setWert("RNA");
        // RNA 3
        // Unterformular: Eintrag: Feldname = FusionRNA3ENSEMBLID
        // will be filled automatically by DNPM
        Eintrag fusionRNA3ENSEMBLID = new Eintrag();
        fusionRNA3ENSEMBLID.setFeldname("FusionRNA3ENSEMBLID");

        // Unterformular: Eintrag: Feldname = FusionRNA3HGNCID
        // will be filled automatically by DNPM
        Eintrag  fusionRNA3HGNCID = new Eintrag();
        fusionRNA3HGNCID.setFeldname("FusionRNA3HGNCID");

        // Unterformular: Eintrag: Feldname = FusionRNA3HGNCName
        // will be filled automatically by DNPM
        Eintrag fusionRNA3HGNCName = new Eintrag();
        fusionRNA3HGNCName.setFeldname("FusionRNA3HGNCName");

        // Unterformular: Eintrag: Feldname = FusionRNA3HGNCSymbol

        Eintrag fusionRNA3HGNCSymbol = new Eintrag();
        fusionRNA3HGNCSymbol.setFeldname("FusionRNA3HGNCSymbol");
        fusionRNA3HGNCSymbol.setWert(parseFusionPart3(variantLongList.getDisplayModifiedObject()));

        // Unterformular: Eintrag: Feldname = FusionRNA3ExonID
        Eintrag fusionRNA3ExonID = new Eintrag();
        fusionRNA3ExonID.setFeldname("FusionRNA3ExonID");
        fusionRNA3ExonID.setWert(extractStringFromJson(variantLongList.getAnnotationJson(), "MH_EXON_NUMBER_2"));


        // Unterformular: Eintrag: Feldname = FusionRNA3Strand
        Eintrag fusionRNA3Strand = new Eintrag();
        fusionRNA3Strand.setFeldname("FusionRNA3Strand");
        fusionRNA3Strand.setWert(extractStringFromJson(variantLongList.getAnnotationJson(), "SEGMENT_2_ALIGNMENT_STRAND"));

        // Unterformular: Eintrag: Feldname = FusionRNA3TransPosition
        Eintrag  fusionRNA3TransPosition  = new Eintrag();
        fusionRNA3TransPosition.setFeldname("FusionRNA3TransPosition");
        fusionRNA3TransPosition.setWert(parseFusionPart3(variantLongList.getChromosomeModification()));

        // Unterformular: Eintrag: Feldname = FusionRNA3TranscriptID
        Eintrag fusionRNA3TranscriptID = new Eintrag();
        fusionRNA3TranscriptID.setFeldname("FusionRNA3TranscriptID");
        fusionRNA3Strand.setWert(extractStringFromJson(variantLongList.getAnnotationJson(), "SEGMENT_2_ALIGNMENT_STRAND"));

        // RNA 5
        // Unterformular: Eintrag: Feldname = FusionRNA5ENSEMBLID
        // Filled automatically from DNPM: Laut Mapping file
        Eintrag fusionRNA5ENSEMBLID = new Eintrag();
        fusionRNA5ENSEMBLID.setFeldname("FusionRNA5ENSEMBLID");

        // Unterformular: Eintrag: Feldname = FusionRNA5HGNCID
        // Filled automatically from DNPM: Laut Mapping file
        Eintrag fusionRNA5HGNCID = new Eintrag();
        fusionRNA5HGNCID.setFeldname("FusionRNA5HGNCID");

        // Unterformular: Eintrag: Feldname = FusionRNA5HGNCName
        // Filled automatically from DNPM: Laut Mapping file
        Eintrag fusionRNA5HGNCName = new Eintrag();
        fusionRNA5HGNCName.setFeldname("FusionRNA5HGNCName");

        // Unterformular: Eintrag: Feldname = FusionRNA5HGNCSymbol

        Eintrag fusionRNA5HGNCSymbol = new Eintrag();
        fusionRNA5HGNCSymbol.setFeldname("FusionRNA5HGNCSymbol");
        fusionRNA5HGNCSymbol.setWert(parseFusionPart5(variantLongList.getDisplayModifiedObject()));

        // Unterformular: Eintrag: Feldname = FusionRNA5ExonID
        Eintrag fusionRNA5ExonID = new Eintrag();
        fusionRNA5ExonID.setFeldname("FusionRNA5ExonID");
        fusionRNA5ExonID.setWert(extractStringFromJson(variantLongList.getAnnotationJson(), "MH_EXON_NUMBER_1"));

        // Unterformular: Eintrag: Feldname = FusionRNA5Strand
        Eintrag fusionRNA5Strand = new Eintrag();
        fusionRNA5Strand.setFeldname("FusionRNA5Strand");
        fusionRNA5Strand.setWert(extractStringFromJson(variantLongList.getAnnotationJson(), "SEGMENT_1_ALIGNMENT_STRAND"));

        // Unterformular: Eintrag: Feldname = FusionRNA5TransPosition
        // Parse the first part
        Eintrag fusionRNA5TransPosition = new Eintrag();
        fusionRNA5TransPosition.setFeldname("FusionRNA5TransPosition");
        fusionRNA5TransPosition.setWert(parseFusionPart5(variantLongList.getChromosomeModification()));

        // Unterformular: Eintrag: Feldname = FusionRNA5TranscriptID
        Eintrag fusionRNA5TranscriptID = new Eintrag();
        fusionRNA5TranscriptID.setFeldname("FusionRNA5TranscriptID");
        fusionRNA5TranscriptID.setWert(extractStringFromJson(variantLongList.getAnnotationJson(),"SEGMENT_1_DISPLAY_TRANSCRIPT_ID"));

        // Unterformular: Eintrag: Feldname = FusionRNACosmicID
        // NA
        Eintrag fusionRNACosmicID = new Eintrag();
        fusionRNACosmicID.setFeldname("FusionRNACosmicID");

        // Unterformular: Eintrag: Feldname = FusionRNAEffect
        Eintrag fusionRNAEffect = new Eintrag();
        fusionRNAEffect.setFeldname("FusionRNAEffect");
        fusionRNAEffect.setFeldname(variantLongList.getVariantEffect());

        // Unterformular: Eintrag: Feldname = FusionRNAReportedNumRead
        Eintrag fusionRNAReportedNumRead = new Eintrag();
        fusionRNAReportedNumRead.setFeldname("FusionRNAReportedNumRead");
        fusionRNAReportedNumRead.setWert(variantLongList.getVariantEffect());
        // Unterformular: Eintrag: Feldname = FusioniertesGen

        // Unterformular: Eintrag: Feldname = Untersucht
        // NA
        Eintrag untersucht = new Eintrag();
        untersucht.setFeldname("Untersucht");
        // Add all the eintrags
        unterformularRNAFusion.setEintraege(Arrays.asList(dokumentationUnterformular,aktivierend,
                allelfrequenz, allelzahl, analysemethode, bemerkung, datumFusion,
                fusionArt, fusionRNA3ENSEMBLID, fusionRNA3ExonID, fusionRNA3HGNCID, fusionRNA3HGNCName, fusionRNA3HGNCSymbol,
                fusionRNA3Strand, fusionRNA3TransPosition, fusionRNA3TranscriptID, fusionRNA5ENSEMBLID, fusionRNA5ExonID,
                fusionRNA5HGNCID, fusionRNA5HGNCName, fusionRNA5HGNCSymbol, fusionRNA5Strand,
                fusionRNA5TransPosition, fusionRNA5TranscriptID, fusionRNACosmicID, fusionRNAEffect, fusionRNAReportedNumRead, untersucht, ergebnisEintragRNAFusion));

        unterformularRNAFusion.setHauptTudokEintragExportID(3);
        unterformularRNAFusion.setRevision(1);
        unterformularRNAFusion.setBearbeitungStatus(0);

        return unterformularRNAFusion;
    }


}
