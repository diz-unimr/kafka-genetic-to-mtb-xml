package de.unimarburg.diz.kafkagenetictomtbxml;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiConsumer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.test.context.EmbeddedKafka;

@SpringBootTest
@EmbeddedKafka
class ApplicationTests {

  @Autowired ApplicationContext applicationContext;

  @Test
  void contextLoads() {}

  @Test
  void contextHasProcessBiConsumerBean() {
    assertThat(applicationContext.getBean("process", BiConsumer.class)).isNotNull();
  }
}
