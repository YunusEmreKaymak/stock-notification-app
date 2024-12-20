package com.yunus.stock_ingestion_service.kafka;

import com.yunus.stock_ingestion_service.model.DailyData;
import com.yunus.stock_ingestion_service.model.RequestBody;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.AbstractMap;
import java.util.Properties;

public class KProcessor {
    private static final Logger log = LoggerFactory.getLogger(KProcessor.class);
    private static final int THREAD_SLEEP_TIME_FOR_KAFKA_STREAM = 30000;
    private static final String KAFKA_PROCESSOR_NAME = "stock-info-processor";
    private static final String STRING_SERDE = Serdes.StringSerde.class.getName();
    private static final String CUSTOM_SERDE = CustomSerde.class.getName();

    public static void stream(String bootstrapServers, String topic_name_before_stream, String topic_name_after_stream) {
        Properties streamsConfiguration = getKafkaProperties(bootstrapServers);

        try (KafkaStreams streams = createKafkaStreams(topic_name_before_stream, topic_name_after_stream, streamsConfiguration)) {
            streams.start();
            log.info("Kafka Streams Started");
//        Thread.sleep(THREAD_SLEEP_TIME_FOR_KAFKA_STREAM);
//        streams.close();
        }
    }

    private static Properties getKafkaProperties(String bootstrapServers) {
        Properties streamsConfiguration = new Properties();
        streamsConfiguration.put(
                StreamsConfig.APPLICATION_ID_CONFIG,
                KAFKA_PROCESSOR_NAME);
        streamsConfiguration.put(
                StreamsConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapServers);
        streamsConfiguration.put(
                StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG,
                STRING_SERDE);
        streamsConfiguration.put(
                StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG,
                CUSTOM_SERDE);

        return streamsConfiguration;
    }

    private static KafkaStreams createKafkaStreams(String topic_name_before_stream, String topic_name_after_stream, Properties streamsConfiguration) {
        StreamsBuilder builder = new StreamsBuilder();

        KStream<String, RequestBody> rawStream = builder.stream(topic_name_before_stream, Consumed.with(Serdes.String(), new CustomSerde<>(RequestBody.class)));

        KStream<String, AbstractMap.SimpleEntry<String, DailyData>> processedStream = rawStream.mapValues(KProcessor::process);

        processedStream.to(topic_name_after_stream);

        Topology topology = builder.build();
        return new KafkaStreams(topology, streamsConfiguration);
    }


    private static AbstractMap.SimpleEntry<String, DailyData> process(RequestBody requestBody) {

        if (requestBody != null) {
            return new AbstractMap.SimpleEntry<>(requestBody.metaData.getLastRefreshed(), requestBody.dailyData.get(requestBody.metaData.getLastRefreshed()));
        }

        return null;
    }
}