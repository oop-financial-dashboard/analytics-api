package oop.analyticsapi.Repository;

import oop.analyticsapi.Entity.StockDailyPrice.StockDailyPriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.Optional;

public interface StockDailyPriceRepository extends JpaRepository<StockDailyPriceEntity, Long> {
    @Query("""
        SELECT s FROM StockDailyPriceEntity s WHERE s.symbol = :symbol AND s.date = :timestamp
    """)
    Optional<StockDailyPriceEntity> getStockDailyPriceBySymbol(@Param("symbol") String symbol,
                                                               @Param("timestamp") Timestamp timestamp);
}
