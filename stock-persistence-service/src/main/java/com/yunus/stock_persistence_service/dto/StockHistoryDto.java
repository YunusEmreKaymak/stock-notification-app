package com.yunus.stock_persistence_service.dto;


import com.yunus.stock_persistence_service.kafka.DailyData;
import com.yunus.stock_persistence_service.model.Stock;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class StockHistoryDto {
    private String symbol;
    private String name;
    private Date date;
    private Stock stock;
    private DailyData dailyData;
}