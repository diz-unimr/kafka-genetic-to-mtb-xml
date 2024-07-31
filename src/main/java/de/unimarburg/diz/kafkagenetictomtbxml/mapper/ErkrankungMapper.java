package de.unimarburg.diz.kafkagenetictomtbxml.mapper;

import de.unimarburg.diz.kafkagenetictomtbxml.model.MtbPidInfo;
import de.unimarburg.diz.kafkagenetictomtbxml.model.mhGuide.MHGuide;
import de.unimarburg.diz.kafkagenetictomtbxml.model.onkostarXml.Erkrankung;
import org.springframework.stereotype.Component;


@Component
public class ErkrankungMapper {

    public Erkrankung createErkrankung(MHGuide mhGuideInfo, MtbPidInfo mtbPidInfo){
        Erkrankung erkrankung = new Erkrankung();
        erkrankung.setTumorId(mtbPidInfo.getTumorId());
        // TODO: Which Date?
        erkrankung.setDiagnosedatum("2024-01-11T12:00:08.000+02:00");
        // TODO: Which Version?
        erkrankung.setRevision(19);
        return erkrankung;
    }

}
