package com.yunus.alert_service.controller;

import com.yunus.alert_service.dto.AlertDto;
import com.yunus.alert_service.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alert")
public class AlertController {
    private final AlertService alertService;

    @Autowired
    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    @PostMapping()
    public void createAlert(@RequestBody AlertDto alert) {
        alertService.createAlert(alert);
    }

    @GetMapping()
    public List<AlertDto> getAllAlerts(){
        return alertService.getAllAlerts();
    }

    @GetMapping("/{id}")
    public AlertDto getAlertById(@PathVariable("id") String id) {
        return alertService.getAlertById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteAlertById(@PathVariable("id") String id) {
        alertService.deleteAlertById(id);
    }
}
