package de.unimarburg.diz.kafkagenetictomtbxml;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.function.BiConsumer;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ApplicationTests {

    @Autowired
    ApplicationContext applicationContext;

	@Test
	void contextHasProcessBiConsumerBean() {
        assertThat(applicationContext.getBean("process", BiConsumer.class)).isNotNull();
	}

}
