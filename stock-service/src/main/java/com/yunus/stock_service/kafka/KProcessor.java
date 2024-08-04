package com.yunus.stock_service.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yunus.stock_service.model.DailyData;
import com.yunus.stock_service.model.RequestBody;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class KProcessor {
    public static void stream(String bootstrapServers, String topic) throws InterruptedException {
        Properties streamsConfiguration = getKafkaProperties(bootstrapServers);

        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String> textLines = builder.stream(topic);

        textLines.mapValues(value -> {
                    try {
                        return process(value);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .peek((key, value) -> System.out.println("Value: " + value));

        Topology topology = builder.build();
        KafkaStreams streams = new KafkaStreams(topology, streamsConfiguration);
        streams.start();

        Thread.sleep(30000);
        streams.close();

    }

    private static Properties getKafkaProperties(String bootstrapServers) {
        Properties streamsConfiguration = new Properties();
        streamsConfiguration.put(
                StreamsConfig.APPLICATION_ID_CONFIG,
                "stock-info-processor");

        streamsConfiguration.put(
                StreamsConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapServers);
        streamsConfiguration.put(
                StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG,
                Serdes.String().getClass().getName());
        streamsConfiguration.put(
                StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG,
                Serdes.String().getClass().getName());
        return streamsConfiguration;
    }

    public static Double process(String body) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        RequestBody requestBody = objectMapper.readValue(body, RequestBody.class);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String yesterday = dtf.format(LocalDateTime.now().minusDays(3));

        DailyData dailyData = requestBody.getDailyData().get(yesterday);

        return calculateAverage(dailyData);
    }

    public static Double calculateAverage(DailyData dailyData) {
        return (dailyData.getHigh() + dailyData.getLow()) / 2;
    }
}
