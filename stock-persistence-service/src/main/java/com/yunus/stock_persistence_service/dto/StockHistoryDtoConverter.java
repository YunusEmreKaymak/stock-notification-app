package com.yunus.stock_persistence_service.dto;

import com.yunus.stock_persistence_service.model.StockHistory;
import com.yunus.stock_persistence_service.model.StockHistoryId;
import org.springframework.stereotype.Component;

@Component
public class StockHistoryDtoConverter {
    public StockHistoryDto convertToDto(StockHistory stockHistory) {
        return new StockHistoryDto(stockHistory.getId().getSymbol(), stockHistory.getName(), stockHistory.getId().getDate(), stockHistory.getStock(), stockHistory.getDailyData());
    }

    public StockHistory convertToModel(StockHistoryDto stockDto) {
        return new StockHistory(new StockHistoryId(stockDto.getSymbol(), stockDto.getDate()), stockDto.getName(), stockDto.getDailyData(), stockDto.getStock());
    }
}
