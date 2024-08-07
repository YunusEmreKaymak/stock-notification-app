package com.yunus.alert_service.model;


import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.*;


@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Alert {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Nonnull
    private String id;
    @Column
    @Nonnull
    private String stockName;
    @Column
    @Nonnull
    private Double maxPrice;
    @Column
    @Nonnull
    private Double minPrice;
    @Column
    @Nonnull
    private boolean isActive;
}