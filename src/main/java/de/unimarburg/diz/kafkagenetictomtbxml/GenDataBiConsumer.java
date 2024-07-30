package de.unimarburg.diz.kafkagenetictomtbxml;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.unimarburg.diz.kafkagenetictomtbxml.model.onkostarXml.*;
import org.apache.kafka.streams.kstream.KTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.function.BiConsumer;

@Service
public class GenDataBiConsumer {
    private static final Logger log = LoggerFactory.getLogger(GenDataBiConsumer.class);
    private final RestClientMtbSender restClientMtbSender;
    private final ObjectMapper objectMapper;

    @Autowired
    public GenDataBiConsumer(RestClientMtbSender restClientMtbSender, ObjectMapper objectMapper) {
        this.restClientMtbSender = restClientMtbSender;
        this.objectMapper = objectMapper;
    }

    public OnkostarDaten ceateOnkostarDatenXml(String mhGuideInfo, String mtbPidInfo) throws JsonProcessingException {
        var jsonNodeMhGuide = objectMapper.readTree(mhGuideInfo);
        var jsonNodeMtbInfo = objectMapper.readTree(mtbPidInfo);
        // patient mtb
        var pid = jsonNodeMtbInfo.get("pid").asText();
        // tumorId
        var tumorId = jsonNodeMtbInfo.get("tumorid").asText();

        //MtbGeneticXml mtbGeneticXml = new MtbGeneticXml();
        // the values need to extracted from the joined result
        //mtbGeneticXml.setPid(pid);
        //mtbGeneticXml.setTumorId(tumorId);
        // Root Element
        // Omkostar
        OnkostarDaten onkostarDaten = new OnkostarDaten();
        // Sende Organisation
        SendendeOrganisation sendendeOrganisation = new SendendeOrganisation();
        onkostarDaten.setSendendeOrganisation(sendendeOrganisation);
        // SendeDatum
        onkostarDaten.setSendeDatum("2024-07-22T11:05:43.410+02:00");
        // DokumentId
        onkostarDaten.setDokumentId(1);
        // DokumentVersion
        onkostarDaten.setDokumentVersion(1);
        // Inhalt
        Inhalt inhalt = new Inhalt();
        // Inhalt: PatientenDaten
        PatientenDaten patientenDaten = new PatientenDaten();
        // PatientenDaten: Patient
        Patient patient = createPatient(mhGuideInfo,mtbPidInfo);
        // Patient: PatientenId
        patientenDaten.setPatient(patient);
        // PatientenDaten: Dokumentation
        Dokumentation dokumentation = new Dokumentation();
        // Dokumentation: Erkrankung
        Erkrankung erkrankung = createErankung(mhGuideInfo, mtbPidInfo);
        dokumentation.setErkrankung(erkrankung);
        // Dokumentation: TudokEintrag
        TudokEintrag tudokEintrag = createTudokEintrag(mhGuideInfo,mtbPidInfo);
        dokumentation.setTudokEintrag(tudokEintrag);
        patientenDaten.setDokumentation(dokumentation);
        inhalt.setPatientenDaten(patientenDaten);
        onkostarDaten.setInhalt(inhalt);
        return onkostarDaten;
    }

    public Patient createPatient(String mhGuideInfo, String mtbPidInfo){
        Patient patient = new Patient();
        patient.setPatientenId(1009);
        // Patient: PersonenDaten
        Personendaten personendaten = new Personendaten();
        // PersonenDaten: Address
        Adresse adresse = new Adresse();
        personendaten.setAktuelleAdresse(adresse);
        patient.setPersonendaten(personendaten);
        // Patient: LetzteInformation
        patient.setLetzteInformation("2023-09-27+02:00");
        // Patient: AngelegtAm
        patient.setAngelegtAm("2023-08-10T12:00:08.000+02:00");
        // Patient: setZuletztBearbeitetAm
        patient.setZuletztBearbeitetAm("2024-06-10T10:58:07.000+02:00");
        return patient;
    }


