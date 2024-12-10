package com.yunus.stock_persistence_service.model;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.util.Date;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class StockHistoryId {
    @Nonnull
    private String symbol;

    @Nonnull
    private Date date;
}