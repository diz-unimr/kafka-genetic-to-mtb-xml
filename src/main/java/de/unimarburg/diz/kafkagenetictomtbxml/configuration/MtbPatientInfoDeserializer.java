package de.unimarburg.diz.kafkagenetictomtbxml.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.unimarburg.diz.kafkagenetictomtbxml.model.MtbPatientInfo;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class MtbPatientInfoDeserializer implements Deserializer<MtbPatientInfo> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public MtbPatientInfo deserialize(String topic, byte[] data) {
        try {
            if (data == null){
                System.out.println("Null received at deserializing");
                return null;
            }
            System.out.println("Deserializing...");
            return objectMapper.readValue(new String(data, "UTF-8"), MtbPatientInfo.class);
        } catch (Exception e) {
            throw new SerializationException("Error when deserializing byte[] to MessageDto");
        }
    }

}