    public Erkrankung createErankung(String mhGuideInfo, String mtbPidInfo){
        Erkrankung erkrankung = new Erkrankung();
        erkrankung.setTumorId(1);
        erkrankung.setDiagnosedatum("2024-01-11T12:00:08.000+02:00");
        erkrankung.setRevision(19);
        return erkrankung;
    }
    // Functions for creating subelement in xml
    public TudokEintrag createTudokEintrag(String mhGuideInfo, String mtbPidInfo) {
        TudokEintrag tudokEintrag = new TudokEintrag();
        tudokEintrag.setExportID(1);
        tudokEintrag.setTumorId(1);
        tudokEintrag.setStartDatum("2024-07-21");
        tudokEintrag.setFormularName("OS.Molekulargenetik");
        tudokEintrag.setFormularVersion(1);
        tudokEintrag.setProzedurtyp("Beobachtung");
        DokumentierendeFachabteilung fachabteilung = new DokumentierendeFachabteilung();
        fachabteilung.setFachabteilungKennung("Studiensekretariat Hämatologie");
        fachabteilung.setEinrichtungKennung("CCCUMR");

        // TudokEintrag: Eintrag: Feldname = AnalyseID
        Eintrag analyseID  = new Eintrag();
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
        artDerSequenzierung.setWert("");
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
        datum.setWert("");
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
        einsendenummer.setWert("");

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


        // TudokEintrag: Eintrag: Feldname = MolekulargenetischeUntersuchung
        Eintrag eintragMolekulargenetischeUntersuchung = new Eintrag();
        eintragMolekulargenetischeUntersuchung.setFeldname("MolekulargenetischeUntersuchung");
        // Eintrag : Feldname = MolekulargenetischeUntersuchung
        // MolekulargenetischeUntersuchung: Formular
        // UnterformaularTyp: SimpleVariant (SV)
        UnterformularSV unterformularSV = createXmlUnterformularSV(mhGuideInfo,mtbPidInfo, tudokEintrag.getDokumentierendeFachabteilung());
        // UnterformaularTyp: CNV
        UnterformularCNV unterformularCNV = createXmlUnterformularCNV(mhGuideInfo,mtbPidInfo,tudokEintrag.getDokumentierendeFachabteilung());
        // RNAFusion
        UnterformularRNAFusion unterformularRNAFusion = createXmlUnterformularRANFusion(mhGuideInfo,mtbPidInfo,tudokEintrag.getDokumentierendeFachabteilung());
        // Add all formulars in the
        eintragMolekulargenetischeUntersuchung.setUnterformulars(Arrays.asList(unterformularSV, unterformularCNV, unterformularRNAFusion));
        tudokEintrag.setDokumentierendeFachabteilung(fachabteilung);
        tudokEintrag.setProzedurtyp("Beobachtung");
        tudokEintrag.setEintraege(Arrays.asList(analyseID, analyseMethode, analyseMethoden, artDerSequenzierung, artinsituHybridisierung, blocknummer, datum, doc,
                durchfuehrendeOE, einsendenummer, entnahmedatum, entnahmemethode, ergebnisMSI, internExtern, eintragMolekulargenetischeUntersuchung));
        tudokEintrag.setHauptTudokEintragExportID(2967997);
        tudokEintrag.setRevision(7);
        tudokEintrag.setBearbeitungStatus(2);
        return tudokEintrag;
    }


