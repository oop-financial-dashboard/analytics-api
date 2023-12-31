package oop.analyticsapi.Repository;

import oop.analyticsapi.Entity.StockDailyPrice.StockDailyPriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface StockDailyPriceRepository extends JpaRepository<StockDailyPriceEntity, Long> {
    @Query("""
        SELECT s FROM StockDailyPriceEntity s WHERE s.symbol = :symbol AND s.timestamp = :timestamp
    """)
    Optional<StockDailyPriceEntity> getStockDailyPriceBySymbol(@Param("symbol") String symbol,
                                                               @Param("timestamp") LocalDate timestamp);

    @Query("""
        SELECT s FROM StockDailyPriceEntity s WHERE s.symbol = :symbol order by s.timestamp desc limit 1
    """)
    StockDailyPriceEntity getLatestStockPrice(@Param("symbol") String symbol);

    @Query("""
        SELECT s FROM StockDailyPriceEntity s WHERE s.symbol = :symbol AND s.timestamp <= :end AND s.timestamp >= :start
        ORDER BY s.timestamp ASC
    """)
    List<StockDailyPriceEntity> getStockHistoricals(@Param("symbol") String symbol, @Param("end") LocalDate end, @Param("start") LocalDate start);
}
