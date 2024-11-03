package com.yunus.stock_service;

import com.yunus.stock_service.client.AlertServiceClient;
import com.yunus.stock_service.dto.AlertDto;
import com.yunus.stock_service.kafka.KProcessor;
import com.yunus.stock_service.kafka.KProducer;
import com.yunus.stock_service.stock.FetchStock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Component
public class AppRunner implements ApplicationRunner {
    @Value("${alphavantage.api.key}")
    private static String apiKey;

    @Value("${kafka.config.bootstrapServers}")
    private static String bootstrapServers;

    @Value("${kafka.config.topic}")
    private static String topic;

    private final AlertServiceClient alertServiceClient;


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
            if (alert.isActive()) {
                streamAlert(stockData);
            }
        }
    }

    private void streamAlert(String stockData) throws InterruptedException {
        KProcessor.stream(bootstrapServers, topic);
        KProducer.producer(stockData, bootstrapServers, topic);
    }
}
