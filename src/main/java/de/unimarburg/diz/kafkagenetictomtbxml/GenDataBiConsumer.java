package de.unimarburg.diz.kafkagenetictomtbxml;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.unimarburg.diz.kafkagenetictomtbxml.configuration.KafkaConfiguration;
import de.unimarburg.diz.kafkagenetictomtbxml.mapper.OnkostarDataMapper;
import de.unimarburg.diz.kafkagenetictomtbxml.model.MtbPatientInfo;
import de.unimarburg.diz.kafkagenetictomtbxml.model.mhGuide.MHGuide;
import de.unimarburg.diz.kafkagenetictomtbxml.model.onkostarXml.*;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.kstream.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
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
    // Use of K-Table
    // Create Double Result
    public BiConsumer<KTable<String, MHGuide>, KTable<String, MtbPatientInfo>> process2() {
        return (mhGuideInfo,mtbPidInfo) -> mhGuideInfo.join(mtbPidInfo, (mhGuide, mtbPid) ->  {
            try {
                OnkostarDaten onkostarDaten = onkostarDataMapper.createOnkostarDaten(mhGuide, mtbPid);
                return restClientMtbSender.sendRequestToMtb(onkostarDaten);
            } catch (JacksonException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Bean
    public BiConsumer<KStream<String, MHGuide>, KStream<String,MtbPatientInfo>> process(Serde<MHGuide> mhGuideSerde, Serde<MtbPatientInfo> mtbPatientInfoSerde) {
        return (mhGuideInfo,mtbPidInfo) -> mhGuideInfo.join(mtbPidInfo, (mhGuide, mtbPid) -> {
            try {
                OnkostarDaten onkostarDaten = onkostarDataMapper.createOnkostarDaten(mhGuide, mtbPid);
                return restClientMtbSender.sendRequestToMtb(onkostarDaten);
            } catch (JacksonException e) {
                throw new RuntimeException(e);
            }
        }, JoinWindows.ofTimeDifferenceWithNoGrace(Duration.ofMinutes(5)),StreamJoined.with(Serdes.String(), mhGuideSerde, mtbPatientInfoSerde));
    }
}



