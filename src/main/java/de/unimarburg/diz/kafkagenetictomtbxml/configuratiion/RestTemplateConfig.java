package de.unimarburg.diz.kafkagenetictomtbxml.configuratiion;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
public class RestTemplateConfig {

    //@Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        // Create and configure MappingJackson2XmlHttpMessageConverter
        MappingJackson2XmlHttpMessageConverter xmlConverter = new MappingJackson2XmlHttpMessageConverter();
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
        xmlConverter.setObjectMapper(xmlMapper);

        //Create and configure MappingJackson2XmlHttpMessageConverter
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        jsonConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));

        // Set converters to RestTemplate
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        messageConverters.add(xmlConverter);
        messageConverters.add(jsonConverter);
        restTemplate.setMessageConverters(messageConverters);

        return restTemplate;
    }
}
