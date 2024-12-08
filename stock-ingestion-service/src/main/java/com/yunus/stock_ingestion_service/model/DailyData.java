package com.yunus.stock_ingestion_service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DailyData {
    @JsonProperty("1. open")
    public double open;
    @JsonProperty("2. high")
    public double high;
    @JsonProperty("3. low")
    public double low;
    @JsonProperty("4. close")
    public double close;
    @JsonProperty("5. volume")
    public long volume;
}
