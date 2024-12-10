package com.yunus.stock_persistence_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class StockHistoryNotFoundException extends RuntimeException {
    public StockHistoryNotFoundException(String message) {
        super(message);
    }
}