package de.unimarburg.diz.kafkagenetictomtbxml;

import com.fasterxml.jackson.core.JacksonException;
import de.unimarburg.diz.kafkagenetictomtbxml.mapper.OnkostarDataMapper;
import de.unimarburg.diz.kafkagenetictomtbxml.model.MtbPatientInfo;
import de.unimarburg.diz.kafkagenetictomtbxml.model.mhGuide.MHGuide;
import de.unimarburg.diz.kafkagenetictomtbxml.model.onkostarXml.OnkostarDaten;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

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

    @Bean
    public BiConsumer<KStream<String, MtbPatientInfo>, KTable<String,MHGuide>> process() {
        return (mtbPidInfo, mhGuideInfo) -> mtbPidInfo.join(mhGuideInfo, (mtbPid,mhGuide) -> {
            try {
                OnkostarDaten onkostarDaten = onkostarDataMapper.createOnkostarDaten(mhGuide, mtbPid);
                return restClientMtbSender.sendRequestToMtb(onkostarDaten);
            } catch (JacksonException e) {
                throw new RuntimeException(e);
            } catch (IllegalArgumentException e) {
                log.warn("Ignoring incoming message due to: {}", e.getMessage());
                return "Ignoring incoming message";
            }
        });
    }
}



