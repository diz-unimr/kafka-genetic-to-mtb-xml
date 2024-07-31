package de.unimarburg.diz.kafkagenetictomtbxml.mapper;

import de.unimarburg.diz.kafkagenetictomtbxml.model.MtbPidInfo;
import de.unimarburg.diz.kafkagenetictomtbxml.model.mhGuide.MHGuide;
import de.unimarburg.diz.kafkagenetictomtbxml.model.onkostarXml.DokumentierendeFachabteilung;
import de.unimarburg.diz.kafkagenetictomtbxml.model.onkostarXml.Eintrag;
import de.unimarburg.diz.kafkagenetictomtbxml.model.onkostarXml.UnterformularRNAFusion;
import org.springframework.stereotype.Component;

import java.util.Arrays;
@Component
public class UnterformularRANFusionMapper {


    public UnterformularRNAFusion createXmlUnterformularRANFusion (MHGuide mhGuideInfo, MtbPidInfo mtbPidInfo, DokumentierendeFachabteilung dokumentierendeFachabteilung){
        UnterformularRNAFusion unterformularRNAFusion = new UnterformularRNAFusion();
        unterformularRNAFusion.setExportID(1);
        unterformularRNAFusion.setTumorId("1");
        unterformularRNAFusion.setDokumentierendeFachabteilung(dokumentierendeFachabteilung);
        unterformularRNAFusion.setStartDatum("2023-08-10");
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

        // Unterformular: Eintrag: Feldname = FusionRNA3ENSEMBLID
        Eintrag fusionRNA3ENSEMBLID = new Eintrag();
        fusionRNA3ENSEMBLID.setFeldname("FusionRNA3ENSEMBLID");

        // Unterformular: Eintrag: Feldname = FusionRNA3ExonID
        Eintrag fusionRNA3ExonID = new Eintrag();
        fusionRNA3ExonID.setFeldname("FusionRNA3ExonID");

        // Unterformular: Eintrag: Feldname = FusionRNA3HGNCID
        Eintrag  fusionRNA3HGNCID = new Eintrag();
        fusionRNA3HGNCID.setFeldname("FusionRNA3HGNCID");

        // Unterformular: Eintrag: Feldname = FusionRNA3HGNCName
        Eintrag fusionRNA3HGNCName = new Eintrag();
        fusionRNA3HGNCName.setFeldname("FusionRNA3HGNCName");

        // Unterformular: Eintrag: Feldname = FusionRNA3HGNCSymbol
        Eintrag fusionRNA3HGNCSymbol = new Eintrag();
        fusionRNA3HGNCSymbol.setFeldname("FusionRNA3HGNCSymbol");

        // Unterformular: Eintrag: Feldname = FusionRNA3Strand
        Eintrag fusionRNA3Strand = new Eintrag();
        fusionRNA3Strand.setFeldname("FusionRNA3Strand");

        // Unterformular: Eintrag: Feldname = FusionRNA3TransPosition
        Eintrag  fusionRNA3TransPosition  = new Eintrag();
        fusionRNA3TransPosition.setFeldname("FusionRNA3TransPosition");

        // Unterformular: Eintrag: Feldname = FusionRNA3TranscriptID
        Eintrag fusionRNA3TranscriptID = new Eintrag();
        fusionRNA3TranscriptID.setFeldname("FusionRNA3TranscriptID");

        // Unterformular: Eintrag: Feldname = FusionRNA5ENSEMBLID
        Eintrag fusionRNA5ENSEMBLID = new Eintrag();
        fusionRNA5ENSEMBLID.setFeldname("FusionRNA5ENSEMBLID");


        // Unterformular: Eintrag: Feldname = FusionRNA5ExonID
        Eintrag fusionRNA5ExonID = new Eintrag();
        fusionRNA5ExonID.setFeldname("FusionRNA5ExonID");

        // Unterformular: Eintrag: Feldname = FusionRNA5HGNCID
        Eintrag fusionRNA5HGNCID = new Eintrag();
        fusionRNA5HGNCID.setFeldname("FusionRNA5HGNCID");

        // Unterformular: Eintrag: Feldname = FusionRNA5HGNCName
        Eintrag fusionRNA5HGNCName = new Eintrag();
        fusionRNA5HGNCName.setFeldname("FusionRNA3HGNCName");

        // Unterformular: Eintrag: Feldname = FusionRNA5HGNCSymbol
        Eintrag fusionRNA5HGNCSymbol = new Eintrag();
        fusionRNA5HGNCSymbol.setFeldname("FusionRNA5HGNCSymbol");


        // Unterformular: Eintrag: Feldname = FusionRNA5Strand
        Eintrag fusionRNA5Strand = new Eintrag();
        fusionRNA5Strand.setFeldname("FusionRNA5Strand");

        // Unterformular: Eintrag: Feldname = FusionRNA5TransPosition
        Eintrag fusionRNA5TransPosition = new Eintrag();
        fusionRNA5TransPosition.setFeldname("FusionRNA5TransPosition");

        // Unterformular: Eintrag: Feldname = FusionRNA5TranscriptID
        Eintrag fusionRNA5TranscriptID = new Eintrag();
        fusionRNA5TranscriptID.setFeldname("FusionRNA5TranscriptID");

        // Unterformular: Eintrag: Feldname = FusionRNACosmicID
        Eintrag fusionRNACosmicID = new Eintrag();
        fusionRNACosmicID.setFeldname("FusionRNACosmicID");

        // Unterformular: Eintrag: Feldname = FusionRNAEffect
        Eintrag fusionRNAEffect = new Eintrag();
        fusionRNAEffect.setFeldname("FusionRNAEffect");

        // Unterformular: Eintrag: Feldname = FusionRNAReportedNumRead
        Eintrag fusionRNAReportedNumRead = new Eintrag();
        fusionRNAReportedNumRead.setFeldname("FusionRNAReportedNumRead");        // Unterformular: Eintrag: Feldname = FusioniertesGen

        // Unterformular: Eintrag: Feldname = Untersucht
        Eintrag untersucht = new Eintrag();
        untersucht.setFeldname("Untersucht");
        // Add all the eintrags
        unterformularRNAFusion.setEintraege(Arrays.asList(dokumentationUnterformular,aktivierend,
                allelfrequenz, allelzahl, analysemethode, bemerkung, datumFusion,
                fusionArt, fusionRNA3ENSEMBLID, fusionRNA3ExonID, fusionRNA3HGNCID, fusionRNA3HGNCName, fusionRNA3HGNCSymbol,
                fusionRNA3Strand, fusionRNA3TransPosition, fusionRNA3TranscriptID, fusionRNA5ENSEMBLID, fusionRNA5ExonID,
                fusionRNA5HGNCID, fusionRNA5HGNCName, fusionRNA5HGNCSymbol, fusionRNA5Strand,
                fusionRNA5TransPosition, fusionRNA5TranscriptID, fusionRNACosmicID, fusionRNAEffect, fusionRNAReportedNumRead, untersucht, ergebnisEintragRNAFusion));
        return unterformularRNAFusion;
    }


}
