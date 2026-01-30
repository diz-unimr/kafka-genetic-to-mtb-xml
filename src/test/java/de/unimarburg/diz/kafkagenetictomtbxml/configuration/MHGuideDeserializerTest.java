package de.unimarburg.diz.kafkagenetictomtbxml.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.unimarburg.diz.kafkagenetictomtbxml.UtilCreateDummyDataTest;
import de.unimarburg.diz.kafkagenetictomtbxml.model.mhGuide.MHGuide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPOutputStream;

import static org.assertj.core.api.Assertions.assertThat;

class MHGuideDeserializerTest {

    MHGuideDeserializer out;

    @BeforeEach
    void setup() {
        this.out = new MHGuideDeserializer();
    }

    @Test
    void shouldDecompressGzipContent() throws Exception {
        final var plainJson = new ObjectMapper().writeValueAsBytes(UtilCreateDummyDataTest.getDummyMHGuide());

        final var baos = new ByteArrayOutputStream();
        final var gzipJson = new GZIPOutputStream(baos);
        gzipJson.write(plainJson);
        gzipJson.close();

        var actual = out.deserialize("test", baos.toByteArray());
        assertThat(actual).isInstanceOf(MHGuide.class);
    }

    @Test
    void shouldDecompressPlainContent() throws Exception {
        final var plainJson = new ObjectMapper().writeValueAsBytes(UtilCreateDummyDataTest.getDummyMHGuide());

        var actual = out.deserialize("test", plainJson);
        assertThat(actual).isInstanceOf(MHGuide.class);
    }

}
