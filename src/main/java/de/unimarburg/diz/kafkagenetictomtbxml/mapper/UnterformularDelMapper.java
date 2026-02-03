package de.unimarburg.diz.kafkagenetictomtbxml.mapper;

import de.unimarburg.diz.kafkagenetictomtbxml.model.MtbPatientInfo;
import de.unimarburg.diz.kafkagenetictomtbxml.model.mhGuide.Variant;
import de.unimarburg.diz.kafkagenetictomtbxml.model.onkostarXml.DokumentierendeFachabteilung;
import de.unimarburg.diz.kafkagenetictomtbxml.model.onkostarXml.Eintrag;
import de.unimarburg.diz.kafkagenetictomtbxml.model.onkostarXml.UnterformularDel;
import de.unimarburg.diz.kafkagenetictomtbxml.util.CurrentDateFormatter;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class UnterformularDelMapper {

    public UnterformularDel createXmlUnterformularDel(MtbPatientInfo mtbPatientInfo, Variant variant, DokumentierendeFachabteilung dokumentierendeFachabteilung, int startExportIDUNterformular) {
        UnterformularDel unterformularDel = new UnterformularDel();

        unterformularDel.setExportID(startExportIDUNterformular);
        unterformularDel.setTumorId(mtbPatientInfo.getTumorId());
        unterformularDel.setErkrankungExportID(2);
        unterformularDel.setDokumentierendeFachabteilung(dokumentierendeFachabteilung);
        unterformularDel.setStartDatum(CurrentDateFormatter.formatCurrentDate());
        unterformularDel.setFormularName("OS.GenetischeVeraenderung");
        unterformularDel.setFormularVersion(1);
        unterformularDel.setProzedurtyp("Beobachtung");


        // Eintrag: Veranderung
        Eintrag veraenderung = new Eintrag();
        veraenderung.setFeldname("Veraenderung");
        veraenderung.setWert("del17p53");
        veraenderung.setVersion("OS.GenetischeVeränderungen.v1");
        veraenderung.setKurztext("del(17)(p53)");

        // Eintrag: SeqProzentwert
        Eintrag wert = new Eintrag();
        wert.setFeldname("Wert");
        wert.setWert("TODO");
        wert.setVersion("OS.GenetischeVeraenderungenWerte.v1");
        wert.setKurztext("Nicht bestimmt");


        // Add all the entrage in the array
        unterformularDel.setEintraege(Arrays.asList(veraenderung,wert));
        unterformularDel.setHauptTudokEintragExportID(3);
        unterformularDel.setRevision(1);
        unterformularDel.setBearbeitungStatus(2);
        return unterformularDel;
    }





}
