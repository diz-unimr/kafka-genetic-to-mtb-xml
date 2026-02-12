package de.unimarburg.diz.kafkagenetictomtbxml.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.unimarburg.diz.kafkagenetictomtbxml.model.mhGuide.MHGuide;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

public class MHGuideSerializer implements Serializer<MHGuide> {
  private final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public byte[] serialize(String topic, MHGuide data) {
    try {
      if (data == null) {
        System.out.println("Null received at serializing");
        return null;
      }
      System.out.println("Serializing...");
      return objectMapper.writeValueAsBytes(data);
    } catch (Exception e) {
      throw new SerializationException("Error when serializing MessageDto to byte[]");
    }
  }
}
