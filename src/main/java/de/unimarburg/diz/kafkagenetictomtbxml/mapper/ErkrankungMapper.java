package de.unimarburg.diz.kafkagenetictomtbxml.mapper;

import de.unimarburg.diz.kafkagenetictomtbxml.model.MtbPatientInfo;
import de.unimarburg.diz.kafkagenetictomtbxml.model.onkostarXml.Erkrankung;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Component
public class ErkrankungMapper {

    public Erkrankung createErkrankung(final MtbPatientInfo mtbPatientInfo){
        if (mtbPatientInfo.getGuid() == null || mtbPatientInfo.getGuid().isBlank()) {
            throw new IllegalArgumentException("Invalid GUID in MtbPatientInfo");
        }

        try {
            UUID.fromString(mtbPatientInfo.getGuid());
        } catch(IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid GUID in MtbPatientInfo");
        }

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
