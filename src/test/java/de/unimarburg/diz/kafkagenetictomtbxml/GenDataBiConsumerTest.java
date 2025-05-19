package de.unimarburg.diz.kafkagenetictomtbxml;

import de.unimarburg.diz.kafkagenetictomtbxml.configuration.KafkaConfiguration;
import de.unimarburg.diz.kafkagenetictomtbxml.configuration.MHGuideSerializer;
import de.unimarburg.diz.kafkagenetictomtbxml.configuration.MtbPatientInfoSerializer;
import de.unimarburg.diz.kafkagenetictomtbxml.model.MtbPatientInfo;
import de.unimarburg.diz.kafkagenetictomtbxml.model.mhGuide.MHGuide;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.kstream.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.time.Duration;
import java.util.Properties;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.shouldHaveThrown;

@SpringBootTest
public class GenDataBiConsumerTest {
    @Autowired GenDataBiConsumer genDataBiConsumer;
    @Autowired
    Serde<MHGuide> mhGuideSerde;
    @Autowired
    Serde<MtbPatientInfo> mtbPatientInfoSerde;

    //@Test
    public void test() {
        String INPUT_TOPIC_MH = "input_mhguide";
        String INPUT_TOPIC_PID  = "input_mtbPidInfo";

        StreamsBuilder builder = new StreamsBuilder();

       // final KTable<String, MHGuide> inputStreamMH =
         //       builder.table("input_mhguide", Consumed.with(Serdes.String(), new JsonSerde<>(MHGuide.class)));
        //final KTable<String, MtbPatientInfo> inputStreamPID =
         //       builder.table("input_mtbPidInfo", Consumed.with(Serdes.String(), new JsonSerde<>(MtbPatientInfo.class)));

        final KStream<String, MHGuide> inputStreamMH =
                builder.stream("input_mhguide", Consumed.with(Serdes.String(), mhGuideSerde));
        final KStream<String, MtbPatientInfo> inputStreamPID =
                builder.stream("input_mtbPidInfo", Consumed.with(Serdes.String(), mtbPatientInfoSerde));

        //genDataBiConsumer.process(mhGuideSerde,mtbPatientInfoSerde).accept(inputStreamMH, inputStreamPID);
        genDataBiConsumer.process();
        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "test");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "dummy:1234");

        try (TopologyTestDriver testDriver = new TopologyTestDriver(builder.build(), config)){

            TestInputTopic<String, MHGuide> inputTopic1 =
                    testDriver.createInputTopic(
                            INPUT_TOPIC_MH, new StringSerializer(), new MHGuideSerializer());
            TestInputTopic<String, MtbPatientInfo> inputTopic2 =
                    testDriver.createInputTopic(
                            INPUT_TOPIC_PID, new StringSerializer(), new MtbPatientInfoSerializer());

            inputTopic1.pipeInput("test", UtilCreateDummyDataTest.getDummyMHGuide());
            inputTopic2.pipeInput("test",UtilCreateDummyDataTest.getDummyMtbPID());

        }

    }
}
