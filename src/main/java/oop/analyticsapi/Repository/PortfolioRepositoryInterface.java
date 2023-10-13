package oop.analyticsapi.Repository;

import oop.analyticsapi.Entity.Portfolio.PortfolioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PortfolioRepositoryInterface extends JpaRepository<PortfolioEntity, Long> {
    @Query(value = """
           SELECT * FROM PortfolioEntity p WHERE p.portfolioId = :portfolioId
       """, nativeQuery = true)
    List<PortfolioEntity> getAllStocksInPortfolio(String portfolioId);

    @Query(value = """
           SELECT s FROM PortfolioEntity s WHERE s.portfolioId = :portfolioId AND s.symbol = :symbol
       """)
    PortfolioEntity getOneStockInfo(String portfolioId, String symbol);

    @Modifying
    @Query(value = """
           INSERT INTO PortfolioEntity (portfolioId, quantity, symbol, averageCost, totalValue)
           VALUES (:portfolioId, :quantity, :symbol, :averageCost, :totalValue);
    """, nativeQuery = true)
    int createPortfolioEntry(String portfolioId, int quantity, String symbol, double averageCost, Long totalValue);

    @Modifying
    @Query(value = """
         DELETE FROM PortfolioEntity WHERE portfolioId = :portfolioId
         """, nativeQuery = true)
    int deletePortfolioEntry(String portfolioId);

    //Update
    //For updating values/quantity/avg cost
    @Modifying
    @Query(value = """
       UPDATE PortfolioEntity SET quantity = :quantity, averageCost = :averageCost, totalValue = :totalValue
       WHERE portfolioId = :portfolioId AND symbol = :symbol
       """, nativeQuery = true)
    int updatePortfolioEntry(String portfolioId, int quantity, String symbol, double averageCost, double totalValue);
}
