package com.yunus.stock_persistence_service.repository;

import com.yunus.stock_persistence_service.model.StockHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockHistoryRepository extends JpaRepository<StockHistory, String> {
}
