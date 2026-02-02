package de.unimarburg.diz.kafkagenetictomtbxml.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.unimarburg.diz.kafkagenetictomtbxml.model.mhGuide.MHGuide;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

            return objectMapper.readValue(new String(decompressInputIfRequired(data), StandardCharsets.UTF_8), MHGuide.class);
        } catch (Exception e) {
            throw new SerializationException("Error when deserializing byte[] to MessageDto");
        }
    }

    private byte[] decompressInputIfRequired(final byte[] data) throws IOException {
        if (data.length < 3) {
            return data;
        }

        if (data[0] == 31 && data[1] == -117 && data[2] == 8) {
            logger.info("Decompressing...");
            var inputStream = new GZIPInputStream(new ByteArrayInputStream(data));
            return inputStream.readAllBytes();
        }

        return data;
    }
}
