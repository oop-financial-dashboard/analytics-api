package oop.analyticsapi.Repository.Interface;

import oop.analyticsapi.Entity.StockDescription.StockDescriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface StockDescriptionRepository extends JpaRepository<StockDescriptionEntity, Long> {
    @Query("""
        SELECT s FROM StockDescriptionEntity s WHERE s.symbol = :symbol
    """)
    Optional<StockDescriptionEntity> getStockDescriptionBySymbol(String symbol);

}
