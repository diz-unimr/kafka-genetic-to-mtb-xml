package de.unimarburg.diz.kafkagenetictomtbxml.mapper;


import de.unimarburg.diz.kafkagenetictomtbxml.model.MtbPatientInfo;
import de.unimarburg.diz.kafkagenetictomtbxml.model.mhGuide.VariantLongList;
import de.unimarburg.diz.kafkagenetictomtbxml.model.onkostarXml.DokumentierendeFachabteilung;
import de.unimarburg.diz.kafkagenetictomtbxml.model.onkostarXml.Eintrag;
import de.unimarburg.diz.kafkagenetictomtbxml.model.onkostarXml.UnterformularKomplexBiomarker;
import de.unimarburg.diz.kafkagenetictomtbxml.util.CurrentDateFormatter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class UnterformularKomplexBiomarkerMapperTMB {
    private final String analyseMethodenVal;

    public UnterformularKomplexBiomarkerMapperTMB(@Value("${metadata.ngsReports.analyseMethoden}") String analyseMethodenVal){
        this.analyseMethodenVal = analyseMethodenVal;
    }

    public UnterformularKomplexBiomarker createXmlUnterformularKBiomarkerTMB(MtbPatientInfo mtbPatientInfo, VariantLongList variantLongList, DokumentierendeFachabteilung dokumentierendeFachabteilung, int startExportIDUNterformular) {
        UnterformularKomplexBiomarker unterformularKBiomarker = new UnterformularKomplexBiomarker();

        unterformularKBiomarker.setExportID(startExportIDUNterformular);
        unterformularKBiomarker.setTumorId(mtbPatientInfo.getTumorId());
        unterformularKBiomarker.setErkrankungExportID(2);
        unterformularKBiomarker.setDokumentierendeFachabteilung(dokumentierendeFachabteilung);
        unterformularKBiomarker.setStartDatum(CurrentDateFormatter.formatCurrentDate());
        unterformularKBiomarker.setFormularName("OS.MolGen Komplexe Biomarker");
        unterformularKBiomarker.setFormularVersion(1);
        unterformularKBiomarker.setProzedurtyp("");

        // Eintrag: AnalyseMethoden
        Eintrag analyseMethoden = new Eintrag();
        analyseMethoden.setFeldname("AnalyseMethoden");
        analyseMethoden.setWert(analyseMethodenVal);
        analyseMethoden.setFilterkategorie("{}");
        analyseMethoden.setVersion("OS.MolDiagMethode.v1");

        // Eintrag: Assay

        Eintrag assay = new Eintrag();
        assay.setFeldname("Assay");
        assay.setWert("");

        // Eintrag: KomplexerBiomarker
        Eintrag komplexerBiomarker = new Eintrag();
        komplexerBiomarker.setFeldname("KomplexerBiomarker");
        komplexerBiomarker.setWert("TMB");
        komplexerBiomarker.setFilterkategorie("{}");
        komplexerBiomarker.setVersion("OS.MolDiagKomplexeBiomarker.v1");

        Eintrag komplexerBiomarkerTMB = new Eintrag();
        komplexerBiomarkerTMB.setFeldname("TumorMutationalBurden");
        komplexerBiomarkerTMB.setWert(variantLongList.getTmbVariantCountPerMegabase());

        // Add all the entrage in the array
        unterformularKBiomarker.setEintraege(Arrays.asList(analyseMethoden, assay, komplexerBiomarker, komplexerBiomarkerTMB));
        unterformularKBiomarker.setHauptTudokEintragExportID(3);
        unterformularKBiomarker.setRevision(1);
        unterformularKBiomarker.setBearbeitungStatus(2);
        return unterformularKBiomarker;
    }

}
