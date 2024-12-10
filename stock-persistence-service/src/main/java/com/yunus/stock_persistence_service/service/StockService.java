package com.yunus.stock_persistence_service.service;

import com.yunus.stock_persistence_service.dto.StockDto;
import com.yunus.stock_persistence_service.dto.StockDtoConverter;
import com.yunus.stock_persistence_service.exception.StockNotFoundException;
import com.yunus.stock_persistence_service.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockService {
    private final StockRepository stockRepository;
    private final StockDtoConverter stockDtoConverter;


    @Autowired
    public StockService(StockRepository stockRepository, StockDtoConverter stockDtoConverter) {
        this.stockRepository = stockRepository;
        this.stockDtoConverter = stockDtoConverter;
    }

    public void createStock(StockDto stock) {
        stockRepository.save(stockDtoConverter.convertToModel(stock));
    }

    public List<StockDto> getAllStocks() {
        return stockRepository.findAll().stream().map(stockDtoConverter::convertToDto).toList();
    }

    public StockDto getStockById(String id) {
        if (!stockRepository.existsById(id)) throw new StockNotFoundException("Stock not found by id: " + id);

        return stockDtoConverter.convertToDto(stockRepository.findById(id).get());
    }

    public StockDto getStockBySymbol(String symbol) {
        if (!stockRepository.existsBySymbol(symbol))
            throw new StockNotFoundException("Stock not found by symbol: " + symbol);

        return stockDtoConverter.convertToDto(stockRepository.findBySymbol(symbol).get());
    }

    public boolean doesStockExistBySymbol(String symbol) {
        return stockRepository.existsBySymbol(symbol);
    }

    public void deleteStockById(String id) {
        if (!stockRepository.existsById(id)) throw new StockNotFoundException("Stock not found by id: " + id);
        stockRepository.deleteById(id);
    }
}
