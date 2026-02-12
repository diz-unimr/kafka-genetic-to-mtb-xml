package de.unimarburg.diz.kafkagenetictomtbxml.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {

  /**
   * Extracts Value from JSON String
   *
   * @param jsonString The JSON string to extract value from
   * @param keyToExtract The Key of the JSON value to be extracted
   * @param clazz The class of the JSON value
   * @param <T> The type of the value
   * @return The value
   * @throws JsonProcessingException if the value cannot be found
   */
  @SuppressWarnings("unchecked")
  public static <T> T extractFromJson(String jsonString, String keyToExtract, Class<T> clazz)
      throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    var jsonNode = objectMapper.readTree(jsonString).get(keyToExtract);

    if (String.class == clazz) {
      return (T) jsonNode.asText();
    } else if (Integer.class == clazz) {
      return (T) Integer.valueOf(jsonNode.asInt());
    } else if (Boolean.class == clazz) {
      return (T) Boolean.valueOf(jsonNode.asBoolean());
    }

    throw new IllegalArgumentException(
        String.format("Cannot get '%s' as '%s'", keyToExtract, clazz.getSimpleName()));
  }
}
