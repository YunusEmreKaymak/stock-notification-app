package com.yunus.stock_persistence_service.repository;

import com.yunus.stock_persistence_service.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, String> {
    boolean existsBySymbol(String symbol);

    Optional<Stock> findBySymbol(String symbol);
}
