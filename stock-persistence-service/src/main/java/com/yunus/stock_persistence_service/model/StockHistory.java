package com.yunus.stock_persistence_service.model;

import com.yunus.stock_persistence_service.kafka.DailyData;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class StockHistory {
    @EmbeddedId
    private StockHistoryId id;
    @Column
    @Nonnull
    private String name;
    @Embedded
    @Nonnull
    private DailyData dailyData;


    @ManyToOne
    @JoinColumn(name = "symbol", referencedColumnName = "symbol", insertable = false, updatable = false)
    private Stock stock;
}
