package com.yunus.alert_service;

import org.springframework.stereotype.Component;

@Component
public class AlertDtoConverter {
    public AlertDto convert(Alert alert){
        return new AlertDto(alert.getId(), alert.getStockName(), alert.getMinPrice(), alert.getMaxPrice(), alert.isActive());
    }
}