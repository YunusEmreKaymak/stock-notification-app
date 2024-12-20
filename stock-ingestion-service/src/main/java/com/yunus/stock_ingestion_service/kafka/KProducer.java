package com.yunus.stock_ingestion_service.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class KProducer {

    public static void producer(String stockSymbol, String data, String bootstrapServers, String topic_name_before_stream) {
        Properties properties = getKafkaProperties(bootstrapServers);

        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topic_name_before_stream, stockSymbol, data);

        producer.send(producerRecord);

        producer.flush();

        producer.close();
    }

    private static Properties getKafkaProperties(String bootstrapServers) {
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        return properties;
    }
}
