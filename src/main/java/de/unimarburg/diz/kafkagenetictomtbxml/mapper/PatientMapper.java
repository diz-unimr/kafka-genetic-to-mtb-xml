package de.unimarburg.diz.kafkagenetictomtbxml.mapper;

import de.unimarburg.diz.kafkagenetictomtbxml.model.MtbPatientInfo;
import de.unimarburg.diz.kafkagenetictomtbxml.model.mhGuide.MHGuide;
import de.unimarburg.diz.kafkagenetictomtbxml.model.onkostarXml.Patient;
import de.unimarburg.diz.kafkagenetictomtbxml.model.onkostarXml.Personendaten;
import org.springframework.stereotype.Component;

@Component
public class PatientMapper {

  public Patient createPatient(MHGuide mhGuideInfo, MtbPatientInfo mtbPatientInfo) {
    Patient patient = new Patient();
    // ExportID
    patient.setExportID(1);
    // Patient: PatientId
    patient.setPatientenId(mtbPatientInfo.getPatientenId());
    // Patient: PersonenDaten
    Personendaten personendaten = new Personendaten();
    // PersonenDaten: Address
    // Adresse adresse = new Adresse();
    // personendaten.setAktuelleAdresse(adresse);
    patient.setPersonendaten(personendaten);
    // Patient: PID gesperrt
    if (mtbPatientInfo.getPidGesperrt() == 0) {
      patient.setPidGesperrt("false");
    } else {
      patient.setPidGesperrt("true");
    }

    return patient;
  }
}
