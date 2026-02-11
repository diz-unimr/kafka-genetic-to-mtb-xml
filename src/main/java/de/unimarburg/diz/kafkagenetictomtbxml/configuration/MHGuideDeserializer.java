package de.unimarburg.diz.kafkagenetictomtbxml.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.unimarburg.diz.kafkagenetictomtbxml.model.mhGuide.MHGuide;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

public class MHGuideDeserializer implements Deserializer<MHGuide> {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Logger logger = LoggerFactory.getLogger(MHGuideDeserializer.class);

    @Override
    public MHGuide deserialize(String topic, byte[] data) {
        try {
            if (data == null){
                logger.warn("Null received at deserializing");
                return null;
            }
            logger.info("Deserializing...");

            return objectMapper.readValue(new String(data, StandardCharsets.UTF_8), MHGuide.class);
        } catch (Exception e) {
            throw new SerializationException("Error when deserializing byte[] to MessageDto", e);
        }
    }
}
