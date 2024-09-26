package de.unimarburg.diz.kafkagenetictomtbxml;

import com.fasterxml.jackson.core.JacksonException;
import de.unimarburg.diz.kafkagenetictomtbxml.mapper.OnkostarDataMapper;
import de.unimarburg.diz.kafkagenetictomtbxml.model.MtbPatientInfo;
import de.unimarburg.diz.kafkagenetictomtbxml.model.mhGuide.MHGuide;
import de.unimarburg.diz.kafkagenetictomtbxml.model.onkostarXml.*;
import org.apache.kafka.streams.kstream.KTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.function.BiConsumer;

@Service
public class GenDataBiConsumer {
    private static final Logger log = LoggerFactory.getLogger(GenDataBiConsumer.class);
    private final RestClientMtbSender restClientMtbSender;
    private final OnkostarDataMapper onkostarDataMapper;

    @Autowired
    public GenDataBiConsumer(RestClientMtbSender restClientMtbSender, OnkostarDataMapper onkostarDataMapper) {
        this.restClientMtbSender = restClientMtbSender;
        this.onkostarDataMapper = onkostarDataMapper;
    }

    // Functions for creating sub element in xml
    @Bean
    public BiConsumer<KTable<String, MHGuide>, KTable<String, MtbPatientInfo>> process() {
        return (mhGuideInfo,mtbPidInfo) -> mhGuideInfo.join(mtbPidInfo, (mhGuide, mtbPid) ->  {
            try {
                // Construct onkostarDataObject
                OnkostarDaten onkostarDaten = onkostarDataMapper.createOnkostarDaten(mhGuide, mtbPid);
                return restClientMtbSender.sendRequestToMtb(onkostarDaten);
            } catch (JacksonException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
