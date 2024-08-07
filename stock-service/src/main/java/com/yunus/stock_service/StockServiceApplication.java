package com.yunus.stock_service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

@SpringBootApplication
@EnableFeignClients
public class StockServiceApplication {
    @Value("${alphavantage.api.key}")
    private static String apiKey;

    @Value("${kafka.config.bootstrapServers}")
    private static String bootstrapServers;

    @Value("${kafka.config.topic}")
    private static String topic;

    private static final String stockName = "IBM";

    public static void main(String[] args) throws InterruptedException, URISyntaxException, ExecutionException {
        SpringApplication.run(StockServiceApplication.class, args);
//        String stockHistory = getStockData(stockName, apiKey);
//        KProcessor.stream(bootstrapServers, topic);
//        KProducer.producer(stockHistory, bootstrapServers, topic);
    }
}
