package de.unimarburg.diz.kafkagenetictomtbxml.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.unimarburg.diz.kafkagenetictomtbxml.model.MtbPatientInfo;
import de.unimarburg.diz.kafkagenetictomtbxml.model.mhGuide.MHGuide;
import de.unimarburg.diz.kafkagenetictomtbxml.model.mhGuide.VariantLongList;
import de.unimarburg.diz.kafkagenetictomtbxml.model.onkostarXml.*;
import de.unimarburg.diz.kafkagenetictomtbxml.util.CurrentDateFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class TudokMapper {
    private final static Logger log = LoggerFactory.getLogger(TudokMapper.class);

    private final UnterformularSVMapper unterformularSVMapper;
    private final UnterformularCNVMapper unterformularCNVMapper;
    private final UnterformularRNAFusionMapper unterformularRANFusionMapper;
    private final String kitManufacturer;
    private final String sequencer;
    private final String referenceGenome;
    private final String sequencingType;
    private final String panel;


    DokumentierendeFachabteilung dokumentierendeFachabteilung = new DokumentierendeFachabteilung();

    public TudokMapper(UnterformularSVMapper unterformularSVMapper, UnterformularCNVMapper unterformularCNVMapper, UnterformularRNAFusionMapper unterformularRANFusionMapper,
                       @Value("${metadata.ngsReports.kitManufacturer}") String kitManufacturer,
                       @Value("${metadata.ngsReports.sequencer}") String sequencer,
                       @Value("${metadata.ngsReports.referenceGenome}") String referenceGenome,
                       @Value("${metadata.ngsReports.sequencingType}") String sequencingType,
                       @Value("${metadata.ngsReports.panel}") String panel
                       ) {
        this.unterformularSVMapper = unterformularSVMapper;
        this.unterformularCNVMapper = unterformularCNVMapper;
        this.unterformularRANFusionMapper = unterformularRANFusionMapper;
        this.kitManufacturer = kitManufacturer;
        this.sequencer = sequencer;
        this.referenceGenome = referenceGenome;
        this.sequencingType = sequencingType;
        this.panel = panel;
    }

    public TudokEintrag createTudokEintrag(MHGuide mhGuideInfo, MtbPatientInfo mtbPatientInfo) throws JsonProcessingException {
        TudokEintrag tudokEintrag = new TudokEintrag();
        // TudokEintrag : ExportID
        tudokEintrag.setExportID(3);
        //
        tudokEintrag.setErkrankungExportID(2);
        // TudokEintrag :
        tudokEintrag.setTumorId(mtbPatientInfo.getTumorId());
        // TudokEintrag //
        dokumentierendeFachabteilung.setFachabteilungKennung("Tumordokumentation");
        dokumentierendeFachabteilung.setEinrichtungKennung("CCC");
        // Set the Dokumentierendedfachabteilung
        tudokEintrag.setDokumentierendeFachabteilung(dokumentierendeFachabteilung);

        // TudokEintrag : StartDatum
        tudokEintrag.setStartDatum("");
        // TudokEintrag :
        tudokEintrag.setFormularName("OS.Molekulargenetik");
        // TudokEintrag :
        tudokEintrag.setFormularVersion(1);
        // TudokEintrag :
        tudokEintrag.setProzedurtyp("Beobachtung");
        // TudokEintrag :

        // TudokEintrag: Eintrag: Feldname = AnalyseID
        Eintrag analyseID = new Eintrag();
        analyseID.setFeldname("AnalyseID");

        // TudokEintrag: Eintrag: Feldname = AnalyseMethode
        Eintrag analyseMethode = new Eintrag();
        analyseMethode.setFeldname("AnalyseMethode");

        // TudokEintrag: Eintrag: Feldname = AnalyseMethoden
        Eintrag analyseMethoden = new Eintrag();
        analyseMethoden.setFeldname("AnalyseMethoden");
        // application.yml
        analyseMethoden.setWert("S,H,");
        analyseMethoden.setFilterkategorie("{}");
        analyseMethoden.setVersion("OS.MolDiagMethode.v1");

        // TudokEintrag: Eintrag: Feldname = ArtDerSequenzierung
        Eintrag artDerSequenzierung = new Eintrag();
        artDerSequenzierung.setFeldname("ArtDerSequenzierung");
        artDerSequenzierung.setWert("PanelKit");
        artDerSequenzierung.setFilterkategorie("{}");
        artDerSequenzierung.setVersion("OS.MolDiagSequenzierung.v1");
        artDerSequenzierung.setKurztext("Panel");

        // TudokEintrag: Eintrag: Feldname = ArtinsituHybridisierung
        Eintrag artinsituHybridisierung = new Eintrag();
        artinsituHybridisierung.setFeldname("ArtinsituHybridisierung");
        artinsituHybridisierung.setWert("FISH");
        artinsituHybridisierung.setFilterkategorie("");
        artinsituHybridisierung.setVersion("OS.MolArtderHybridisierung.v1");
        artinsituHybridisierung.setKurztext("FISH");

        // TudokEintrag: Eintrag: Feldname = Befund
        Eintrag befund = new Eintrag();
        befund.setFeldname("Befund");

        // TudokEintrag: Eintrag: Feldname = Bemerkung
        Eintrag bemerkung = new Eintrag();
        bemerkung.setFeldname("Bemerkung");

        // TudokEintrag: Eintrag: Feldname = Blocknummer
        Eintrag blocknummer = new Eintrag();
        blocknummer.setFeldname("Blocknummer");

        // TudokEintrag: Eintrag: Feldname = Datum
        Eintrag datum = new Eintrag();
        datum.setFeldname("Datum");
        datum.setWert(CurrentDateFormatter.formatCurrentDate());
        datum.setGenauigkeit("exact");

        // TudokEintrag: Eintrag: Feldname = Dokumentation
        Eintrag doc = new Eintrag();
        doc.setFeldname("Dokumentation");
        doc.setWert("BAS");
        doc.setFilterkategorie("{}");
        doc.setVersion("OS.MolDokumentation.v1");
        doc.setKurztext("Basis");

        // TudokEintrag: Eintrag: Feldname = DurchfuehrendeOE
        Eintrag durchfuehrendeOE = new Eintrag();
        durchfuehrendeOE.setFeldname("DurchfuehrendeOE");
        durchfuehrendeOE.setWert("CCC");
        durchfuehrendeOE.setKurztext("CCC Marburg");


        // TudokEintrag: Eintrag: Feldname = Einsendenummer
        Eintrag einsendenummer = new Eintrag();
        einsendenummer.setFeldname("Einsendenummer");
        einsendenummer.setWert(mtbPatientInfo.getEinsendennummer());

        // TudokEintrag: Eintrag: Feldname = Entnahmedatum
        Eintrag entnahmedatum = new Eintrag();
        entnahmedatum.setFeldname("Entnahmedatum");
        //entnahmedatum.setWert("");
        //entnahmedatum.setGenauigkeit("");

        // TudokEintrag: Eintrag: Feldname = Entnahmemethode
        Eintrag entnahmemethode = new Eintrag();
        entnahmemethode.setFeldname("EntnahmeMethode");
        //entnahmemethode.setWert("");
        entnahmemethode.setFilterkategorie("");
        //entnahmemethode.setVersion("");
        //entnahmemethode.setKurztext("");

        // TudokEintrag: Eintrag : ErgebnisMSI
        Eintrag ergebnisMSI = new Eintrag();
        ergebnisMSI.setFeldname("ErgebnisMSI");
        ergebnisMSI.setWert("");
        ergebnisMSI.setFilterkategorie("{}");

        // TudokEintrag: Eintrag : GenetischeVeraenderung
        Eintrag genetischeVeraenderung = new Eintrag();
        genetischeVeraenderung.setFeldname("GenetischeVeraenderung");

        // TudokEintrag: Eintrag : Genexpressionstests
        Eintrag genexpressionstests = new Eintrag();
        genexpressionstests.setFeldname("Genexpressionstests");

        // TudokEintrag: Eintrag : HRD
        Eintrag hRD = new Eintrag();
        hRD.setFeldname("HRD");
        hRD.setFilterkategorie("{}");

        // TudokEintrag: Eintrag : ICDO3Lokalisation
        Eintrag iCDO3Lokalisation = new Eintrag();
        iCDO3Lokalisation.setFeldname("ICDO3Lokalisation");
        iCDO3Lokalisation.setFilterkategorie("");

        // TudokEintrag: Eintrag: Feldname = InternExtern
        Eintrag internExtern = new Eintrag();
        internExtern.setFeldname("InternExtern");
        internExtern.setWert("E");
        internExtern.setFilterkategorie("{}");
        internExtern.setVersion("OS.OrtDurchfuehrung.v1");
        internExtern.setKurztext("Andere Einrichtung");

        // TudokEintrag: Eintrag : TumorMutationalBurden
        Eintrag tumorMutationalBurden = new Eintrag();
        tumorMutationalBurden.setFeldname("TumorMutationalBurden");


        // TudokEintrag: Eintrag: Feldname = MolekulargenetischeUntersuchung
        Eintrag eintragMolekulargenetischeUntersuchung = new Eintrag();
        eintragMolekulargenetischeUntersuchung.setFeldname("MolekulargenetischeUntersuchung");
        List<Unterformular> unterformularList = new ArrayList<>();

        // Eintrag : Feldname = MolekulargenetischeUntersuchung
        // MolekulargenetischeUntersuchung: Formular
        // UnterformaularTyp: SimpleVariant (SV)

        List<VariantLongList> variantLongLists = mhGuideInfo.getVariantLongList();
        // length of variantlong list
        var  lengthVariantLongList = variantLongLists.size();
        log.info("Length of current variantList: {}", lengthVariantLongList);
        var startExportIDUNterformular = 4;
        for (VariantLongList variantLongList : variantLongLists) {
            var variantType = variantLongList.getDisplayVariantType();
            if ( variantType != null) {
                System.out.println("Current Variation" + variantLongList.getDisplayVariantType());
                switch (variantType) {
                    case "SNV":
                        log.info("SNV found");
                        var  unterformularSV = unterformularSVMapper.createXmlUnterformularSV(mtbPatientInfo,variantLongList, dokumentierendeFachabteilung, startExportIDUNterformular);
                        unterformularList.add(unterformularSV);
                        log.info("New formular created and add to the list of unterformular");
                        startExportIDUNterformular++;
                        break;
                    case "CNV":
                        log.info("CNV found");
                        var  unterformularCNV = unterformularCNVMapper.createXmlUnterformularCNV(mtbPatientInfo,variantLongList, dokumentierendeFachabteilung, startExportIDUNterformular);
                        unterformularList.add(unterformularCNV);
                        log.info("New formular created and add to the list of unterformular");
                        startExportIDUNterformular++;
                        break;
                    case "fusion":
                        var  unterformularRNAFusion = unterformularRANFusionMapper.createXmlUnterformularRANFusion(mtbPatientInfo,variantLongList, dokumentierendeFachabteilung, startExportIDUNterformular);
                        unterformularList.add(unterformularRNAFusion);
                        startExportIDUNterformular++;
                        break;
                    case "TMB":
                        log.info("TMB found");
                        tumorMutationalBurden.setWert(variantLongList.getTmbVariantCountPerMegabase());
                        startExportIDUNterformular++;
                        break;
                    case "MSI":
                        log.info("MSI found");
                        var variantSymbol = variantLongList.getVariantSymbol();
                        if (variantSymbol.equals("MSS")){
                            ergebnisMSI.setWert(variantLongList.getGenomicExtraData());
                        }
                        startExportIDUNterformular++;
                        break;
                }
            }else {
                log.warn(String.format("CASE_UUID: {%s}: MH Guide variation list element with DETECTED_VAR_ID: {%s} has empty CHROMOSOMAL_VARIANT_TYPE", mhGuideInfo.getGeneralInfo().getCaseUuid(), variantLongList.getDetectedVarId()));
            }
        }
        log.info("Total number of unterformular: " + unterformularList.size());
        eintragMolekulargenetischeUntersuchung.setUnterformulars(unterformularList);
        // TudokEintrag: Eintrag : Panel
        Eintrag panelEintrag = new Eintrag();
        panelEintrag.setFeldname("Panel");
        // TODO: check it
        panelEintrag.setWert(panel);

        // TudokEintrag: Eintrag : ProbeID
        Eintrag probeID = new Eintrag();
        probeID.setFeldname("ProbeID");

        // TudokEintrag: Eintrag : Probenmaterial
        Eintrag probenmaterial = new Eintrag();
        probenmaterial.setFeldname("Probenmaterial");

        // TudokEintrag: Eintrag : Projekt
        Eintrag projekt = new Eintrag();
        projekt.setFeldname("Projekt");

        // TudokEintrag: Eintrag : ReferenzGenom
        Eintrag referenzGenom = new Eintrag();
        referenzGenom.setFeldname("ReferenzGenom");
        referenzGenom.setWert(referenceGenome);

        // TudokEintrag: Eintrag : SeqKitHersteller
        Eintrag seqKitHersteller = new Eintrag();
        seqKitHersteller.setFeldname("SeqKitHersteller");
        seqKitHersteller.setWert(kitManufacturer);

        // TudokEintrag: Eintrag : SeqKitTyp
        Eintrag seqKitTyp = new Eintrag();
        seqKitTyp.setFeldname("SeqKitTyp");

        // TudokEintrag: Eintrag : SeqPipeline
        Eintrag seqPipeline = new Eintrag();
        seqPipeline.setFeldname("SeqPipeline");

        // if GENERAL ➔ LABTEST_DISPLAY_NAME = VCF ArcherDx VP Complete Solid Tumor (unpaired) PathoMarburg
        //then Archer VariantPlex Complete Solid Tumor
        //if GENERAL ➔ LABTEST_DISPLAY_NAME = VCF ArcherDx VP Complete Solid Tumor + FP Pan Solid Tumor PathoMarburg v2
        //then Archer VariantPlex Complete Solid Tumor + FusionPlex Pan Solid Tumor v2
        String lastestDisplayName = mhGuideInfo.getGeneralInfo().getLastestDisplayName();
        if (Objects.equals(lastestDisplayName, "VCF ArcherDx VP Complete Solid Tumor (unpaired) PathoMarburg")) {
            seqKitTyp.setWert("Archer VariantPlex Complete Solid Tumor");
            seqPipeline.setWert("urn:Marburg:VP:CST:1.0:MHGuide:6.3.0");
        } else if (Objects.equals(lastestDisplayName, "VCF ArcherDx VP Complete Solid Tumor + FP Pan Solid Tumor PathoMarburg v2")) {
            seqKitTyp.setWert("Archer VP Complete Solid Tumor + FP Pan Solid Tumor v2");
            seqPipeline.setWert("urn:Marburg:VP:CST:1.0:FP:PST:1.0:MHGuide:6.3.0");
        }

        // TudokEintrag: Eintrag : Sequenziergeraet
        Eintrag sequenziergeraet = new Eintrag();
        sequenziergeraet.setFeldname("Sequenziergeraet");
        sequenziergeraet.setWert(sequencer);

        // TudokEintrag: Eintrag : Tumorzellgehalt
        Eintrag tumorzellgehalt = new Eintrag();
        tumorzellgehalt.setFeldname("Tumorzellgehalt");
        tudokEintrag.setEintraege(Arrays.asList(analyseID, analyseMethode, analyseMethoden, artDerSequenzierung, artinsituHybridisierung, befund, bemerkung, blocknummer, datum, doc,
                durchfuehrendeOE, einsendenummer, entnahmedatum, entnahmemethode, ergebnisMSI,  genetischeVeraenderung, genexpressionstests, hRD, iCDO3Lokalisation, internExtern, eintragMolekulargenetischeUntersuchung,
                panelEintrag, probeID, probenmaterial, projekt, referenzGenom, seqKitHersteller, seqKitTyp, seqPipeline, sequenziergeraet, tumorMutationalBurden, tumorzellgehalt));
        tudokEintrag.setRevision(1);
        tudokEintrag.setBearbeitungStatus(0);
        return tudokEintrag;
    }
}
