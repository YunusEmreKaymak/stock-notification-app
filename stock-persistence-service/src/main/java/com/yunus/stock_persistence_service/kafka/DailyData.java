package com.yunus.stock_persistence_service.kafka;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Embeddable
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