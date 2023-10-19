package oop.analyticsapi.Repository;

import oop.analyticsapi.Entity.StockDailyPrice.StockDailyPriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface StockDailyPriceRepository extends JpaRepository<StockDailyPriceEntity, Long> {
    @Query("""
        SELECT s FROM StockDailyPriceEntity s WHERE s.symbol = :symbol AND s.timestamp = :timestamp
    """)
    Optional<StockDailyPriceEntity> getStockDailyPriceBySymbol(@Param("symbol") String symbol,
                                                               @Param("timestamp") LocalDate timestamp);
}
