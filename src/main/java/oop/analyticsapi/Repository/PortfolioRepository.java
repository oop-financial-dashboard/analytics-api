package oop.analyticsapi.Repository;

import oop.analyticsapi.Domain.Models.PortfolioUserIds;
import oop.analyticsapi.Entity.Portfolio.PortfolioEntity;
import oop.analyticsapi.Entity.Portfolio.PortfolioId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PortfolioRepository extends JpaRepository<PortfolioEntity, Long> {
    @Query(value = """
           SELECT p FROM PortfolioEntity p WHERE p.portfolioId = :portfolioId AND p.userId = :userId
       """)
    List<PortfolioEntity> getAllStocksInPortfolio(@Param("userId") String userId, @Param("portfolioId") String portfolioId);

    @Query(value = """
           SELECT s FROM PortfolioEntity s WHERE s.portfolioId = :portfolioId AND s.symbol = :symbol
       """)
    PortfolioEntity getOneStockInfo( @Param("portfolioId") String portfolioId, @Param("symbol") String symbol);

    @Query(value = """
           SELECT DISTINCT s.userId, s.portfolioId FROM PortfolioEntity s
       """)
    List<PortfolioUserIds> getAllPortfolioIds();

    @Modifying
    @Query(value = """
         DELETE FROM PortfolioEntity WHERE portfolioId = :portfolioId
         """)
    int deletePortfolioEntry(@Param("portfolioId") String portfolioId);

    @Modifying
    @Query(value = """
         DELETE FROM PortfolioEntity WHERE portfolioId = :portfolioId AND symbol = :symbol
         """)
    int deleteOnePortfolioEntry(@Param("portfolioId") String portfolioId, @Param("symbol") String symbol);

    //Update
    //For updating values/quantity/avg cost
    @Modifying
    @Query(value = """
       UPDATE PortfolioEntity SET quantity = :quantity, averageCost = :averageCost, totalValue = :totalValue
       WHERE portfolioId = :portfolioId AND symbol = :symbol
       """, nativeQuery = true)
    int updatePortfolioEntry(@Param("portfolioId") String portfolioId,
                             @Param("quantity") int quantity,
                             @Param("symbol") String symbol,
                             @Param("averageCost")double averageCost,
                             @Param("totalValue") double totalValue
    );
}
