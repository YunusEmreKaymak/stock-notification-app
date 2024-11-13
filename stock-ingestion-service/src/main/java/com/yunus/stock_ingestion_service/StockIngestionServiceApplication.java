package com.yunus.stock_ingestion_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class StockIngestionServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(StockIngestionServiceApplication.class, args);
    }
}
