package de.unimarburg.diz.kafkagenetictomtbxml;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withBadRequest;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import de.unimarburg.diz.kafkagenetictomtbxml.model.onkostarXml.OnkostarDaten;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

class RestClientMtbSenderTest {

  private MockRestServiceServer mockRestServiceServer;
  private RestClientMtbSender out;

  @BeforeEach
  void setUp() {
    var restTemplate = new RestTemplate();
    this.out = new RestClientMtbSender("http://localhost", restTemplate);
    this.mockRestServiceServer = MockRestServiceServer.createServer(restTemplate);
  }

  @Test
  void shouldSendXmlDocument() throws Exception {
    this.mockRestServiceServer
        .expect(requestTo("http://localhost"))
        .andExpect(content().contentTypeCompatibleWith(MediaType.MULTIPART_FORM_DATA))
        .andRespond(withSuccess("dummy-content", MediaType.TEXT_PLAIN));

    var actual = out.sendRequestToMtb(testOnkostarDaten());

    assertThat(actual).isEqualTo("dummy-content");
  }

  @Test
  void shouldHandleRequestErrorOnXmlDocumentSend() throws Exception {
    this.mockRestServiceServer
        .expect(ExpectedCount.times(3), requestTo("http://localhost"))
        .andExpect(content().contentTypeCompatibleWith(MediaType.MULTIPART_FORM_DATA))
        .andRespond(withBadRequest());

    var actual = out.sendRequestToMtb(testOnkostarDaten());

    assertThat(actual).isNull();
  }

  private OnkostarDaten testOnkostarDaten() {
    OnkostarDaten onkostarDaten = new OnkostarDaten();
    onkostarDaten.setTitel("Titel");
    onkostarDaten.setDokumentId(1);
    onkostarDaten.setDokumentVersion(1);
    onkostarDaten.setSendeDatum("2025-10-13");
    return onkostarDaten;
  }
}
