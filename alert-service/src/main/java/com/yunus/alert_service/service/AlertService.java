package com.yunus.alert_service.service;

import com.yunus.alert_service.dto.AlertDto;
import com.yunus.alert_service.dto.AlertDtoConverter;
import com.yunus.alert_service.exception.AlertNotFoundException;
import com.yunus.alert_service.repository.AlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlertService {
    private final AlertRepository alertRepository;
    private final AlertDtoConverter alertDtoConverter;


    @Autowired
    public AlertService(AlertRepository alertRepository, AlertDtoConverter alertDtoConverter) {
        this.alertRepository = alertRepository;
        this.alertDtoConverter = alertDtoConverter;
    }

    public void createAlert(AlertDto alert) {
        alertRepository.save(alertDtoConverter.convertToModel(alert));
    }

    public List<AlertDto> getAllAlerts() {
        return alertRepository.findAll().stream().map(alertDtoConverter::convertToDto).toList();
    }

    public AlertDto getAlertById(String id) {
        if (!alertRepository.existsById(id)) throw new AlertNotFoundException("Alert not found by id: " + id);

        return alertDtoConverter.convertToDto(alertRepository.findById(id).get());
    }

    public void deleteAlertById(String id) {
        if (!alertRepository.existsById(id)) throw new AlertNotFoundException("Alert not found by id: " + id);
        alertRepository.deleteById(id);
    }
}
