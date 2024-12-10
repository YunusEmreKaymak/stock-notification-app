package com.yunus.stock_persistence_service.dto;


import com.yunus.stock_persistence_service.model.StockHistory;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
public class StockDto {
    private String id;
    private String symbol;
    private String name;
    private List<StockHistory> stockHistoryList;
}