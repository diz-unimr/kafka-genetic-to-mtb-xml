package de.unimarburg.diz.kafkagenetictomtbxml.mapper;

import de.unimarburg.diz.kafkagenetictomtbxml.model.MtbPidInfo;
import de.unimarburg.diz.kafkagenetictomtbxml.model.mhGuide.MHGuide;
import de.unimarburg.diz.kafkagenetictomtbxml.model.onkostarXml.Adresse;
import de.unimarburg.diz.kafkagenetictomtbxml.model.onkostarXml.Patient;
import de.unimarburg.diz.kafkagenetictomtbxml.model.onkostarXml.Personendaten;
import org.springframework.stereotype.Component;

@Component
public class PatientMapper {

    public Patient createPatient(MHGuide mhGuideInfo, MtbPidInfo mtbPidInfo){
        Patient patient = new Patient();
        // Patient: PatientId
        patient.setPatientenId(mtbPidInfo.getPid());
        // Patient: PersonenDaten
        Personendaten personendaten = new Personendaten();
        // PersonenDaten: Address
        Adresse adresse = new Adresse();
        personendaten.setAktuelleAdresse(adresse);
        patient.setPersonendaten(personendaten);
        // Patient: LetzteInformation
        // TODO: Which date?
        patient.setLetzteInformation("2023-09-27+02:00");
        // Patient: AngelegtAm
        // TODO: Which date?
        patient.setAngelegtAm("2023-08-10T12:00:08.000+02:00");
        // Patient: setZuletztBearbeitetAm
        // TODO: Which date?
        patient.setZuletztBearbeitetAm("2024-06-10T10:58:07.000+02:00");
        return patient;
    }
}
