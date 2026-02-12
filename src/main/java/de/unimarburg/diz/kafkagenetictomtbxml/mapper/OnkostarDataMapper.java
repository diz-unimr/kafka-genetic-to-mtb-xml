package de.unimarburg.diz.kafkagenetictomtbxml.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.unimarburg.diz.kafkagenetictomtbxml.model.MtbPatientInfo;
import de.unimarburg.diz.kafkagenetictomtbxml.model.mhGuide.MHGuide;
import de.unimarburg.diz.kafkagenetictomtbxml.model.onkostarXml.*;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OnkostarDataMapper {

  private final PatientMapper patientMapper;
  private final ErkrankungMapper erkrankungMapper;
  private final TudokMapper tudokMapper;

  @Autowired
  public OnkostarDataMapper(
      PatientMapper patientMapper, ErkrankungMapper erkrankungMapper, TudokMapper tudokMapper) {
    this.patientMapper = patientMapper;
    this.erkrankungMapper = erkrankungMapper;
    this.tudokMapper = tudokMapper;
  }

  public OnkostarDaten createOnkostarDaten(MHGuide mhGuideInfo, MtbPatientInfo mtbPatientInfo)
      throws JsonProcessingException {
    if (null == mtbPatientInfo || !mtbPatientInfo.isValid()) {
      throw new IllegalArgumentException("MtbPatientInfo invalid");
    }

    // Onkostar
    OnkostarDaten onkostarDaten = new OnkostarDaten();
    // Sende Organisation
    SendendeOrganisation sendendeOrganisation = new SendendeOrganisation();
    onkostarDaten.setSendendeOrganisation(sendendeOrganisation);
    // SendeDatum
    // TODO: Which Date?
    OffsetDateTime currentDateTime = OffsetDateTime.now();
    // Define the desired format
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
    // Format the current date and time
    var formattedDateTime = currentDateTime.format(formatter);
    onkostarDaten.setSendeDatum(formattedDateTime);
    // DokumentId
    onkostarDaten.setDokumentId(1);
    // DokumentVersion
    onkostarDaten.setDokumentVersion(1);
    // Inhalt
    Inhalt inhalt = new Inhalt();
    // Inhalt: PatientenDaten
    PatientenDaten patientenDaten = new PatientenDaten();
    // PatientenDaten: Patient
    Patient patient = patientMapper.createPatient(mhGuideInfo, mtbPatientInfo);
    // Patient: PatientenId
    patientenDaten.setPatient(patient);
    // PatientenDaten: Dokumentation
    Dokumentation dokumentation = new Dokumentation();
    // Dokumentation: Erkrankung
    Erkrankung erkrankung = erkrankungMapper.createErkrankung(mtbPatientInfo);
    dokumentation.setErkrankung(erkrankung);
    // Dokumentation: TudokEintrag
    TudokEintrag tudokEintrag = tudokMapper.createTudokEintrag(mhGuideInfo, mtbPatientInfo);
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
