package de.unimarburg.diz.kafkagenetictomtbxml.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.unimarburg.diz.kafkagenetictomtbxml.model.MtbPatientInfo;
import de.unimarburg.diz.kafkagenetictomtbxml.model.mhGuide.MHGuide;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class MHGuideDeserializer implements Deserializer<MHGuide> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public MHGuide deserialize(String topic, byte[] data) {
        try {
            if (data == null){
                System.out.println("Null received at deserializing");
                return null;
            }
            System.out.println("Deserializing...");
            return objectMapper.readValue(new String(data, "UTF-8"), MHGuide.class);
        } catch (Exception e) {
            throw new SerializationException("Error when deserializing byte[] to MessageDto");
        }
    }
}
