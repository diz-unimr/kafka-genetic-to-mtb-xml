package de.unimarburg.diz.kafkagenetictomtbxml;

import com.fasterxml.jackson.core.JacksonException;
import de.unimarburg.diz.kafkagenetictomtbxml.mapper.OnkostarDataMapper;
import de.unimarburg.diz.kafkagenetictomtbxml.model.MtbPatientInfo;
import de.unimarburg.diz.kafkagenetictomtbxml.model.mhGuide.MHGuide;
import de.unimarburg.diz.kafkagenetictomtbxml.model.onkostarXml.OnkostarDaten;
import java.util.function.BiConsumer;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GenDataBiConsumer {
  private static final Logger log = LoggerFactory.getLogger(GenDataBiConsumer.class);

  @Bean
  public BiConsumer<KStream<String, MtbPatientInfo>, KTable<String, MHGuide>> process(
      final RestClientMtbSender restClientMtbSender, final OnkostarDataMapper onkostarDataMapper) {
    return (mtbPidInfo, mhGuideInfo) ->
        mtbPidInfo.join(
            mhGuideInfo,
            (mtbPid, mhGuide) -> {
              try {
                OnkostarDaten onkostarDaten =
                    onkostarDataMapper.createOnkostarDaten(mhGuide, mtbPid);
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
