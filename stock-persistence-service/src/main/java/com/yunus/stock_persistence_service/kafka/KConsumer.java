package com.yunus.stock_persistence_service.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

@Service
public class KConsumer {
    private static final Logger log = LoggerFactory.getLogger(KConsumer.class);
    @Value("${kafka.config.bootstrapServers}")
    private String BOOTSTRAP_SERVERS;
    private final String stringDeserializer = StringDeserializer.class.getName();
    private final RecordSavingService recordSavingService;

    public KConsumer(RecordSavingService recordSavingService) {
        this.recordSavingService = recordSavingService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        Thread consumerThread = new Thread(this::consumer);
        consumerThread.setDaemon(true);
        consumerThread.start();
    }


    public void consumer() {
        Properties properties = getKafkaProperties();
        String topicName = "processed-stock-prices";

        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties)) {
            consumer.subscribe(List.of(topicName));

            while (true) {
                log.info("Kafka consumer started from topic: {}", topicName);
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

                for (ConsumerRecord<String, String> record : records) {
                    recordSavingService.saveRecord(record);
                }
            }
        }
    }

    private Properties getKafkaProperties() {
        final String GROUP_ID_NAME = "stock-info-processor";
        final String AUTO_OFFSET_RESET_EARLIEST = "earliest";

        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, stringDeserializer);
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, stringDeserializer);
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID_NAME);
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, AUTO_OFFSET_RESET_EARLIEST);

        return properties;
    }

}
