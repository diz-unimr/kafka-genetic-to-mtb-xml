package de.unimarburg.diz.kafkagenetictomtbxml.mapper;

import de.unimarburg.diz.kafkagenetictomtbxml.model.MtbPatientInfo;
import de.unimarburg.diz.kafkagenetictomtbxml.model.mhGuide.MHGuide;
import de.unimarburg.diz.kafkagenetictomtbxml.model.onkostarXml.Erkrankung;
import org.springframework.stereotype.Component;


@Component
public class ErkrankungMapper {

    public Erkrankung createErkrankung(MHGuide mhGuideInfo, MtbPatientInfo mtbPatientInfo){
        Erkrankung erkrankung = new Erkrankung();
        erkrankung.setExportId(2);
        erkrankung.setTumorId(mtbPatientInfo.getTumorId());
        erkrankung.setSid(mtbPatientInfo.getSid());
        erkrankung.setGuid(mtbPatientInfo.getGuid());
        erkrankung.setRevision(mtbPatientInfo.getRevision());
        erkrankung.setMigReferenzTumorId(mtbPatientInfo.getMigReferenzTumorId());
        return erkrankung;
    }

}
