package com.yunus.stock_service;

import com.yunus.stock_service.kafka.KProducer;
import com.yunus.stock_service.kafka.KProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

import static com.yunus.stock_service.stock.FetchStock.getStockData;

@SpringBootApplication
public class StockServiceApplication {
    @Value("${alphavantage.api.key}")
    private static String apiKey;

    @Value("${kafka.config.bootstrapServers}")
    private static String bootstrapServers;

    @Value("${kafka.config.topic}")
    private static String topic;

    private static String stockName = "IBM";

    public static void main(String[] args) throws InterruptedException, URISyntaxException, ExecutionException {
        String stockHistory = getStockData(stockName, apiKey);
        KProcessor.stream(bootstrapServers, topic);
        KProducer.producer(stockHistory, bootstrapServers, topic);
    }
}
