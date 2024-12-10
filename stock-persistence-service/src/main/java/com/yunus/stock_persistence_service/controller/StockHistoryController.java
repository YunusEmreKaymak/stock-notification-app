package com.yunus.stock_persistence_service.controller;

import com.yunus.stock_persistence_service.dto.StockHistoryDto;
import com.yunus.stock_persistence_service.service.StockHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stockHistory")
public class StockHistoryController {
    private final StockHistoryService stockHistoryService;

    @Autowired
    public StockHistoryController(StockHistoryService stockHistoryService) {
        this.stockHistoryService = stockHistoryService;
    }

    @PostMapping()
    public void createStockHistory(@RequestBody StockHistoryDto stockHistory) {
        stockHistoryService.createStockHistory(stockHistory);
    }

    @GetMapping()
    public List<StockHistoryDto> getAllStockHistories() {
        return stockHistoryService.getAllStockHistories();
    }

    @GetMapping("/{id}")
    public StockHistoryDto getStockHistoryById(@PathVariable("id") String id) {
        return stockHistoryService.getStockHistoryById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteStockHistoryById(@PathVariable("id") String id) {
        stockHistoryService.deleteStockHistoryById(id);
    }
}
