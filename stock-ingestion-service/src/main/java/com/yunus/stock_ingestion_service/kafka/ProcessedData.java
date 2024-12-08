package com.yunus.stock_ingestion_service.kafka;

import com.yunus.stock_ingestion_service.model.DailyData;

public class ProcessedData {
    String lastRefreshedDay;
    DailyData dailyData;
}
