package de.unimarburg.diz.kafkagenetictomtbxml.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.unimarburg.diz.kafkagenetictomtbxml.model.MtbPatientInfo;
import de.unimarburg.diz.kafkagenetictomtbxml.model.mhGuide.GeneralInfo;
import de.unimarburg.diz.kafkagenetictomtbxml.model.mhGuide.MHGuide;
import de.unimarburg.diz.kafkagenetictomtbxml.model.mhGuide.Variant;
import de.unimarburg.diz.kafkagenetictomtbxml.model.onkostarXml.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class TudokMapper {
    private final static Logger log = LoggerFactory.getLogger(TudokMapper.class);

    private final UnterformularSVMapper unterformularSVMapper;
    private final UnterformularCNVMapper unterformularCNVMapper;
    private final UnterformularRNAFusionMapper unterformularRANFusionMapper;
    private final UnterformularKomplexBiomarkerMapperMSI unterformularKomplexBiomarkerMapperMSI;
    private final UnterformularKomplexBiomarkerMapperTMB unterformularKomplexBiomarkerMapperTMB;
    private final String kitManufacturer;
    private final String sequencer;
    private final String referenceGenome;
    private final String sequencingType;
    private final String sequencingTypeShort;
    private final String panelVP;
    private final String panelVPCP;
    private final String seqKitTypVP;
    private final String seqPipelineVP;
    private final String seqKitTypVPCP;
    private final String seqPipelineVPCP;
    private final String fachabteilungKennung;
    private final String einrichtungKennung;
    private final String durchfuehrendeOE;
    private final String durchfuehrendeOEKurzText;
    private final String internExternWert;
    private final String internExternKurztext;
    private final String analyseMethodenVal;
    private final String classificationName;
    private final String oncogenicClassificationName;


    DokumentierendeFachabteilung dokumentierendeFachabteilung = new DokumentierendeFachabteilung();
    public TudokMapper(UnterformularSVMapper unterformularSVMapper, UnterformularCNVMapper unterformularCNVMapper, UnterformularRNAFusionMapper unterformularRANFusionMapper,
                       UnterformularKomplexBiomarkerMapperMSI unterformularKomplexBiomarkerMapperMSI, UnterformularKomplexBiomarkerMapperTMB unterformularKomplexBiomarkerMapperTMB,
                       @Value("${metadata.ngsReports.kitManufacturer}") String kitManufacturer,
                       @Value("${metadata.ngsReports.sequencer}") String sequencer,
                       @Value("${metadata.ngsReports.referenceGenome}") String referenceGenome,
                       @Value("${metadata.ngsReports.sequencingType}") String sequencingType,
                       @Value("${metadata.ngsReports.sequencingTypeShort}") String sequencingTypeShort,
                       @Value("${metadata.ngsReports.panelVP}") String panelVP,@Value("${metadata.ngsReports.panelVPCP}") String panelVPCP,
                       @Value("${metadata.ngsReports.seqKitTypVP}")  String seqKitTypVP,
                       @Value("${metadata.ngsReports.seqPipelineVP}") String seqPipelineVP,
                       @Value("${metadata.ngsReports.seqKitTypVPCP}") String seqKitTypVPCP,
                       @Value("${metadata.ngsReports.seqPipelineVPCP}") String seqPipelineVPCP,
                       @Value("${metadata.ngsReports.fachabteilungKennung}") String fachabteilungKennung,
                       @Value("${metadata.ngsReports.einrichtungKennung}") String einrichtungKennung,
                       @Value("${metadata.ngsReports.durchfuehrendeOE}") String durchfuehrendeOE,
                       @Value("${metadata.ngsReports.durchfuehrendeOEKurzText}") String durchfuehrendeOEKurzText,
                       @Value("${metadata.ngsReports.internExternWert}") String internExternWert,
                       @Value("${metadata.ngsReports.internExternKurztext}") String internExternKurztext,
                       @Value("${metadata.ngsReports.analyseMethoden}") String analyseMethodenVal,
                       @Value("${metadata.ngsReports.classificationName}") String classificationName,
                       @Value("${metadata.ngsReports.oncogenicClassificationName}") String oncogenicClassificationName
                       ) {
        this.unterformularSVMapper = unterformularSVMapper;
        this.unterformularCNVMapper = unterformularCNVMapper;
        this.unterformularRANFusionMapper = unterformularRANFusionMapper;
        this.unterformularKomplexBiomarkerMapperMSI = unterformularKomplexBiomarkerMapperMSI;
        this.unterformularKomplexBiomarkerMapperTMB = unterformularKomplexBiomarkerMapperTMB;
        this.kitManufacturer = kitManufacturer;
        this.sequencer = sequencer;
        this.referenceGenome = referenceGenome;
        this.sequencingType = sequencingType;
        this.sequencingTypeShort = sequencingTypeShort;
        this.panelVP = panelVP;
        this.panelVPCP = panelVPCP;
        this.seqKitTypVP = seqKitTypVP;
        this.seqPipelineVP = seqPipelineVP;
        this.seqKitTypVPCP = seqKitTypVPCP;
        this.seqPipelineVPCP = seqPipelineVPCP;
        this.fachabteilungKennung = fachabteilungKennung;
        this.einrichtungKennung = einrichtungKennung;
        this.durchfuehrendeOE = durchfuehrendeOE;
        this.durchfuehrendeOEKurzText = durchfuehrendeOEKurzText;
        this.internExternWert = internExternWert;
        this.internExternKurztext = internExternKurztext;
        this.analyseMethodenVal = analyseMethodenVal;
        this.classificationName = classificationName;
        this.oncogenicClassificationName = oncogenicClassificationName;
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
        dokumentierendeFachabteilung.setFachabteilungKennung(fachabteilungKennung);
        dokumentierendeFachabteilung.setEinrichtungKennung(einrichtungKennung);
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
        analyseMethoden.setWert(analyseMethodenVal);
        analyseMethoden.setFilterkategorie("{}");
        analyseMethoden.setVersion("OS.MolDiagMethode.v1");

        // TudokEintrag: Eintrag: Feldname = ArtDerSequenzierung
        Eintrag artDerSequenzierung = new Eintrag();
        artDerSequenzierung.setFeldname("ArtDerSequenzierung");
        artDerSequenzierung.setWert(sequencingType);
        artDerSequenzierung.setFilterkategorie("{}");
        artDerSequenzierung.setVersion("OS.MolDiagSequenzierung.v1");
        artDerSequenzierung.setKurztext(sequencingTypeShort);

        // TudokEintrag: Eintrag: Feldname = ArtinsituHybridisierung
        // Only if analysemethode = "H"
        // Actual not necessary due to actual analysemethode is "S"
        Eintrag artinsituHybridisierung = new Eintrag();
        artinsituHybridisierung.setFeldname("ArtinsituHybridisierung");
        artinsituHybridisierung.setWert("");
        artinsituHybridisierung.setFilterkategorie("");
        artinsituHybridisierung.setVersion("");
        artinsituHybridisierung.setKurztext("");

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
        // this is necessary to make clear
        // Extract the general part of the mh-guide
        GeneralInfo generalInfo = mhGuideInfo.getGeneralInfo();
        Eintrag datum = new Eintrag();
        datum.setFeldname("Datum");
        datum.setWert(generalInfo.getOrderDate());
        datum.setGenauigkeit("exact");

        // TudokEintrag: Eintrag: Feldname = Dokumentation
        Eintrag doc = new Eintrag();
        doc.setFeldname("Dokumentation");
        doc.setWert("BAS");
        doc.setFilterkategorie("{}");
        doc.setVersion("OS.MolDokumentation.v1");
        doc.setKurztext("Basis");

        // TudokEintrag: Eintrag: Feldname = DurchfuehrendeOE
        Eintrag durchfuehrendeOEFeld = new Eintrag();
        durchfuehrendeOEFeld.setFeldname("DurchfuehrendeOE");
        durchfuehrendeOEFeld.setWert(durchfuehrendeOE);
        durchfuehrendeOEFeld.setKurztext(durchfuehrendeOEKurzText);


        // TudokEintrag: Eintrag: Feldname = Einsendenummer
        Eintrag einsendenummer = new Eintrag();
        einsendenummer.setFeldname("Einsendenummer");
        einsendenummer.setWert(mtbPatientInfo.getEinsendennummer());

        // TudokEintrag: Eintrag: Feldname = Entnahmedatum
        Eintrag entnahmedatum = new Eintrag();
        entnahmedatum.setFeldname("Entnahmedatum");
        //entnahmedatum.setWert("");
        //entnahmedatum.setGenauigkeit("");

        // TODO: TudokEintrag: Eintrag: Feldname = Entnahmemethode
        // Unterschied Neue und Alte
        //- EntnahmeMethode zu Entnahmemethode
        Eintrag entnahmemethode = new Eintrag();
        entnahmemethode.setFeldname("Entnahmemethode");
        //entnahmemethode.setWert("");
        entnahmemethode.setFilterkategorie("");
        //entnahmemethode.setVersion("");
        //entnahmemethode.setKurztext("");

        // TudokEintrag: Eintrag : ErgebnisMSI
        // Let here MSI empty, it was
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
        internExtern.setWert(internExternWert);
        internExtern.setFilterkategorie("{}");
        internExtern.setVersion("OS.OrtDurchfuehrung.v1");
        internExtern.setKurztext(internExternKurztext);

        // TudokEintrag: Eintrag: Feldname = MolekulargenetischeUntersuchung
        Eintrag eintragMolekulargenetischeUntersuchung = new Eintrag();
        eintragMolekulargenetischeUntersuchung.setFeldname("MolekulargenetischeUntersuchung");
        List<Unterformular> unterformularListMolUntersuchung = new ArrayList<>();


        // TudokEintrag: Eintrag: Feldname = MolekulargenetischeUntersuchung
        Eintrag eintragKBiomarker = new Eintrag();
        eintragKBiomarker.setFeldname("KomplexeBiomarker");
        List<Unterformular> unterformularListKBiomarker = new ArrayList<>();

        Eintrag materialfixierung = new Eintrag();
        materialfixierung.setFeldname("Materialfixierung");
        materialfixierung.setWert("3");
        materialfixierung.setVersion("OS.Material.v1");
        materialfixierung.setKurztext("FFPE-Fixierung");

        // Eintrag : Feldname = MolekulargenetischeUntersuchung
        // MolekulargenetischeUntersuchung: Formular
        // UnterformaularTyp: SimpleVariant (SV)

        List<Variant> variantLongListsAll = mhGuideInfo.getVariant();
        // length of variantlong list
        var  lengthVariantLongList = variantLongListsAll.size();
        log.info("Length of current variantList: {}", lengthVariantLongList);
        var startExportIDUNterformular = 4;
        //TODO: Need to implement for filtering only notable biomarkers
        //List<Variant> variantFiltered = filterBiomarkers(variantLongListsAll);
        List<String> classificationNameList  = Arrays.asList(classificationName.split(",\\s*"));
        List<String>  oncogenicClassificationNameList = Arrays.asList(oncogenicClassificationName.split(",\\s*"));

        for (Variant variant : variantLongListsAll) {
            var variantType = variant.getDisplayVariantType();
            String classificationName = variant.getClassificationName();
            String oncogenicClassificationName = variant.getOncogenicClassificationName();
            if (variantType != null) {
                switch (variantType) {
                    case "SNV":
                        log.info("SNV found");
                        if(oncogenicClassificationName != null && !oncogenicClassificationName.isEmpty()) {
                            if(oncogenicClassificationNameList.contains(oncogenicClassificationName)){
                                var  unterformularSV = unterformularSVMapper.createXmlUnterformularSV(mtbPatientInfo, variant, dokumentierendeFachabteilung, startExportIDUNterformular);
                                unterformularListMolUntersuchung.add(unterformularSV);
                                log.info("New subform created for SNV and add to the list of unterformular");
                                startExportIDUNterformular++;
                            }
                        } else if (oncogenicClassificationName == null && classificationName != null && !classificationName.isEmpty()) {
                            if (classificationNameList.contains(classificationName)) {
                                var unterformularSV = unterformularSVMapper.createXmlUnterformularSV(mtbPatientInfo, variant, dokumentierendeFachabteilung, startExportIDUNterformular);
                                unterformularListMolUntersuchung.add(unterformularSV);
                                log.info("New subform created for SNV and add to the list of unterformular");
                                startExportIDUNterformular++;
                            }
                        }
                        break;
                    case "CNV":
                        log.info("CNV found");

                        if(oncogenicClassificationName != null && !oncogenicClassificationName.isEmpty()) {
                            if(oncogenicClassificationNameList.contains(oncogenicClassificationName)){
                                var  unterformularCNV = unterformularCNVMapper.createXmlUnterformularCNV(mtbPatientInfo, variant, dokumentierendeFachabteilung, startExportIDUNterformular);
                                unterformularListMolUntersuchung.add(unterformularCNV);
                                log.info("New subform created for CNV and add to the list of unterformular");
                                startExportIDUNterformular++;

                            }
                        } else if (oncogenicClassificationName == null && classificationName != null && !classificationName.isEmpty()) {
                            if (classificationNameList.contains(classificationName)) {
                                var  unterformularCNV = unterformularCNVMapper.createXmlUnterformularCNV(mtbPatientInfo, variant, dokumentierendeFachabteilung, startExportIDUNterformular);
                                unterformularListMolUntersuchung.add(unterformularCNV);
                                log.info("New subform created for CNV and add to the list of unterformular");
                                startExportIDUNterformular++;
                            }
                        }
                        break;
                    case "fusion":
                        if(oncogenicClassificationName != null && !oncogenicClassificationName.isEmpty()) {
                            if(oncogenicClassificationNameList.contains(oncogenicClassificationName)){
                                var  unterformularRNAFusion = unterformularRANFusionMapper.createXmlUnterformularRANFusion(mtbPatientInfo, variant, dokumentierendeFachabteilung, startExportIDUNterformular);
                                unterformularListMolUntersuchung.add(unterformularRNAFusion);
                                log.info("New subform created for RNV Fusion and add to the list of unterformular");
                                startExportIDUNterformular++;
                            }
                        } else if (oncogenicClassificationName == null && classificationName != null && !classificationName.isEmpty()) {
                            if (classificationNameList.contains(classificationName)) {
                                var  unterformularRNAFusion = unterformularRANFusionMapper.createXmlUnterformularRANFusion(mtbPatientInfo, variant, dokumentierendeFachabteilung, startExportIDUNterformular);
                                unterformularListMolUntersuchung.add(unterformularRNAFusion);
                                startExportIDUNterformular++;
                                log.info("New subform created for RNV Fusion and add to the list of unterformular");
                            }
                        }
                        break;
                    case "TMB":
                        log.info("TMB found");
                        var unterformularKomplexBiomarkerTMB = unterformularKomplexBiomarkerMapperTMB.createXmlUnterformularKBiomarkerTMB(mtbPatientInfo, variant,dokumentierendeFachabteilung,startExportIDUNterformular);
                        unterformularListKBiomarker.add(unterformularKomplexBiomarkerTMB);
                        log.info("New subform created for complex biomarker MSI and add to the list of unterformular");
                        startExportIDUNterformular++;
                        break;
                    case "MSI":
                        log.info("MSI found");
                        var variantSymbol = variant.getVariantSymbol();
                        if (variantSymbol.equals("MSS")){
                            // This need to be make clear
                            //ergebnisMSI.setWert(variantLongList.getGenomicExtraData());
                            var unterformularKomplexBiomarkerMSI = unterformularKomplexBiomarkerMapperMSI.createXmlUnterformularKBiomarkerMSI(mtbPatientInfo, variant,dokumentierendeFachabteilung,startExportIDUNterformular);
                            unterformularListKBiomarker.add(unterformularKomplexBiomarkerMSI);
                            log.info("New subform created for complex biomarker TMB and add to the list of unterformular");
                            startExportIDUNterformular++;
                        }
                        break;
                }
            }else {
                log.warn(String.format("CASE_UUID: {%s}: MH Guide variation list element with DETECTED_VAR_ID: {%s} has empty CHROMOSOMAL_VARIANT_TYPE", mhGuideInfo.getGeneralInfo().getCaseUuid(), variant.getDetectedVarId()));
            }
        }
        log.info("Total number of created subform for  MolekulargenetischeUntersuchung: {}", unterformularListMolUntersuchung.size());
        eintragMolekulargenetischeUntersuchung.setUnterformulars(unterformularListMolUntersuchung);

        log.info("Total number of created subform for KomplexeBiomarker: {}", unterformularListKBiomarker.size());
        eintragKBiomarker.setUnterformulars(unterformularListKBiomarker);

        // TudokEintrag: Eintrag : Panel
        Eintrag panelEintrag = new Eintrag();
        panelEintrag.setFeldname("Panel");

        // TudokEintrag: Eintrag : ProbeID
        Eintrag probeID = new Eintrag();
        probeID.setFeldname("ProbeID");

        //TudokEintrag: Eintrag : Probenmaterial
        // Used for new version of onkostar
        Eintrag probenmaterial = new Eintrag();
        probenmaterial.setFeldname("Probenmaterial");
        probenmaterial.setWert("");
        probenmaterial.setVersion("OS.Probenmaterial.v1");
        probenmaterial.setKurztext("");


        // TudokEintrag: Eintrag : Projekt
        Eintrag projekt = new Eintrag();
        projekt.setFeldname("Projekt");
        projekt.setWert("ZPM,");
        projekt.setVersion("OS.MolDiag.Projekte.v1");

        // TudokEintrag: Eintrag : ReferenzGenom
        Eintrag referenzGenom = new Eintrag();
        referenzGenom.setFeldname("ReferenzGenom");
        referenzGenom.setWert(referenceGenome);
        referenzGenom.setVersion("OS.MolSeqGenom.v1");
        referenzGenom.setKurztext(referenceGenome);

        // TudokEintrag: Eintrag : SeqKitHersteller
        Eintrag seqKitHersteller = new Eintrag();
        seqKitHersteller.setFeldname("SeqKitHersteller");
        seqKitHersteller.setWert(kitManufacturer);
        seqKitHersteller.setVersion("OS.MolDiag.KitHersteller.v1");
        seqKitHersteller.setKurztext("Archer");

        // TudokEintrag: Eintrag : SeqKitTyp
        // Is not clear what is difference to panel
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
            // TODO! Need to checked the values present in panel and seqKitTyp
            panelEintrag.setWert(panelVP);
            // seqKitType need to be checked
            seqKitTyp.setWert(seqKitTypVP);
            seqPipeline.setWert(seqPipelineVP);
        } else if (Objects.equals(lastestDisplayName, "VCF ArcherDx VP Complete Solid Tumor + FP Pan Solid Tumor PathoMarburg v2")) {
            panelEintrag.setWert(panelVPCP);
            seqKitTyp.setWert(seqKitTypVPCP);
            seqPipeline.setWert(seqPipelineVPCP);
        }

        // TudokEintrag: Eintrag : Sequenziergeraet
        Eintrag sequenziergeraet = new Eintrag();
        sequenziergeraet.setFeldname("Sequenziergeraet");
        sequenziergeraet.setWert(sequencer);
        sequenziergeraet.setVersion("OS.MolDiag.Sequenziergerät.v1");
        sequenziergeraet.setKurztext("Illumina NovaSeq 6000");


        // TudokEintrag: Eintrag : Tumorzellgehalt
        Eintrag tumorzellgehalt = new Eintrag();
        tumorzellgehalt.setFeldname("Tumorzellgehalt");
        tudokEintrag.setEintraege(Arrays.asList(analyseID, analyseMethode, analyseMethoden, artDerSequenzierung, artinsituHybridisierung, befund, bemerkung, blocknummer, datum, doc,
                durchfuehrendeOEFeld, einsendenummer, entnahmedatum, entnahmemethode, ergebnisMSI,  genetischeVeraenderung, genexpressionstests, hRD, iCDO3Lokalisation, internExtern, eintragKBiomarker, eintragMolekulargenetischeUntersuchung,
                panelEintrag, probeID, probenmaterial, projekt, referenzGenom, seqKitHersteller, seqKitTyp, seqPipeline, sequenziergeraet, tumorzellgehalt));
        tudokEintrag.setRevision(1);
        tudokEintrag.setBearbeitungStatus(2);
        return tudokEintrag;
    }

    // Actual not in use
    public List<Variant> filterBiomarkers(List<Variant> variants) {
        List<String> classificationNameList  = Arrays.asList(classificationName.split(",\\s*"));
        List<String>  oncogenicClassificationNameList = Arrays.asList(oncogenicClassificationName.split(",\\s*"));
        return variants.stream().filter(variantLongList -> {
                    String classificationName = variantLongList.getClassificationName();
                    String oncogenicClassificationName = variantLongList.getOncogenicClassificationName();
                    if (oncogenicClassificationName != null && !oncogenicClassificationName.isEmpty()) {

                        return  oncogenicClassificationNameList.contains(oncogenicClassificationName);
                    }
                    return classificationName != null && !classificationName.isEmpty() && classificationNameList.contains(classificationName);
        }).collect(Collectors.toList());
    }



}
