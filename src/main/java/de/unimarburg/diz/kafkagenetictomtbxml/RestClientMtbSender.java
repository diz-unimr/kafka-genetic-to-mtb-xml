package de.unimarburg.diz.kafkagenetictomtbxml;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import de.unimarburg.diz.kafkagenetictomtbxml.model.onkostarXml.OnkostarDaten;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.web.client.RestTemplate;
import org.springframework.retry.RetryListener;
import java.util.HashMap;

@Component
public class RestClientMtbSender {

    private static final Logger log = LoggerFactory.getLogger(RestClientMtbSender.class);

    RestTemplate restTemplate = new RestTemplate();
    XmlMapper xmlMapper = new XmlMapper();
    private final String postUrl;
    private final String username;
    private final String password;

    private final RetryTemplate retryTemplate = defaultTemplate();

    public RestClientMtbSender(@Value("${services.mtbSender.post_url}") String postUrl, @Value("${services.mtbSender.mtb-username}") String username, @Value("${services.mtbSender.mtb-password}") String password ) {
        this.postUrl = postUrl;
        this.username = username;
        this.password = password;
    }

    public static RetryTemplate defaultTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(5000);
        backOffPolicy.setMultiplier(1.25);
        retryTemplate.setBackOffPolicy(backOffPolicy);
        HashMap<Class<? extends Throwable>, Boolean> retryableExceptions = new HashMap<>();
        retryableExceptions.put(RestClientException.class, true);
        RetryPolicy retryPolicy = new SimpleRetryPolicy(3, retryableExceptions);
        retryTemplate.setRetryPolicy(retryPolicy);
        retryTemplate.registerListener(new RetryListener() {
            @Override
            public <T, E extends Throwable> void onError(RetryContext context,
                                                         RetryCallback<T, E> callback, Throwable throwable) {
                log.warn("HTTP Error occurred: {}. Retrying {}", throwable.getMessage(),
                        context.getRetryCount());
            }
        });
        return retryTemplate;
    }

    public String sendRequestToMtb(OnkostarDaten onkostarDaten) throws JacksonException {
        xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
        String xmlOnkostarDaten = xmlMapper.writeValueAsString(onkostarDaten);
        log.info("Current mtb xml file");
        log.info(xmlOnkostarDaten);
        String authHeaderValue = "Basic " + java.util.Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set("Authorization", authHeaderValue);
        ByteArrayResource xmlResource = new ByteArrayResource(xmlOnkostarDaten.getBytes()) {
            @Override
            public String getFilename() {
                return "mtbXmlFile.xml";
            }
        };
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("document", xmlResource);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        try {
            var response = retryTemplate.execute(ctx -> restTemplate
                    .exchange(postUrl, HttpMethod.POST, requestEntity, String.class));
            if (response.getStatusCode().is2xxSuccessful()) {
                log.debug("API request succeeded");
                return response.getBody();
            }
        } catch (RestClientException e) {
            log.debug(e.getMessage());
        }
    return null;
    }
}
