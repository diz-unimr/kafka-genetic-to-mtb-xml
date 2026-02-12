package de.unimarburg.diz.kafkagenetictomtbxml.mapper;


import de.unimarburg.diz.kafkagenetictomtbxml.configuration.MetadataConfigurationProperties;
import de.unimarburg.diz.kafkagenetictomtbxml.model.MtbPatientInfo;
import de.unimarburg.diz.kafkagenetictomtbxml.model.mhGuide.Variant;
import de.unimarburg.diz.kafkagenetictomtbxml.model.onkostarXml.DokumentierendeFachabteilung;
import de.unimarburg.diz.kafkagenetictomtbxml.model.onkostarXml.Eintrag;
import de.unimarburg.diz.kafkagenetictomtbxml.model.onkostarXml.UnterformularKomplexBiomarker;
import de.unimarburg.diz.kafkagenetictomtbxml.util.CurrentDateFormatter;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;

@Component
public class UnterformularKomplexBiomarkerMapperTMB {

    private final MetadataConfigurationProperties metadataConfigurationProperties;

    public UnterformularKomplexBiomarkerMapperTMB(final MetadataConfigurationProperties metadataConfigurationProperties){
        this.metadataConfigurationProperties = metadataConfigurationProperties;
    }

    public UnterformularKomplexBiomarker createXmlUnterformularKBiomarkerTMB(MtbPatientInfo mtbPatientInfo, Variant variant, DokumentierendeFachabteilung dokumentierendeFachabteilung, int startExportIDUNterformular) {
        UnterformularKomplexBiomarker unterformularKBiomarker = new UnterformularKomplexBiomarker();

        unterformularKBiomarker.setExportID(startExportIDUNterformular);
        unterformularKBiomarker.setTumorId(mtbPatientInfo.getTumorId());
        unterformularKBiomarker.setErkrankungExportID(2);
        unterformularKBiomarker.setDokumentierendeFachabteilung(dokumentierendeFachabteilung);
        unterformularKBiomarker.setFormularName("OS.MolGen Komplexe Biomarker");
        unterformularKBiomarker.setFormularVersion(1);
        unterformularKBiomarker.setProzedurtyp("");

        // Eintrag: AnalyseMethoden
        Eintrag analyseMethoden = new Eintrag();
        analyseMethoden.setFeldname("AnalyseMethoden");
        analyseMethoden.setWert(metadataConfigurationProperties.getNgsReports().getAnalyseMethoden());
        analyseMethoden.setFilterkategorie("{\"OS.MolDiagMethode.v1\":\"MSI\"}");
        analyseMethoden.setVersion("OS.MolDiagMethode.v1");
        // Eintrag: Assay

        Eintrag assay = new Eintrag();
        assay.setFeldname("Assay");
        assay.setWert("");

        // Eintrag: KomplexerBiomarker
        Eintrag komplexerBiomarker = new Eintrag();
        komplexerBiomarker.setFeldname("KomplexerBiomarker");
        komplexerBiomarker.setWert("TMB");
        komplexerBiomarker.setVersion("OS.MolDiagKomplexeBiomarker.v1");
        komplexerBiomarker.setKurztext("TMB");

        Eintrag komplexerBiomarkerTMB = new Eintrag();
        komplexerBiomarkerTMB.setFeldname("TumorMutationalBurden");
        komplexerBiomarkerTMB.setWert(variant.getTmbVariantCountPerMegabase());

        // Add all the entrage in the array
        unterformularKBiomarker.setEintraege(Arrays.asList(analyseMethoden, assay, komplexerBiomarker, komplexerBiomarkerTMB));
        unterformularKBiomarker.setHauptTudokEintragExportID(3);
        unterformularKBiomarker.setRevision(1);
        unterformularKBiomarker.setBearbeitungStatus(2);
        return unterformularKBiomarker;
    }

}
