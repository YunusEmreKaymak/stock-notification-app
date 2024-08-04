package com.yunus.alert_service;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class AlertDto {
    private String id;
    private String stockName;
    private Double maxPrice;
    private Double minPrice;
    private boolean isActive;
}