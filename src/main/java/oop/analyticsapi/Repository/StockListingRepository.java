package oop.analyticsapi.Repository;

import oop.analyticsapi.Entity.StockListing.StockListing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StockListingRepository extends JpaRepository<StockListing, Long> {
    @Query(value = """
        SELECT p FROM StockListing p WHERE p.symbol = :symbol
    """)
    Optional<StockListing> getAvailableStockBySymbol(@Param("symbol") String symbol);

    @Query(value = """
        SELECT p FROM StockListing p
    """)
    List<StockListing> getAvailableStocks();

}
