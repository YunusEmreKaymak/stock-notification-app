package com.yunus.stock_persistence_service.kafka;

import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProcessedData {

    private Map<String, DailyData> dailyDataMap = new HashMap<>();

    @JsonAnySetter
    public void setDailyData(String date, DailyData data) {
        dailyDataMap.put(date, data);
    }

    public Map<String, DailyData> getDailyDataMap() {
        return dailyDataMap;
    }

    public void setDailyDataMap(Map<String, DailyData> dailyDataMap) {
        this.dailyDataMap = dailyDataMap;
    }

    public List<String> getKeyList() {
        return new ArrayList<>(dailyDataMap.keySet());
    }
}