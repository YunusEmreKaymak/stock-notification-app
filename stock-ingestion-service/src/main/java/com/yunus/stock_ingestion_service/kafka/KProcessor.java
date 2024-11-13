package com.yunus.stock_ingestion_service.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yunus.stock_ingestion_service.model.DailyData;
import com.yunus.stock_ingestion_service.model.RequestBody;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.AbstractMap;
import java.util.Properties;

public class KProcessor {
    private static final int THREAD_SLEEP_TIME_FOR_KAFKA_STREAM = 30000;
    private static final int DIVISION_VALUE_TO_CALCULATE_AVERAGE = 2;
    private static final int LATEST_PAST_DAY_FREE_API_CAN_FETCH = 3;
    private static final String KAFKA_PROCESSOR_NAME = "stock-info-processor";
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd";

    public static void stream(String bootstrapServers, String topic, Double maxPrice, Double minPrice) throws InterruptedException {
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
                .peek((key, value) -> checkPriceRange(value.getValue(), maxPrice, minPrice, value.getKey()));


        Topology topology = builder.build();
        KafkaStreams streams = new KafkaStreams(topology, streamsConfiguration);
        streams.start();

        Thread.sleep(THREAD_SLEEP_TIME_FOR_KAFKA_STREAM);
        streams.close();

    }


    private static void checkPriceRange(Double price, Double maxPrice, Double minPrice, String stockSymbol) {
        if (price > maxPrice) {
            //NotificationHandler.sendNotification("Max", maxPrice, stockSymbol);
        } else if (price < minPrice) {
            //NotificationHandler.sendNotification("Min", minPrice, stockSymbol);
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
                Serdes.String().getClass().getName());
        streamsConfiguration.put(
                StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG,
                Serdes.String().getClass().getName());
        return streamsConfiguration;
    }

    private static AbstractMap.SimpleEntry<String, Double> process(String body) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        RequestBody requestBody = objectMapper.readValue(body, RequestBody.class);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
        String yesterday = dtf.format(LocalDateTime.now().minusDays(LATEST_PAST_DAY_FREE_API_CAN_FETCH));

        DailyData dailyData = requestBody.dailyData.get(yesterday);

        return new AbstractMap.SimpleEntry<>(requestBody.metaData.getSymbol(), calculateAverage(dailyData));


    }

    private static Double calculateAverage(DailyData dailyData) {
        return (dailyData.getHigh() + dailyData.getLow()) / DIVISION_VALUE_TO_CALCULATE_AVERAGE;
    }
}