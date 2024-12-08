package com.yunus.stock_ingestion_service.kafka;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Properties;
import java.util.Set;

public class KTopicCreator {
    private static final Logger log = LoggerFactory.getLogger(KTopicCreator.class);

    private static final Integer PARTITIONS = 3;
    private static final Short REPLICATION_FACTOR = 1;

    public static void createTopic(String bootstrapServers, String topicName) {
        Properties prop = getKafkaProperties(bootstrapServers);


        try (AdminClient adminClient = AdminClient.create(prop)) {
            Set<String> topicNames = adminClient.listTopics().names().get();

            if (topicNames.contains(topicName)) {
                log.warn("Topic {} already exists.", topicName);
            } else {
                NewTopic processedStockPricesTopic = new NewTopic(topicName, PARTITIONS, REPLICATION_FACTOR);

                adminClient.createTopics(Collections.singletonList(processedStockPricesTopic));
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private static Properties getKafkaProperties(String bootstrapServers) {
        Properties properties = new Properties();
        properties.setProperty(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        return properties;
    }
}