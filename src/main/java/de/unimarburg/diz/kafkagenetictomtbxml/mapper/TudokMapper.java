package de.unimarburg.diz.kafkagenetictomtbxml.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.unimarburg.diz.kafkagenetictomtbxml.model.MtbPidInfo;
import de.unimarburg.diz.kafkagenetictomtbxml.model.mhGuide.GeneralInfo;
import de.unimarburg.diz.kafkagenetictomtbxml.model.mhGuide.MHGuide;
import de.unimarburg.diz.kafkagenetictomtbxml.model.mhGuide.VariantLongList;
import de.unimarburg.diz.kafkagenetictomtbxml.model.onkostarXml.*;
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
    private final UnterformularRANFusionMapper unterformularRANFusionMapper;
    private final String kitManufacturer;
    private final String sequencer;
    private final String referenceGenome;
    private final String sequencingType;
    private final String panel;

    DokumentierendeFachabteilung dokumentierendeFachabteilung = new DokumentierendeFachabteilung();

    public TudokMapper(UnterformularSVMapper unterformularSVMapper, UnterformularCNVMapper unterformularCNVMapper, UnterformularRANFusionMapper unterformularRANFusionMapper,
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

    public TudokEintrag createTudokEintrag(MHGuide mhGuideInfo, MtbPidInfo mtbPidInfo) throws JsonProcessingException {
        TudokEintrag tudokEintrag = new TudokEintrag();


        // TudokEintrag : ExportID
        tudokEintrag.setExportID(3);
        tudokEintrag.setErkrankungExportID(2);
        // TudokEintrag :
        tudokEintrag.setTumorId(mtbPidInfo.getTumorId());
        // TudokEintrag // ToDo
        tudokEintrag.setStartDatum(mtbPidInfo.getDiagnoseDatum());
        // TudokEintrag :
        tudokEintrag.setFormularName("OS.Molekulargenetik");
        // TudokEintrag :
        tudokEintrag.setFormularVersion(1);
        // TudokEintrag :
        tudokEintrag.setProzedurtyp("Beobachtung");
        // TudokEintrag :
        dokumentierendeFachabteilung.setFachabteilungKennung("Tumordokumentation");
        // TudokEintrag :
        dokumentierendeFachabteilung.setEinrichtungKennung("CCC");
        // TudokEintrag: Eintrag: Feldname = AnalyseID
        Eintrag analyseID = new Eintrag();
        analyseID.setFeldname("AnalyseID");

        // TudokEintrag: Eintrag: Feldname = AnalyseMethode
        Eintrag analyseMethode = new Eintrag();
        analyseMethode.setFeldname("AnalyseMethode");
        analyseMethode.setWert("");

        // TudokEintrag: Eintrag: Feldname = AnalyseMethoden
        Eintrag analyseMethoden = new Eintrag();
        analyseMethoden.setFeldname("AnalyseMethoden");
        // application.yml
        analyseMethoden.setWert("S,H,");
        analyseMethoden.setFilterkategorie("{}");
        analyseMethoden.setVersion("OS.MolArtderHybridisierung.v1");

        // TudokEintrag: Eintrag: Feldname = ArtDerSequenzierung
        Eintrag artDerSequenzierung = new Eintrag();
        artDerSequenzierung.setFeldname("ArtDerSequenzierung");
        artDerSequenzierung.setWert(sequencingType);
        artDerSequenzierung.setFilterkategorie("{}");
        artDerSequenzierung.setVersion("OS.MolArtderHybridisierung.v1");
        artDerSequenzierung.setKurztext("");

        // TudokEintrag: Eintrag: Feldname = ArtinsituHybridisierung
        Eintrag artinsituHybridisierung = new Eintrag();
        artinsituHybridisierung.setFeldname("ArtinsituHybridisierung");
        artinsituHybridisierung.setWert("");
        artinsituHybridisierung.setFilterkategorie("{}");
        artinsituHybridisierung.setKurztext("");
        artinsituHybridisierung.setVersion("");

        // TudokEintrag: Eintrag: Feldname = Blocknummer
        Eintrag blocknummer = new Eintrag();
        blocknummer.setFeldname("Blocknummer");
        blocknummer.setWert("");


        // TudokEintrag: Eintrag: Feldname = Datum
        Eintrag datum = new Eintrag();
        datum.setFeldname("Datum");
        datum.setWert(new Date().toString());
        datum.setGenauigkeit("");

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
        durchfuehrendeOE.setWert("");
        durchfuehrendeOE.setKurztext("");
        durchfuehrendeOE.setFachabteilungKennung("");

        // TudokEintrag: Eintrag: Feldname = Einsendenummer
        Eintrag einsendenummer = new Eintrag();
        einsendenummer.setFeldname("Einsendenummer");
        einsendenummer.setWert(mtbPidInfo.getOrderId());

        // TudokEintrag: Eintrag: Feldname = Entnahmedatum
        Eintrag entnahmedatum = new Eintrag();
        entnahmedatum.setFeldname("Entnahmedatum");
        entnahmedatum.setWert("2024-07-21");

        // TudokEintrag: Eintrag: Feldname = Entnahmemethode
        Eintrag entnahmemethode = new Eintrag();
        entnahmemethode.setFeldname("EntnahmeMethoden");
        entnahmemethode.setWert("B");
        entnahmemethode.setFilterkategorie("{}");
        entnahmemethode.setVersion("OS.MolDiagEntnahmemethode.v1");
        entnahmemethode.setKurztext("Biopsie");

        // TudokEintrag: Eintrag : ErgebnisMSI
        Eintrag ergebnisMSI = new Eintrag();
        ergebnisMSI.setFeldname("ErgebnisMSI");
        ergebnisMSI.setWert("");
        ergebnisMSI.setFilterkategorie("{}");

        // TudokEintrag: Eintrag: Feldname = InternExtern
        Eintrag internExtern = new Eintrag();
        internExtern.setFeldname("InternExtern");
        internExtern.setWert("");
        internExtern.setFilterkategorie("{}");
        internExtern.setVersion("OS.MolDiagInternExtern.v1");
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
        for (VariantLongList variantLongList : variantLongLists) {
            var variantType = variantLongList.getDisplayVariantType();
            if ( variantType != null) {
                System.out.println("Current Variation" + variantLongList.getDisplayVariantType());
                switch (variantType) {
                    case "SNV":
                        log.info("SNV found");
                        var  unterformularSV = unterformularSVMapper.createXmlUnterformularSV(mtbPidInfo,variantLongList, dokumentierendeFachabteilung);
                        unterformularList.add(unterformularSV);
                        log.info("New formular created and add to the list of unterformular");
                        break;
                    case "CNV":
                        log.info("CNV found");
                        var  unterformularCNV = unterformularCNVMapper.createXmlUnterformularCNV(mtbPidInfo,variantLongList, dokumentierendeFachabteilung);
                        unterformularList.add(unterformularCNV);
                        log.info("New formular created and add to the list of unterformular");
                        break;
                    case "fusion":
                        var  unterformularRNAFusion = unterformularRANFusionMapper.createXmlUnterformularRANFusion(mtbPidInfo,variantLongList, dokumentierendeFachabteilung);
                        unterformularList.add(unterformularRNAFusion);
                        break;
                    case "TMB":
                        log.info("TMB found");
                        tumorMutationalBurden.setWert(variantLongList.getTmbVariantCountPerMegabase());
                        break;
                    case "MSI":
                        log.info("MSI found");
                        var variantSymbol = variantLongList.getVariantSymbol();
                        if (variantSymbol.equals("MSS")){
                            ergebnisMSI.setWert(variantLongList.getGenomicExtraData());
                        }
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
            seqKitTyp.setWert("Archer VariantPlex Complete Solid Tumor + FusionPlex Pan Solid Tumor v2");
            seqPipeline.setWert("urn:Marburg:VP:CST:1.0:FP:PST:1.0:MHGuide:6.3.0");
        }

        // TudokEintrag: Eintrag : Sequenziergeraet
        Eintrag sequenziergeraet = new Eintrag();
        sequenziergeraet.setFeldname("Sequenziergeraet");
        sequenziergeraet.setWert(sequencer);

        // TudokEintrag: Eintrag : Tumorzellgehalt
        Eintrag tumorzellgehalt = new Eintrag();
        tumorzellgehalt.setFeldname("Tumorzellgehalt");

        tudokEintrag.setEintraege(Arrays.asList(analyseID, analyseMethode, analyseMethoden, artDerSequenzierung, artinsituHybridisierung, blocknummer, datum, doc,
                durchfuehrendeOE, einsendenummer, entnahmedatum, entnahmemethode, ergebnisMSI, internExtern, eintragMolekulargenetischeUntersuchung,
                panelEintrag, probeID, probenmaterial, projekt, referenzGenom, seqKitHersteller, seqKitTyp, seqPipeline, sequenziergeraet, tumorMutationalBurden, tumorzellgehalt));
        tudokEintrag.setRevision(7);
        tudokEintrag.setBearbeitungStatus(2);

        return tudokEintrag;
    }
}
