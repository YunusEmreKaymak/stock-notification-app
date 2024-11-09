package com.yunus.stock_service.client;

import com.yunus.stock_service.dto.AlertDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "alert-service", path = "/alert", url = "${alert.service.url}")
public interface AlertServiceClient {
    @PostMapping()
    void createAlert(@RequestBody AlertDto alert);

    @GetMapping()
    List<AlertDto> getAllAlerts();

    @GetMapping("/{id}")
    AlertDto getAlertById(@PathVariable("id") String id);

    @DeleteMapping("/{id}")
    void deleteAlertById(@PathVariable("id") String id);
}