    public UnterformularSV createXmlUnterformularSV (String mhGuideInfo, String mtbPidInfo, DokumentierendeFachabteilung dokumentierendeFachabteilung) {
        UnterformularSV unterformularSV = new UnterformularSV();
        unterformularSV.setExportID(1);
        unterformularSV.setTumorId(1);
        unterformularSV.setDokumentierendeFachabteilung(dokumentierendeFachabteilung);
        unterformularSV.setStartDatum("2023-08-10");
        unterformularSV.setFormularName("OS.Molekulargenetische Untersuchung");
        unterformularSV.setFormularVersion(1);
        unterformularSV.setProzedurtyp("Beobachtung");
        // SV-Unterformular: Eintrag: Dokumentation
        Eintrag dokumentationUnterformular = new Eintrag();
        dokumentationUnterformular.setFeldname("Dokumentation");
        dokumentationUnterformular.setWert("ERW");
        dokumentationUnterformular.setFilterkategorie("{}");
        dokumentationUnterformular.setVersion("OS.MolDokumentation.v1");
        dokumentationUnterformular.setKurztext("Erweitert");

        // SV-Unterformular: Eintrag: Aktivierend
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
        Eintrag datumSV = new Eintrag();
        datumSV.setFeldname("Datum");
        datumSV.setWert("");

        // SV-Unterformular: Eintrag: "EVAltNucleotide
        Eintrag eVAltNucleotide  = new Eintrag();
        eVAltNucleotide.setFeldname("EVAltNucleotide");
        eVAltNucleotide.setWert("");

        // SV-Unterformular: Eintrag: EVCOSMICID
        Eintrag eVCOSMICID = new Eintrag();
        eVCOSMICID.setFeldname("EVCOSMICID");
        eVCOSMICID.setWert("");

        // SV-Unterformular: Eintrag: EVChromosom
        Eintrag eVChromosom = new Eintrag();
        eVChromosom.setFeldname("EVChromosom");
        eVChromosom.setWert("");
        eVChromosom.setFilterkategorie("{}");

        // SV-Unterformular: Eintrag: EVENSEMBLID
        Eintrag eVENSEMBLID = new Eintrag();
        eVENSEMBLID.setFeldname("EVENSEMBLID");
        eVENSEMBLID.setWert("");

        // SV-Unterformular: Eintrag: EVEnde
        Eintrag eVEnde = new Eintrag();
        eVEnde.setFeldname("EVEnde");
        eVEnde.setWert("");

        // SV-Unterformular: Eintrag: EVHGNCID
        Eintrag eVHGNCID = new Eintrag();
        eVHGNCID.setFeldname("EVHGNCID");
        eVHGNCID.setWert("");
        // SV-Unterformular: Eintrag: EVHGNCName
        Eintrag eVHGNCName = new Eintrag();
        eVHGNCName.setFeldname("EVHGNCName");

        // SV-Unterformular: Eintrag: EVHGNCSymbol
        Eintrag eVHGNCSymbol = new Eintrag();
        eVHGNCSymbol.setFeldname("EVHGNCSymbol");
        eVHGNCSymbol.setWert("");

        // SV-Unterformular: Eintrag: EVNMNummer
        Eintrag eVNMNummer = new Eintrag();
        eVNMNummer.setFeldname("EVNMNummer");

        // SV-Unterformular: Eintrag: EVReadDepth
        Eintrag eVReadDepth = new Eintrag();
        eVReadDepth.setFeldname("EVReadDepth");
        eVReadDepth.setWert("");

        // SV-Unterformular: Eintrag: EVRefNucleotide
        Eintrag eVRefNucleotide = new Eintrag();
        eVRefNucleotide.setFeldname("EVRefNucleotide");

        // SV-Unterformular: Eintrag: EVStart
        Eintrag eVStart = new Eintrag();
        eVStart.setFeldname("EVStart");

        // SV-Unterformular: Eintrag: Untersucht
        Eintrag untersucht = new Eintrag();
        untersucht.setFeldname("Untersucht");
        untersucht.setWert("");
        untersucht.setFilterkategorie("{}");
        untersucht.setKurztext("NF1");

        Eintrag ergebnisEintragSV = new Eintrag();
        ergebnisEintragSV.setFeldname("Ergebnis");
        ergebnisEintragSV.setWert("P");
        ergebnisEintragSV.setFilterkategorie("{&quot;OS.MolGenErgebnis.v1&quot;:&quot;Standard&quot;}");
        ergebnisEintragSV.setVersion("OS.MolGenErgebnis.v1");
        ergebnisEintragSV.setKurztext("Einfache Variante (Mutation / positiv)");

        unterformularSV.setEintraege(Arrays.asList(dokumentationUnterformular, aktivierend,
                allelfrequenz, allelzahl, analysemethode, bemerkung, datumSV, eVAltNucleotide,
                eVCOSMICID, eVChromosom, eVENSEMBLID, eVEnde, eVHGNCID, eVHGNCName, eVHGNCSymbol, eVNMNummer, eVReadDepth, eVRefNucleotide, eVStart, untersucht,ergebnisEintragSV));
        return unterformularSV;
    }


