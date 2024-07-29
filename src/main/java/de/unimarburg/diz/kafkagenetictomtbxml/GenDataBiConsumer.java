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
        Patient patient = new Patient();
        // Patient: PatientenId
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
        patientenDaten.setPatient(patient);

        // PatientenDaten: Dokumentation
        Dokumentation dokumentation = new Dokumentation();
        // Dokumentation: Erkrankung
        Erkrankung erkrankung = new Erkrankung();
        erkrankung.setTumorId(1);
        erkrankung.setDiagnosedatum("2024-01-11T12:00:08.000+02:00");
        erkrankung.setRevision(19);
        dokumentation.setErkrankung(erkrankung);
        // Dokumentation: TudokEintrag
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
        analyseMethoden.setWert("");
        analyseMethoden.setFilterkategorie("{}");
        analyseMethoden.setVersion("OS.MolArtderHybridisierung.v1");

        // TudokEintrag: Eintrag: Feldname = ArtDerSequenzierung
        Eintrag artDerSequenzierung = new Eintrag();
        artDerSequenzierung.setFeldname("ArtDerSequenzierung");
        artDerSequenzierung.setWert("");
        artDerSequenzierung.setFilterkategorie("{}");
        artDerSequenzierung.setVersion("OS.MolArtderHybridisierung.v1");
        artDerSequenzierung.setKurztext("");

        // TudokEintrag: Eintrag: Feldname = Blocknummer
        Eintrag Blocknummer = new Eintrag();
        Blocknummer.setFeldname("Blocknummer");
        Blocknummer.setWert("");


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
        // CNV
        UnterformularCNV unterformularCNV = createXmlUnterformularCNV(mhGuideInfo,mtbPidInfo,tudokEintrag.getDokumentierendeFachabteilung());
        // ToDo other Eintrag
        // Add all formulars in the
        eintragMolekulargenetischeUntersuchung.setUnterformulars(Arrays.asList(unterformularSV, unterformularCNV));
        tudokEintrag.setDokumentierendeFachabteilung(fachabteilung);
        tudokEintrag.setProzedurtyp("Beobachtung");
        tudokEintrag.setEintraege(Arrays.asList(entnahmedatum, entnahmemethode, doc, eintragMolekulargenetischeUntersuchung));
        tudokEintrag.setHauptTudokEintragExportID(2967997);
        tudokEintrag.setRevision(7);
        tudokEintrag.setBearbeitungStatus(2);
        dokumentation.setTudokEintrag(tudokEintrag);
        patientenDaten.setDokumentation(dokumentation);
        inhalt.setPatientenDaten(patientenDaten);
        onkostarDaten.setInhalt(inhalt);
        return onkostarDaten;
    }
    // ToDo
    // Functions for creating subelement in xml

    //ToDo
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
        dokumentationUnterformular.setWert("");
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
        datumSV.setFeldname("DatumSV");
        datumSV.setWert("");

        // SV-Unterformular: Eintrag: "EVAltNucleotide
        Eintrag eVAltNucleotide  = new Eintrag();
        eVAltNucleotide.setFeldname("eVAltNucleotide");
        eVAltNucleotide.setWert("");

        // SV-Unterformular: Eintrag: EVCOSMICID
        Eintrag eVCOSMICID = new Eintrag();
        eVCOSMICID.setFeldname("eVCOSMICID");
        eVCOSMICID.setWert("");

        // SV-Unterformular: Eintrag: EVChromosom
        Eintrag eVChromosom = new Eintrag();
        eVChromosom.setFeldname("eVChromosom");
        eVChromosom.setWert("");
        eVChromosom.setFilterkategorie("{}");

        // SV-Unterformular: Eintrag: EVENSEMBLID
        Eintrag eVENSEMBLID = new Eintrag();
        eVENSEMBLID.setFeldname("eVENSEMBLID");
        eVENSEMBLID.setWert("");

        // SV-Unterformular: Eintrag: EVEnde
        Eintrag eVEnde = new Eintrag();
        eVEnde.setFeldname("EVEnde");
        eVEnde.setWert("");

        // SV-Unterformular: Eintrag: EVHGNCID
        Eintrag eVHGNCID = new Eintrag();
        eVHGNCID.setFeldname("eVHGNCID");
        eVHGNCID.setWert("");
        // SV-Unterformular: Eintrag: EVHGNCName
        Eintrag eVHGNCName = new Eintrag();
        eVHGNCName.setFeldname("EVHGNCName");

        // SV-Unterformular: Eintrag: EVHGNCSymbol
        Eintrag eVHGNCSymbol = new Eintrag();
        eVHGNCSymbol.setFeldname("eVHGNCSymbol");
        eVHGNCSymbol.setWert("");

        // SV-Unterformular: Eintrag: EVNMNummer
        Eintrag eVNMNummer = new Eintrag();
        eVNMNummer.setFeldname("EVNMNummer");

        // SV-Unterformular: Eintrag: EVReadDepth
        Eintrag eVReadDepth = new Eintrag();
        eVReadDepth.setFeldname("eVReadDepth");
        eVReadDepth.setWert("");

        // SV-Unterformular: Eintrag: EVRefNucleotide
        Eintrag eVRefNucleotide = new Eintrag();
        eVRefNucleotide.setFeldname("eVRefNucleotide");

        // SV-Unterformular: Eintrag: EVStart
        Eintrag eVStart = new Eintrag();
        eVStart.setFeldname("EVStart");

        // SV-Unterformular: Eintrag: Untersucht
        Eintrag untersucht = new Eintrag();
        untersucht.setFeldname("untersucht");
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

    // ToDo
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
        //Unterformular: Eintrag: Aktivierend

        // SV-Unterformular: Eintrag: Dokumentation
        Eintrag dokumentationUnterformular = new Eintrag();
        dokumentationUnterformular.setFeldname("Dokumentation");
        dokumentationUnterformular.setWert("");
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
        unterformularCNV.setEintraege(Arrays.asList(dokumentationUnterformular, ergebnisEintragCNV));

        //CNVChromosom
        Eintrag cNVChromosom = new Eintrag();
        cNVChromosom.setFeldname("CNVChromosom");
        cNVChromosom.setWert("chr16");
        cNVChromosom.setFilterkategorie("{}");
        return unterformularCNV;
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
