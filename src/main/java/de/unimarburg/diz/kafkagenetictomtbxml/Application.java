package de.unimarburg.diz.kafkagenetictomtbxml;

import de.unimarburg.diz.kafkagenetictomtbxml.configuration.HgncConfigurationProperties;
import de.unimarburg.diz.kafkagenetictomtbxml.configuration.MetadataConfigurationProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties({HgncConfigurationProperties.class, MetadataConfigurationProperties.class})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

    }

    @Bean
    public RestTemplate restTemplate(
            @Value("${services.mtbSender.mtb-username}") String username,
            @Value("${services.mtbSender.mtb-password}") String password
    ) {
        return new RestTemplateBuilder()
                .basicAuthentication(username, password)
                .build();
    }

}
