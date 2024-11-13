package com.yunus.stock_ingestion_service;

import com.yunus.stock_ingestion_service.client.AlertServiceClient;
import com.yunus.stock_ingestion_service.dto.AlertDto;
import com.yunus.stock_ingestion_service.kafka.KProcessor;
import com.yunus.stock_ingestion_service.kafka.KProducer;
import com.yunus.stock_ingestion_service.stock.FetchStock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Component
public class AppRunner implements ApplicationRunner {
    private final AlertServiceClient alertServiceClient;
    @Value("${alphavantage.api.key}")
    private String apiKey;
    @Value("${kafka.config.bootstrapServers}")
    private String bootstrapServers;
    @Value("${kafka.config.topic}")
    private String topic;


    public AppRunner(AlertServiceClient alertServiceClient) {
        this.alertServiceClient = alertServiceClient;
    }

    @Override
    public void run(ApplicationArguments args) throws URISyntaxException, ExecutionException, InterruptedException {
        checkAlert(alertServiceClient);
    }


    private void checkAlert(AlertServiceClient alertServiceClient) throws URISyntaxException, ExecutionException, InterruptedException {
        List<AlertDto> alerts = alertServiceClient.getAllAlerts();
        for (AlertDto alert : alerts) {
            String stockData = FetchStock.getStockData(alert.getStockName(), apiKey);

            if (alert.isActive()) streamAlert(stockData, alert.getMaxPrice(), alert.getMinPrice());
        }
    }

    private void streamAlert(String stockData, Double maxPrice, Double minPrice) throws InterruptedException {
        KProcessor.stream(bootstrapServers, topic, maxPrice, minPrice);
        KProducer.producer(stockData, bootstrapServers, topic);
    }
}