    public UnterformularCNV createXmlUnterformularCNV (String mhGuideInfo, String mtbPidInfo, DokumentierendeFachabteilung dokumentierendeFachabteilung) {
        // MolekulargenetischeUntersuchung: List: Unterformular
        // CNV
        UnterformularCNV unterformularCNV = new UnterformularCNV();
        unterformularCNV.setExportID(1);
        unterformularCNV.setTumorId(1);
        unterformularCNV.setDokumentierendeFachabteilung(dokumentierendeFachabteilung);
        unterformularCNV.setStartDatum("2023-08-10");
        unterformularCNV.setFormularName("OS.Molekulargenetische Untersuchung");
        unterformularCNV.setFormularVersion(1);
        unterformularCNV.setProzedurtyp("Beobachtung");

        // SV-Unterformular: Eintrag: Aktivierend
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
        Eintrag datumCNV = new Eintrag();
        datumCNV.setFeldname("Datum");
        datumCNV.setWert("");

        // SV-Unterformular: Eintrag: Dokumentation
        Eintrag dokumentationUnterformular = new Eintrag();
        dokumentationUnterformular.setFeldname("Dokumentation");
        dokumentationUnterformular.setWert("ERW");
        dokumentationUnterformular.setFilterkategorie("{}");
        dokumentationUnterformular.setVersion("OS.MolDokumentation.v1");
        dokumentationUnterformular.setKurztext("Erweitert");

        // Unterformular: Eintrag: Feldname = Ergebnis
        Eintrag ergebnisEintragCNV = new Eintrag();
        ergebnisEintragCNV.setFeldname("Ergebnis");
        ergebnisEintragCNV.setWert("CNV");
        ergebnisEintragCNV.setFilterkategorie("{&quot;OS.MolGenErgebnis.v1&quot;:&quot;Standard&quot;}");
        ergebnisEintragCNV.setVersion("OS.MolGenErgebnis.v1");
        ergebnisEintragCNV.setKurztext("Copy number variation (CNV)");

        // CNV-Unterformular: Eintrag: CNVBetroffeneGene
        Eintrag cNVBetroffeneGene = new Eintrag();
        cNVBetroffeneGene.setFeldname("BetroffeneGene");

        // CNV-Unterformular: Eintrag: CNVCNA
        Eintrag cNVCNA = new Eintrag();
        cNVCNA.setFeldname("CNVCNA");

        // CNV-Unterformular: Eintrag: CNVCNB
        Eintrag cNVCNB = new Eintrag();
        cNVCNB.setFeldname("CNVCNB");

        // CNV-Unterformular: Eintrag: Untersucht
        Eintrag untersucht = new Eintrag();
        untersucht.setFeldname("Untersucht");
        untersucht.setWert("MSH3");
        untersucht.setFilterkategorie("{}");
        untersucht.setVersion("OS.Molekulargenetik.v1");
        untersucht.setKurztext("MSH3");

        // CNV-Unterformular: Eintrag: CNVChromosom
        Eintrag cNVChromosom = new Eintrag();
        cNVChromosom.setFeldname("CNVChromosom");
        cNVChromosom.setWert("");
        cNVChromosom.setFilterkategorie("{}");

        // CNV-Unterformular: Eintrag: CNVENSEMBLID
        Eintrag cNVENSEMBLID = new Eintrag();
        cNVENSEMBLID.setFeldname("CNVENSEMBLID");

        // CNV-Unterformular: Eintrag: CNVEndRange
        Eintrag cNVEndRange = new Eintrag();
        cNVEndRange.setFeldname("CNVEndRange");

        // CNV-Unterformular: Eintrag: CNVHGNCID
        Eintrag cNVHGNCID = new Eintrag();
        cNVHGNCID.setFeldname("CNVHGNCID");

        // CNV-Unterformular: Eintrag: CNVHGNCName
        Eintrag cNVHGNCName = new Eintrag();
        cNVHGNCName.setFeldname("CNVHGNCName");

        // CNV-Unterformular: Eintrag: CNVHGNCSymbol
        Eintrag cNVHGNCSymbol = new Eintrag();
        cNVHGNCSymbol.setFeldname("CNVHGNCSymbol");

        // CNV-Unterformular: Eintrag: CNVNeutralLoH
        Eintrag cNVNeutralLoH = new Eintrag();
        cNVNeutralLoH.setFeldname("CNVNeutralLoH");

        // CNV-Unterformular: Eintrag: CNVRelativeCN
        Eintrag cNVRelativeCN = new Eintrag();
        cNVRelativeCN.setFeldname("CNVRelativeCN");

        // CNV-Unterformular: Eintrag: CNVReportedFocality
        Eintrag cNVReportedFocality = new Eintrag();
        cNVReportedFocality.setFeldname("CNVReportedFocality");

        // CNV-Unterformular: Eintrag: CNVStartRange
        Eintrag cNVStartRange = new Eintrag();
        cNVStartRange.setFeldname("CNVStartRange");

        // CNV-Unterformular: Eintrag: CNVTotalCN
        Eintrag cNVTotalCN = new Eintrag();
        cNVTotalCN.setFeldname("CNVTotalCN");

        // CNV-Unterformular: Eintrag: CNVTotalCNDouble
        Eintrag cNVTotalCNDouble = new Eintrag();
        cNVTotalCNDouble.setFeldname("CNVTotalCNDouble");

        // CNV-Unterformular: Eintrag: Codon
        Eintrag codon = new Eintrag();
        codon.setFeldname("Codon");

        // CNV-Unterformular: Eintrag: CopyNumberVariation
        Eintrag copyNumberVariation = new Eintrag();
        copyNumberVariation.setFeldname("CopyNumberVariation");

        unterformularCNV.setEintraege(Arrays.asList(dokumentationUnterformular, ergebnisEintragCNV,aktivierend,
                allelfrequenz, allelzahl, analysemethode, bemerkung, datumCNV, cNVBetroffeneGene,
                cNVCNB, cNVCNA, untersucht, cNVChromosom, cNVENSEMBLID, cNVEndRange, cNVHGNCID, cNVHGNCName,
                cNVHGNCSymbol, cNVNeutralLoH, cNVRelativeCN, cNVReportedFocality, cNVStartRange, cNVTotalCN, cNVTotalCNDouble, codon,
                copyNumberVariation));

        return unterformularCNV;
    }

    public UnterformularRNAFusion createXmlUnterformularRANFusion (String mhGuideInfo, String mtbPidInfo, DokumentierendeFachabteilung dokumentierendeFachabteilung){
        UnterformularRNAFusion unterformularRNAFusion = new UnterformularRNAFusion();
        unterformularRNAFusion.setExportID(1);
        unterformularRNAFusion.setTumorId(1);
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


    @Bean
    public BiConsumer<KTable<String, String>, KTable<String, String>> process() {
        return (mhGuideInfo,mtbPidInfo) -> mhGuideInfo.join(mtbPidInfo, (mhGuide, mtbPid) ->  {
            try {
                // Construct the xml file from the joined values
                // Send the xml file
                OnkostarDaten onkostarDaten = ceateOnkostarDatenXml(mhGuide, mtbPid);
                 //TudoEintragTest tudokEintrag= extractAndCreateXml2(mhGuide,mtbPid);
                //EintragT  eintragT = eintragTXml(mhGuide,mtbPid);
                return restClientMtbSender.sendRequestToMtb(onkostarDaten);
            } catch (JacksonException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
