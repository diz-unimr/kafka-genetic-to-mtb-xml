package de.unimarburg.diz.kafkagenetictomtbxml.configuration;

import de.unimarburg.diz.kafkagenetictomtbxml.model.MtbPatientInfo;
import de.unimarburg.diz.kafkagenetictomtbxml.model.mhGuide.MHGuide;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.errors.StreamsUncaughtExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.StreamsBuilderFactoryBeanConfigurer;

@Configuration
@EnableKafka
public class KafkaConfiguration {
  private static final Logger log = LoggerFactory.getLogger(KafkaConfiguration.class);

  @Bean
  public StreamsBuilderFactoryBeanConfigurer streamsBuilderFactoryBeanCustomizer() {
    return factoryBean -> {
      factoryBean.setKafkaStreamsCustomizer(
          kafkaStreams ->
              kafkaStreams.setUncaughtExceptionHandler(
                  e -> {
                    log.error("Uncaught exception occurred.", e);
                    // default handler response
                    return StreamsUncaughtExceptionHandler.StreamThreadExceptionResponse
                        .SHUTDOWN_CLIENT;
                  }));
    };
  }

  @Bean
  public Serde<MHGuide> mhGuideSerde() {
    return Serdes.serdeFrom(new MHGuideSerializer(), new MHGuideDeserializer());
  }

  @Bean
  public Serde<MtbPatientInfo> mtbPatientInfoSerde() {
    return Serdes.serdeFrom(new MtbPatientInfoSerializer(), new MtbPatientInfoDeserializer());
  }
}
