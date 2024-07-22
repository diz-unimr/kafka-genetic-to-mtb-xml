package de.unimarburg.diz.kafkagenetictomtbxml;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import de.unimarburg.diz.kafkagenetictomtbxml.model.*;
import org.apache.kafka.streams.kstream.KTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

@Service
public class GenDataBiConsumer {
    private static final Logger log = LoggerFactory.getLogger(GenDataBiConsumer.class);
    private final RestClientMtbSender restClientMtbSender;
    private final ObjectMapper objectMapper;

    @Autowired
    public GenDataBiConsumer(RestClientMtbSender restClientMtbSender, ObjectMapper objectMapper) {
        this.restClientMtbSender = restClientMtbSender;
        this.objectMapper = objectMapper;
    }

    public OnkostarDaten extractAndCreateXml(String mhGuideInfo, String mtbPidInfo) throws JsonProcessingException {
        var jsonNodeMhGuide = objectMapper.readTree(mhGuideInfo);
        var jsonNodeMtbInfo = objectMapper.readTree(mtbPidInfo);
        // patient mtb
        var pid = jsonNodeMtbInfo.get("pid").asText();
        // tumorId
        var tumorId = jsonNodeMtbInfo.get("tumorid").asText();

        //MtbGeneticXml mtbGeneticXml = new MtbGeneticXml();
        // the values need to extracted from the joined result
        //mtbGeneticXml.setPid(pid);
        //mtbGeneticXml.setTumorId(tumorId);

        OnkostarDaten onkostarDaten = new OnkostarDaten();
        onkostarDaten.setTitel("Test");
        return onkostarDaten;
    }

    @Bean
    public BiConsumer<KTable<String, String>, KTable<String, String>> process() {
        return (mhGuideInfo,mtbPidInfo) -> mhGuideInfo.join(mtbPidInfo, (mhGuide, mtbPid) ->  {
            try {
                // Construct the xml file from the joined values
                // Send the xml file
                OnkostarDaten onkostarDaten = extractAndCreateXml(mhGuide, mtbPid);
                return restClientMtbSender.sendRequestToMtb(onkostarDaten);
            } catch (JacksonException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
