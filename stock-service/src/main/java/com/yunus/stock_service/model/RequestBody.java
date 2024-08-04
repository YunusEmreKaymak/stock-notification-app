package com.yunus.stock_service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@Data
public class RequestBody {
    @JsonProperty("Meta Data")
    public MetaData metaData;
    @JsonProperty("Time Series (Daily)")
    public Map<String, DailyData> dailyData;
}

