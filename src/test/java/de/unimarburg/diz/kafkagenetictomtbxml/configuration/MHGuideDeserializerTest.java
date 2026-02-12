package de.unimarburg.diz.kafkagenetictomtbxml.configuration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.unimarburg.diz.kafkagenetictomtbxml.UtilCreateDummyDataTest;
import de.unimarburg.diz.kafkagenetictomtbxml.model.mhGuide.MHGuide;
import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPOutputStream;
import org.apache.kafka.common.errors.SerializationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MHGuideDeserializerTest {

  MHGuideDeserializer out;

  @BeforeEach
  void setup() {
    this.out = new MHGuideDeserializer();
  }

  @Test
  void shouldNotDecompressGzipContent() throws Exception {
    final var plainJson =
        new ObjectMapper().writeValueAsBytes(UtilCreateDummyDataTest.getDummyMHGuide());

    final var baos = new ByteArrayOutputStream();
    final var gzipJson = new GZIPOutputStream(baos);
    gzipJson.write(plainJson);
    gzipJson.close();

    var exception =
        assertThrows(
            SerializationException.class,
            () -> {
              out.deserialize("test", baos.toByteArray());
            });

    assertThat(exception.getMessage()).isEqualTo("Error when deserializing byte[] to MessageDto");
  }

  @Test
  void shouldDecompressPlainContent() throws Exception {
    final var plainJson =
        new ObjectMapper().writeValueAsBytes(UtilCreateDummyDataTest.getDummyMHGuide());

    var actual = out.deserialize("test", plainJson);
    assertThat(actual).isInstanceOf(MHGuide.class);
  }
}
