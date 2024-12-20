package com.yunus.stock_ingestion_service;

import com.yunus.stock_ingestion_service.client.AlertServiceClient;
import com.yunus.stock_ingestion_service.dto.AlertDto;
import com.yunus.stock_ingestion_service.kafka.KProcessor;
import com.yunus.stock_ingestion_service.kafka.KProducer;
import com.yunus.stock_ingestion_service.kafka.KTopicCreator;
import com.yunus.stock_ingestion_service.stock.FetchStock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Component
public class AppRunner implements ApplicationRunner {
    private static final Logger log = LoggerFactory.getLogger(AppRunner.class);
    private final AlertServiceClient alertServiceClient;
    @Value("${kafka.config.bootstrapServers}")
    private String bootstrapServers;


    public AppRunner(AlertServiceClient alertServiceClient) {
        this.alertServiceClient = alertServiceClient;
    }

    @Override
    public void run(ApplicationArguments args) throws URISyntaxException, ExecutionException, InterruptedException {
        checkAlert(alertServiceClient);
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void scheduledJob() throws URISyntaxException, ExecutionException, InterruptedException {
        log.info("Scheduled Job Started");
        checkAlert(alertServiceClient);
    }

    private void checkAlert(AlertServiceClient alertServiceClient) throws URISyntaxException, ExecutionException, InterruptedException {
        List<AlertDto> alerts = alertServiceClient.getAllAlerts();
        if (!alerts.isEmpty()) {
            String apiKey = "";
            for (AlertDto alert : alerts) {

                String stockData = FetchStock.getStockData(alert.getStockName(), apiKey);

                if (alert.isActive())
                    streamAlert(alert.getStockName(), stockData);
            }
        }
    }

    private void streamAlert(String stockSymbol, String stockData) {
        String TOPIC_NAME_BEFORE_STREAM = "raw-stock-prices";
        String TOPIC_NAME_AFTER_STREAM = "processed-stock-prices";

        KTopicCreator.createTopic(bootstrapServers, TOPIC_NAME_BEFORE_STREAM);
        KTopicCreator.createTopic(bootstrapServers, TOPIC_NAME_AFTER_STREAM);
        KProcessor.stream(bootstrapServers, TOPIC_NAME_BEFORE_STREAM, TOPIC_NAME_AFTER_STREAM);
        KProducer.producer(stockSymbol, stockData, bootstrapServers, TOPIC_NAME_BEFORE_STREAM);
    }
}

