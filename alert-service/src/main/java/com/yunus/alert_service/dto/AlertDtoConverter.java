package com.yunus.alert_service.dto;

import com.yunus.alert_service.model.Alert;
import org.springframework.stereotype.Component;

@Component
public class AlertDtoConverter {
    public AlertDto convertToDto(Alert alert) {
        return new AlertDto(alert.getId(), alert.getStockName(), alert.getMinPrice(), alert.getMaxPrice(), alert.isActive());
    }

    public Alert convertToModel(AlertDto alert) {
        return new Alert(alert.getStockName(), alert.getMinPrice(), alert.getMaxPrice(), alert.isActive());
    }
}