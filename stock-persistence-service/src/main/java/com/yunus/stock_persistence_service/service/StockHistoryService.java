package com.yunus.stock_persistence_service.service;

import com.yunus.stock_persistence_service.dto.StockHistoryDto;
import com.yunus.stock_persistence_service.dto.StockHistoryDtoConverter;
import com.yunus.stock_persistence_service.exception.StockHistoryNotFoundException;
import com.yunus.stock_persistence_service.repository.StockHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockHistoryService {
    private final StockHistoryRepository stockHistoryRepository;
    private final StockHistoryDtoConverter stockHistoryDtoConverter;


    @Autowired
    public StockHistoryService(StockHistoryRepository stockHistoryRepository, StockHistoryDtoConverter stockHistoryDtoConverter) {
        this.stockHistoryRepository = stockHistoryRepository;
        this.stockHistoryDtoConverter = stockHistoryDtoConverter;
    }

    public void createStockHistory(StockHistoryDto stockHistory) {
        stockHistoryRepository.save(stockHistoryDtoConverter.convertToModel(stockHistory));
    }

    public List<StockHistoryDto> getAllStockHistories() {
        return stockHistoryRepository.findAll().stream().map(stockHistoryDtoConverter::convertToDto).toList();
    }

    public StockHistoryDto getStockHistoryById(String id) {
        if (!stockHistoryRepository.existsById(id))
            throw new StockHistoryNotFoundException("Stock History not found by id: " + id);

        return stockHistoryDtoConverter.convertToDto(stockHistoryRepository.findById(id).get());
    }

    public void deleteStockHistoryById(String id) {
        if (!stockHistoryRepository.existsById(id))
            throw new StockHistoryNotFoundException("Stock History not found by id: " + id);
        stockHistoryRepository.deleteById(id);
    }
}
