package com.yunus.alert_service;

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

    public void createAlert(Alert alert) {
        alertRepository.save(alert);
    }

    public List<AlertDto> getAllAlerts() {
        return alertRepository.findAll().stream().map(alert -> alertDtoConverter.convert(alert)).toList();
    }

    public AlertDto getAlertById(String id) {
        if (!alertRepository.existsById(id)) throw new AlertNotFoundException("Alert not found by id: " + id);

        return alertDtoConverter.convert(alertRepository.findById(id).get());
    }

    public void deleteAlertById(String id) {
        if (!alertRepository.existsById(id)) throw new AlertNotFoundException("Alert not found by id: " + id);
        alertRepository.deleteById(id);
    }
}