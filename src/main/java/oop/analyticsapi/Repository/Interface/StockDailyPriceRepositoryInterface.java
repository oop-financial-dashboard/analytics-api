package oop.analyticsapi.Repository.Interface;

import oop.analyticsapi.Entity.StockDailyPrice.StockDailyPriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.Optional;

public interface StockDailyPriceRepositoryInterface extends JpaRepository<StockDailyPriceEntity, Long> {
    @Query("""
        SELECT s FROM StockDailyPriceEntity s WHERE s.symbol = :symbol AND s.date = :timestamp
    """)
    Optional<StockDailyPriceEntity> getStockDailyPriceBySymbol(String symbol, Timestamp timestamp);
}
