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
@Component
public class UnterformularKomplexBiomarkerMapperMSI {
    private final MetadataConfigurationProperties metadataConfigurationProperties;

    public UnterformularKomplexBiomarkerMapperMSI(final MetadataConfigurationProperties metadataConfigurationProperties){
        this.metadataConfigurationProperties = metadataConfigurationProperties;
    }

    public UnterformularKomplexBiomarker createXmlUnterformularKBiomarkerMSI(MtbPatientInfo mtbPatientInfo, Variant variant, DokumentierendeFachabteilung dokumentierendeFachabteilung, int startExportIDUNterformular) {


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
        analyseMethoden.setWert(metadataConfigurationProperties.getNgsReports().getAnalyseMethoden());
        analyseMethoden.setFilterkategorie("{\"OS.MolDiagMethode.v1\":\"MSI\"}");
        analyseMethoden.setVersion("OS.MolDiagMethode.v1");

        // Eintrag: Assay

        Eintrag assay = new Eintrag();
        assay.setFeldname("Assay");
        // TODO: Siehe Xwiki
        assay.setWert("");

        // Eintrag: ErgebnisMSI
        Eintrag ergebnisEintragSV = new Eintrag();
        ergebnisEintragSV.setFeldname("ErgebnisMSI");
        // Value need to be mapped in categories
        // If the value of GENOMIC_EXTRA_DATA < 20 %  -> MSS (stable) and if >= 20 % -> H (unstable)
        var genomicExtraData = Double.parseDouble(variant.getGenomicExtraData());
        if(genomicExtraData < 20) {
            ergebnisEintragSV.setWert("MSS");
            ergebnisEintragSV.setKurztext("Stabil (MSS)");
        } else if (genomicExtraData >= 20) {
            ergebnisEintragSV.setWert("H");
            ergebnisEintragSV.setKurztext("Instabil (MSI high)");
        }
        ergebnisEintragSV.setFilterkategorie("{}");
        ergebnisEintragSV.setVersion("OS.MolDiagErgebnisMSI.v1");

        // Eintrag: KomplexerBiomarker
        Eintrag komplexerBiomarker = new Eintrag();
        komplexerBiomarker.setFeldname("KomplexerBiomarker");
        komplexerBiomarker.setWert("MSI");
        komplexerBiomarker.setVersion("OS.MolDiagKomplexeBiomarker.v1");
        komplexerBiomarker.setKurztext("MSI/MMR");

        // Eintrag: SeqProzentwert
        Eintrag seqProzentwert = new Eintrag();
        seqProzentwert.setFeldname("SeqProzentwert");
        seqProzentwert.setWert(variant.getGenomicExtraData());
        // Add all the entrage in the array
        unterformularKBiomarker.setEintraege(Arrays.asList(analyseMethoden, assay, ergebnisEintragSV, komplexerBiomarker, seqProzentwert));
        unterformularKBiomarker.setHauptTudokEintragExportID(3);
        unterformularKBiomarker.setRevision(1);
        unterformularKBiomarker.setBearbeitungStatus(2);
        return unterformularKBiomarker;
    }

}
