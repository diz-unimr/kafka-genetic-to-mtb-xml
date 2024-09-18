package de.unimarburg.diz.kafkagenetictomtbxml.mapper;

import de.unimarburg.diz.kafkagenetictomtbxml.model.MtbPidInfo;
import de.unimarburg.diz.kafkagenetictomtbxml.model.mhGuide.MHGuide;
import de.unimarburg.diz.kafkagenetictomtbxml.model.onkostarXml.Erkrankung;
import org.springframework.stereotype.Component;


@Component
public class ErkrankungMapper {

    public Erkrankung createErkrankung(MHGuide mhGuideInfo, MtbPidInfo mtbPidInfo){
        Erkrankung erkrankung = new Erkrankung();
        erkrankung.setExportId(2);
        erkrankung.setTumorId(mtbPidInfo.getTumorId());
        erkrankung.setSid(mtbPidInfo.getSid());
        erkrankung.setGuid(mtbPidInfo.getGuid());
        erkrankung.setRevision(mtbPidInfo.getRevision());
        return erkrankung;
    }

}
