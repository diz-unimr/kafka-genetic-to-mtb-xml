package de.unimarburg.diz.kafkagenetictomtbxml.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.unimarburg.diz.kafkagenetictomtbxml.model.MtbPidInfo;
import de.unimarburg.diz.kafkagenetictomtbxml.model.mhGuide.MHGuide;
import de.unimarburg.diz.kafkagenetictomtbxml.model.onkostarXml.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OnkostarDataMapper {

    private final PatientMapper patientMapper;
    private final ErkrankungMapper erkrankungMapper;
    private final TudokMapper tudokMapper;
    @Autowired
    public  OnkostarDataMapper(PatientMapper patientMapper, ErkrankungMapper erkrankungMapper, TudokMapper tudokMapper){
        this.patientMapper = patientMapper;
        this.erkrankungMapper = erkrankungMapper;
        this.tudokMapper = tudokMapper;
    }

    public OnkostarDaten ceateOnkostarDaten(MHGuide mhGuideInfo, MtbPidInfo mtbPidInfo) throws JsonProcessingException {
        // Onkostar
        OnkostarDaten onkostarDaten = new OnkostarDaten();
        // Sende Organisation
        SendendeOrganisation sendendeOrganisation = new SendendeOrganisation();
        onkostarDaten.setSendendeOrganisation(sendendeOrganisation);
        // SendeDatum
        // TODO: Which Date?
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
        Patient patient = patientMapper.createPatient(mhGuideInfo, mtbPidInfo);
        // Patient: PatientenId
        patientenDaten.setPatient(patient);
        // PatientenDaten: Dokumentation
        Dokumentation dokumentation = new Dokumentation();
        // Dokumentation: Erkrankung
        Erkrankung erkrankung = erkrankungMapper.createErkrankung(mhGuideInfo, mtbPidInfo);
        dokumentation.setErkrankung(erkrankung);
        // Dokumentation: TudokEintrag
        TudokEintrag tudokEintrag = tudokMapper.createTudokEintrag(mhGuideInfo, mtbPidInfo);
        dokumentation.setTudokEintrag(tudokEintrag);
        // add Dokumentation block
        patientenDaten.setDokumentation(dokumentation);
        // add Patientendaten block
        inhalt.setPatientenDaten(patientenDaten);
        // add the inhalt block
        onkostarDaten.setInhalt(inhalt);
        return onkostarDaten;
    }
}
