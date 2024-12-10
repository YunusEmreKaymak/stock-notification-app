package com.yunus.stock_persistence_service.dto;

import com.yunus.stock_persistence_service.model.Stock;
import org.springframework.stereotype.Component;

@Component
public class StockDtoConverter {
    public StockDto convertToDto(Stock stock) {
        return new StockDto(stock.getId(), stock.getSymbol(), stock.getName(), stock.getStockHistory());
    }

    public Stock convertToModel(StockDto stockDto) {
        return new Stock(stockDto.getId(), stockDto.getSymbol(), stockDto.getName(), stockDto.getStockHistoryList());
    }
}
