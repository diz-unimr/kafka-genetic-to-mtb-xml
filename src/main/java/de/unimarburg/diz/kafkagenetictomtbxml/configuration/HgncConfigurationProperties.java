package de.unimarburg.diz.kafkagenetictomtbxml.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "hgnc")
@Data
public class HgncConfigurationProperties {

  private boolean enabled = false;
}
