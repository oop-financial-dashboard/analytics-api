package oop.analyticsapi.Repository;

import oop.analyticsapi.Entity.Portfolio.PortfolioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PortfolioRepository extends JpaRepository<PortfolioEntity, Long> {
    @Query(value = """
           SELECT p FROM PortfolioEntity p WHERE p.portfolioId = :portfolioId
       """)
    List<PortfolioEntity> getAllStocksInPortfolio(@Param("portfolioId") String portfolioId);

    @Query(value = """
           SELECT s FROM PortfolioEntity s WHERE s.portfolioId = :portfolioId AND s.symbol = :symbol
       """)
    PortfolioEntity getOneStockInfo( @Param("portfolioId") String portfolioId, @Param("symbol") String symbol);

    @Modifying
    @Query(value = """
           INSERT INTO PortfolioEntity (portfolioId, quantity, symbol, averageCost, totalValue)
           VALUES (:portfolioId, :quantity, :symbol, :averageCost, :totalValue);
    """, nativeQuery = true)
    int createPortfolioEntry(@Param("portfolioId") String portfolioId,
                             @Param("quantity") int quantity,
                             @Param("symbol") String symbol,
                             @Param("averageCost") double averageCost,
                             @Param("totalValue") Long totalValue
    );

    @Modifying
    @Query(value = """
         DELETE FROM PortfolioEntity WHERE portfolioId = :portfolioId
         """)
    int deletePortfolioEntry(@Param("portfolioId") String portfolioId);

    //Update
    //For updating values/quantity/avg cost
    @Modifying
    @Query(value = """
       UPDATE PortfolioEntity SET quantity = :quantity, averageCost = :averageCost, totalValue = :totalValue
       WHERE portfolioId = :portfolioId AND symbol = :symbol
       """)
    int updatePortfolioEntry(@Param("portfolioId") String portfolioId,
                             @Param("quantity") int quantity,
                             @Param("symbol") String symbol,
                             @Param("averageCost")double averageCost,
                             @Param("totalValue") double totalValue
    );
}
