package de.unimarburg.diz.kafkagenetictomtbxml.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.unimarburg.diz.kafkagenetictomtbxml.configuration.MetadataConfigurationProperties;
import de.unimarburg.diz.kafkagenetictomtbxml.model.MtbPatientInfo;
import de.unimarburg.diz.kafkagenetictomtbxml.model.mhGuide.*;
import de.unimarburg.diz.kafkagenetictomtbxml.model.onkostarXml.*;
import java.util.*;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TudokMapper {
  private static final Logger log = LoggerFactory.getLogger(TudokMapper.class);

  private final UnterformularSVMapper unterformularSVMapper;
  private final UnterformularCNVMapper unterformularCNVMapper;
  private final UnterformularRNAFusionMapper unterformularRANFusionMapper;
  private final UnterformularKomplexBiomarkerMapperMSI unterformularKomplexBiomarkerMapperMSI;
  private final UnterformularKomplexBiomarkerMapperTMB unterformularKomplexBiomarkerMapperTMB;
  private final MetadataConfigurationProperties metadataConfigurationProperties;

  DokumentierendeFachabteilung dokumentierendeFachabteilung = new DokumentierendeFachabteilung();

  public TudokMapper(
      UnterformularSVMapper unterformularSVMapper,
      UnterformularCNVMapper unterformularCNVMapper,
      UnterformularRNAFusionMapper unterformularRANFusionMapper,
      UnterformularKomplexBiomarkerMapperMSI unterformularKomplexBiomarkerMapperMSI,
      UnterformularKomplexBiomarkerMapperTMB unterformularKomplexBiomarkerMapperTMB,
      MetadataConfigurationProperties metadataConfigurationProperties) {
    this.unterformularSVMapper = unterformularSVMapper;
    this.unterformularCNVMapper = unterformularCNVMapper;
    this.unterformularRANFusionMapper = unterformularRANFusionMapper;
    this.unterformularKomplexBiomarkerMapperMSI = unterformularKomplexBiomarkerMapperMSI;
    this.unterformularKomplexBiomarkerMapperTMB = unterformularKomplexBiomarkerMapperTMB;
    this.metadataConfigurationProperties = metadataConfigurationProperties;
  }

  public TudokEintrag createTudokEintrag(MHGuide mhGuideInfo, MtbPatientInfo mtbPatientInfo)
      throws JsonProcessingException {
    TudokEintrag tudokEintrag = new TudokEintrag();
    // TudokEintrag : ExportID
    tudokEintrag.setExportID(3);
    //
    tudokEintrag.setErkrankungExportID(2);
    // TudokEintrag :
    tudokEintrag.setTumorId(mtbPatientInfo.getTumorId());
    // TudokEintrag //
    dokumentierendeFachabteilung.setFachabteilungKennung(
        metadataConfigurationProperties.getNgsReports().getFachabteilungKennung());
    dokumentierendeFachabteilung.setEinrichtungKennung(
        metadataConfigurationProperties.getNgsReports().getEinrichtungKennung());
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

    Eintrag dokumentation = new Eintrag();
    dokumentation.setFeldname("Dokumentation");
    dokumentation.setWert("ERW");
    dokumentation.setFilterkategorie("{}");
    dokumentation.setVersion("OS.MolDokumentation.v1");
    dokumentation.setKurztext("Erweitert");

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
    analyseMethoden.setWert(metadataConfigurationProperties.getNgsReports().getAnalyseMethoden());
    analyseMethoden.setFilterkategorie("{}");
    analyseMethoden.setVersion("OS.MolDiagMethode.v1");

    // TudokEintrag: Eintrag: Feldname = ArtDerSequenzierung
    Eintrag artDerSequenzierung = new Eintrag();
    artDerSequenzierung.setFeldname("ArtDerSequenzierung");
    artDerSequenzierung.setWert(
        metadataConfigurationProperties.getNgsReports().getSequencingType());
    artDerSequenzierung.setFilterkategorie("{}");
    artDerSequenzierung.setVersion("OS.MolDiagSequenzierung.v1");
    artDerSequenzierung.setKurztext(
        metadataConfigurationProperties.getNgsReports().getSequencingTypeShort());

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

    // TudokEintrag: Eintrag: Feldname = DurchfuehrendeOE
    Eintrag durchfuehrendeOEFeld = new Eintrag();
    durchfuehrendeOEFeld.setFeldname("DurchfuehrendeOE");
    durchfuehrendeOEFeld.setWert(
        metadataConfigurationProperties.getNgsReports().getDurchfuehrendeOE());
    durchfuehrendeOEFeld.setKurztext(
        metadataConfigurationProperties.getNgsReports().getDurchfuehrendeOEKurzText());

    // TudokEintrag: Eintrag: Feldname = Einsendenummer
    Eintrag einsendenummer = new Eintrag();
    einsendenummer.setFeldname("Einsendenummer");
    einsendenummer.setWert(mtbPatientInfo.getEinsendennummer());

    // TudokEintrag: Eintrag: Feldname = Entnahmedatum
    Eintrag entnahmedatum = new Eintrag();
    entnahmedatum.setFeldname("Entnahmedatum");
    // entnahmedatum.setWert("");
    // entnahmedatum.setGenauigkeit("");

    // TODO: TudokEintrag: Eintrag: Feldname = Entnahmemethode
    // Unterschied Neue und Alte
    // - EntnahmeMethode zu Entnahmemethode
    Eintrag entnahmemethode = new Eintrag();
    entnahmemethode.setFeldname("Entnahmemethode");
    // entnahmemethode.setWert("");
    entnahmemethode.setFilterkategorie("");
    // entnahmemethode.setVersion("");
    // entnahmemethode.setKurztext("");

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
    internExtern.setWert(metadataConfigurationProperties.getNgsReports().getInternExternWert());
    internExtern.setFilterkategorie("{}");
    internExtern.setVersion("OS.OrtDurchfuehrung.v1");
    internExtern.setKurztext(
        metadataConfigurationProperties.getNgsReports().getInternExternKurztext());

    // TudokEintrag: Eintrag: Feldname = MolekulargenetischeUntersuchung
    Eintrag eintragMolekulargenetischeUntersuchung = new Eintrag();
    eintragMolekulargenetischeUntersuchung.setFeldname("MolekulargenetischeUntersuchung");
    List<Unterformular> unterformularListMolUntersuchung = new ArrayList<>();

    // TudokEintrag: Eintrag: Feldname = MolekulargenetischeUntersuchung
    Eintrag eintragKBiomarker = new Eintrag();
    eintragKBiomarker.setFeldname("KomplexeBiomarker");
    List<Unterformular> unterformularListKBiomarker = new ArrayList<>();

    // TudokEintrag: Eintrag: Feldname = MolekulargenetischeUntersuchung
    Eintrag eintragDel = new Eintrag();
    eintragDel.setFeldname("GenetischeVeraenderung");
    List<Unterformular> unterformularListDel = new ArrayList<>();

    Eintrag materialfixierung = new Eintrag();
    materialfixierung.setFeldname("Materialfixierung");
    materialfixierung.setWert("3");
    materialfixierung.setVersion("OS.Material.v1");
    materialfixierung.setKurztext("FFPE-Fixierung");

    // Eintrag : Feldname = MolekulargenetischeUntersuchung
    // MolekulargenetischeUntersuchung: Formular
    // UnterformaularTyp: SimpleVariant (SV)

    List<Variant> variantLongListsAll = mhGuideInfo.getVariant();
    // Extract Notable-Biomarkers
    List<NotableBiomarker> notableBiomarkers =
        mhGuideInfo.getBiomarkerData().getNotableBiomarkerList();
    List<Integer> variationIdList = new ArrayList<>();
    for (NotableBiomarker notableBiomarker : notableBiomarkers) {
      for (BioMarker biomarker : notableBiomarker.getBiomarkers()) {
        variationIdList.add(biomarker.getDetectedVarId());
      }
    }
    // length of variantlong lis
    log.info("Length of current variantList: {}", variantLongListsAll.size());
    log.info("Length of notable biomarkers: {}", variationIdList.size());
    var startExportIDUNterformular = 4;
    // TODO: Need to implement for filtering only notable biomarkers
    // List<Variant> variantFiltered = filterBiomarkers(variantLongListsAll);
    // List<String> classificationNameList  = Arrays.asList(classificationName.split(",\\s*"));
    // List<String>  oncogenicClassificationNameList =
    // Arrays.asList(oncogenicClassificationName.split(",\\s*"));
    /*for (Variant variant : variantLongListsAll) {
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
    }*/
    // only oncogenic or benign variants are saved to xml
    for (Variant variant : variantLongListsAll) {
      var variantType = variant.getDisplayVariantType();
      if (null != variant.getOncogenicClassificationName()
          && (variant.getOncogenicClassificationName().toLowerCase().contains("oncogenic")
              || variant.getOncogenicClassificationName().toLowerCase().contains("benign"))) {
        switch (variantType) {
          case "SNV":
          case "del":
            log.info("SNV or del found");
            var unterformularSV =
                unterformularSVMapper.createXmlUnterformularSV(
                    mtbPatientInfo,
                    variant,
                    dokumentierendeFachabteilung,
                    startExportIDUNterformular);
            unterformularListMolUntersuchung.add(unterformularSV);
            log.info("New subform created for SNV and add to the list of unterformular");
            startExportIDUNterformular++;
            break;

          case "CNV":
            log.info("CNV found");
            var unterformularCNV =
                unterformularCNVMapper.createXmlUnterformularCNV(
                    mtbPatientInfo,
                    variant,
                    dokumentierendeFachabteilung,
                    startExportIDUNterformular);
            unterformularListMolUntersuchung.add(unterformularCNV);
            log.info("New subform created for CNV and add to the list of unterformular");
            startExportIDUNterformular++;
            break;
          case "fusion":
            var unterformularRNAFusion =
                unterformularRANFusionMapper.createXmlUnterformularRANFusion(
                    mtbPatientInfo,
                    variant,
                    dokumentierendeFachabteilung,
                    startExportIDUNterformular);
            unterformularListMolUntersuchung.add(unterformularRNAFusion);
            log.info("New subform created for RNV Fusion and add to the list of unterformular");
            startExportIDUNterformular++;
            break;
          case "TMB":
            log.info("TMB found");
            var unterformularKomplexBiomarkerTMB =
                unterformularKomplexBiomarkerMapperTMB.createXmlUnterformularKBiomarkerTMB(
                    mtbPatientInfo,
                    variant,
                    dokumentierendeFachabteilung,
                    startExportIDUNterformular);
            unterformularListKBiomarker.add(unterformularKomplexBiomarkerTMB);
            log.info(
                "New subform created for complex biomarker MSI and add to the list of unterformular");
            startExportIDUNterformular++;
            break;
          case "MSI":
            log.info("MSI found");
            var variantSymbol = variant.getVariantSymbol();
            if (variantSymbol.equals("MSS")) {
              // This need to be make clear
              // ergebnisMSI.setWert(variantLongList.getGenomicExtraData());
              var unterformularKomplexBiomarkerMSI =
                  unterformularKomplexBiomarkerMapperMSI.createXmlUnterformularKBiomarkerMSI(
                      mtbPatientInfo,
                      variant,
                      dokumentierendeFachabteilung,
                      startExportIDUNterformular);
              unterformularListKBiomarker.add(unterformularKomplexBiomarkerMSI);
              log.info(
                  "New subform created for complex biomarker TMB and add to the list of unterformular");
              startExportIDUNterformular++;
            }
            break;
        }
      } else {
        log.warn("Notable Biomarkers not available");
      }
    }

    log.info(
        "Total number of created subform for  MolekulargenetischeUntersuchung: {}",
        unterformularListMolUntersuchung.size());
    eintragMolekulargenetischeUntersuchung.setUnterformulars(unterformularListMolUntersuchung);

    log.info(
        "Total number of created subform for KomplexeBiomarker: {}",
        unterformularListKBiomarker.size());
    eintragKBiomarker.setUnterformulars(unterformularListKBiomarker);

    log.info("Total number of created subform for Veraenderung: {}", unterformularListDel.size());
    eintragDel.setUnterformulars(unterformularListDel);

    // TudokEintrag: Eintrag : Panel
    Eintrag panelEintrag = new Eintrag();
    panelEintrag.setFeldname("Panel");

    // TudokEintrag: Eintrag : ProbeID
    Eintrag probeID = new Eintrag();
    probeID.setFeldname("ProbeID");

    // TudokEintrag: Eintrag : Probenmaterial
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
    referenzGenom.setWert(metadataConfigurationProperties.getNgsReports().getReferenceGenome());
    referenzGenom.setVersion("OS.MolSeqGenom.v1");
    referenzGenom.setKurztext(metadataConfigurationProperties.getNgsReports().getReferenceGenome());
    // Aus MH Guide, wenn verfügbar.
    if ("37".equals(mhGuideInfo.getGeneralInfo().getRefGenomeVersion())) {
      referenzGenom.setWert("HG19");
      referenzGenom.setKurztext("HG19");
    } else if ("38".equals(mhGuideInfo.getGeneralInfo().getRefGenomeVersion())) {
      referenzGenom.setWert("HG38");
      referenzGenom.setKurztext("HG38");
    }

    // TudokEintrag: Eintrag : SeqKitHersteller
    Eintrag seqKitHersteller = new Eintrag();
    seqKitHersteller.setFeldname("SeqKitHersteller");
    seqKitHersteller.setWert(metadataConfigurationProperties.getNgsReports().getKitManufacturer());
    seqKitHersteller.setVersion("OS.MolDiag.KitHersteller.v1");
    seqKitHersteller.setKurztext("Archer");

    // TudokEintrag: Eintrag : SeqKitTyp
    // Is not clear what is difference to panel
    Eintrag seqKitTyp = new Eintrag();
    seqKitTyp.setFeldname("SeqKitTyp");

    // TudokEintrag: Eintrag : SeqPipeline
    Eintrag seqPipeline = new Eintrag();
    seqPipeline.setFeldname("SeqPipeline");

    // if GENERAL ➔ LABTEST_DISPLAY_NAME = VCF ArcherDx VP Complete Solid Tumor (unpaired)
    // PathoMarburg
    // then Archer VariantPlex Complete Solid Tumor
    // if GENERAL ➔ LABTEST_DISPLAY_NAME = VCF ArcherDx VP Complete Solid Tumor + FP Pan Solid Tumor
    // PathoMarburg v2
    // then Archer VariantPlex Complete Solid Tumor + FusionPlex Pan Solid Tumor v2
    String lastestDisplayName = mhGuideInfo.getGeneralInfo().getLastestDisplayName();
    if (Objects.equals(
        lastestDisplayName, "VCF ArcherDx VP Complete Solid Tumor (unpaired) PathoMarburg")) {
      // TODO! Need to checked the values present in panel and seqKitTyp
      panelEintrag.setWert(metadataConfigurationProperties.getNgsReports().getPanelVP());
      // seqKitType need to be checked
      seqKitTyp.setWert(metadataConfigurationProperties.getNgsReports().getSeqKitTypVP());
      seqPipeline.setWert(metadataConfigurationProperties.getNgsReports().getSeqPipelineVP());
    } else if (Objects.equals(
        lastestDisplayName,
        "VCF ArcherDx VP Complete Solid Tumor + FP Pan Solid Tumor PathoMarburg v2")) {
      panelEintrag.setWert(metadataConfigurationProperties.getNgsReports().getPanelVPCP());
      seqKitTyp.setWert(metadataConfigurationProperties.getNgsReports().getSeqKitTypVPCP());
      seqPipeline.setWert(metadataConfigurationProperties.getNgsReports().getSeqPipelineVPCP());
    }

    // TudokEintrag: Eintrag : Sequenziergeraet
    Eintrag sequenziergeraet = new Eintrag();
    sequenziergeraet.setFeldname("Sequenziergeraet");
    sequenziergeraet.setWert(metadataConfigurationProperties.getNgsReports().getSequencer());
    sequenziergeraet.setVersion("OS.MolDiag.Sequenziergerät.v1");
    sequenziergeraet.setKurztext("Illumina NovaSeq 6000");

    // TudokEintrag: Eintrag : Tumorzellgehalt
    Eintrag tumorzellgehalt = new Eintrag();
    tumorzellgehalt.setFeldname("Tumorzellgehalt");
    tudokEintrag.setEintraege(
        Arrays.asList(
            dokumentation,
            analyseID,
            analyseMethode,
            analyseMethoden,
            artDerSequenzierung,
            artinsituHybridisierung,
            befund,
            bemerkung,
            blocknummer,
            datum,
            durchfuehrendeOEFeld,
            einsendenummer,
            entnahmedatum,
            entnahmemethode,
            ergebnisMSI,
            genetischeVeraenderung,
            genexpressionstests,
            hRD,
            iCDO3Lokalisation,
            internExtern,
            eintragKBiomarker,
            eintragDel,
            eintragMolekulargenetischeUntersuchung,
            panelEintrag,
            probeID,
            probenmaterial,
            projekt,
            referenzGenom,
            seqKitHersteller,
            seqKitTyp,
            seqPipeline,
            sequenziergeraet,
            tumorzellgehalt));
    tudokEintrag.setRevision(1);
    tudokEintrag.setBearbeitungStatus(2);
    return tudokEintrag;
  }

  // Actual not in use
  public List<Variant> filterBiomarkers(List<Variant> variants) {
    List<String> classificationNameList =
        Arrays.asList(
            metadataConfigurationProperties.getNgsReports().getClassificationName().split(",\\s*"));
    List<String> oncogenicClassificationNameList =
        Arrays.asList(
            metadataConfigurationProperties
                .getNgsReports()
                .getOncogenicClassificationName()
                .split(",\\s*"));
    return variants.stream()
        .filter(
            variantLongList -> {
              String classificationName = variantLongList.getClassificationName();
              String oncogenicClassificationName = variantLongList.getOncogenicClassificationName();
              if (oncogenicClassificationName != null && !oncogenicClassificationName.isEmpty()) {

                return oncogenicClassificationNameList.contains(oncogenicClassificationName);
              }
              return classificationName != null
                  && !classificationName.isEmpty()
                  && classificationNameList.contains(classificationName);
            })
        .collect(Collectors.toList());
  }
}
