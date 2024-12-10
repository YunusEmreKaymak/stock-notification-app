package com.yunus.stock_persistence_service.controller;

import com.yunus.stock_persistence_service.dto.StockDto;
import com.yunus.stock_persistence_service.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stock")
public class StockController {
    private final StockService stockService;

    @Autowired
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @PostMapping()
    public void createStock(@RequestBody StockDto stock) {
        stockService.createStock(stock);
    }

    @GetMapping()
    public List<StockDto> getAllStocks() {
        return stockService.getAllStocks();
    }

    @GetMapping("/{id}")
    public StockDto getStockById(@PathVariable("id") String id) {
        return stockService.getStockById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteStockById(@PathVariable("id") String id) {
        stockService.deleteStockById(id);
    }
}
