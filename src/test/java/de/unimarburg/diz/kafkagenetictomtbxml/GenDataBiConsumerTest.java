package de.unimarburg.diz.kafkagenetictomtbxml;

import de.unimarburg.diz.kafkagenetictomtbxml.configuration.MHGuideSerializer;
import de.unimarburg.diz.kafkagenetictomtbxml.configuration.MtbPatientInfoSerializer;
import de.unimarburg.diz.kafkagenetictomtbxml.mapper.OnkostarDataMapper;
import de.unimarburg.diz.kafkagenetictomtbxml.model.MtbPatientInfo;
import de.unimarburg.diz.kafkagenetictomtbxml.model.mhGuide.MHGuide;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.TestInputTopic;
import org.apache.kafka.streams.TopologyTestDriver;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.state.KeyValueStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.util.List;
import java.util.Properties;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class GenDataBiConsumerTest {

    static final String INPUT_TOPIC_PID = "input-topic1";
    static final String INPUT_TOPIC_MH = "input-topic2";

    RestClientMtbSender restClientMtbSender;
    OnkostarDataMapper onkostarDataMapper;

    BiConsumer<KStream<String, MtbPatientInfo>, KTable<String,MHGuide>> bean;

    @BeforeEach
    void setUp(
            @Mock RestClientMtbSender restClientMtbSender,
            @Mock OnkostarDataMapper onkostarDataMapper
    ) {
        this.restClientMtbSender = restClientMtbSender;
        this.onkostarDataMapper = onkostarDataMapper;

        this.bean = new GenDataBiConsumer().process(this.restClientMtbSender, this.onkostarDataMapper);
    }

    public static Stream<Arguments> joinTestData() {
        return Stream.of(
                Arguments.of("PID0001", List.of("PID1234"), 0), // Do not call
                Arguments.of("PID0001", List.of("PID0001", "PID1234"), 1),  // Call once for one equal key
                Arguments.of("PID0001", List.of("PID0001", "PID0001"), 2)  // Call twice for each equal key

        );
    }

    @ParameterizedTest
    @MethodSource("joinTestData")
    void shouldJoinPatientInfoStreamAndMhGuideTable(
            String mhInputKey,
            List<String> pidInputKeys,
            int expectedCreateOnkostarDataCallCount
    ) throws Exception {
        StreamsBuilder builder = new StreamsBuilder();

        final KStream<String, MtbPatientInfo> patientInput = builder.stream(
                INPUT_TOPIC_PID,
                Consumed.with(Serdes.String(), new JsonSerde<>(MtbPatientInfo.class))
        );
        final KTable<String, MHGuide> mhGuideInput = builder.table(
                INPUT_TOPIC_MH,
                Materialized.<String, MHGuide, KeyValueStore<Bytes, byte[]>>as("teststore")
                        .withKeySerde(Serdes.String())
                        .withValueSerde(new JsonSerde<>(MHGuide.class))
        );

        bean.accept(patientInput, mhGuideInput);

        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "test-app");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "test:9092");

        try (var driver = new TopologyTestDriver(builder.build(), props)) {

            TestInputTopic<String, MtbPatientInfo> pidInputTopic = driver.createInputTopic(
                    INPUT_TOPIC_PID,
                    new StringSerializer(),
                    new MtbPatientInfoSerializer()
            );
            TestInputTopic<String, MHGuide> mhInputTopic = driver.createInputTopic(
                    INPUT_TOPIC_MH,
                    new StringSerializer(),
                    new MHGuideSerializer()
            );

            // Send one entry to MHInput Table Topic
            mhInputTopic.pipeInput(mhInputKey, UtilCreateDummyDataTest.getDummyMHGuide());

            // Send entries to the PIDInput Stream Topic
            pidInputKeys.forEach(key ->
                    pidInputTopic.pipeInput(key, UtilCreateDummyDataTest.getDummyMtbPID())
            );

            verify(this.onkostarDataMapper, times(expectedCreateOnkostarDataCallCount)).createOnkostarDaten(any(), any());
        }

    }
}
