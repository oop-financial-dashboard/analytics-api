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
           SELECT p FROM PortfolioEntity p WHERE p.portfolioId = :portfolioId AND p.userId = :userId ORDER BY p.dateAdded ASC
       """)
    List<PortfolioEntity> getAllStocksInPortfolio(@Param("userId") String userId, @Param("portfolioId") String portfolioId);

    @Query(value = """
           SELECT s FROM PortfolioEntity s WHERE s.userId = :userId AND s.portfolioId = :portfolioId AND s.symbol = :symbol
       """)
    PortfolioEntity getOneStockInfo( @Param("userId") String userId, @Param("portfolioId") String portfolioId, @Param("symbol") String symbol);

    @Query(value = """
           SELECT DISTINCT s.userId, s.portfolioId, s.dateAdded FROM PortfolioEntity s
       """)
    List<Object[]> getAllPortfolioIds();

    @Modifying
    @Query(value = """
         DELETE FROM PortfolioEntity WHERE portfolioId = :portfolioId AND userId = :userId
         """)
    int deletePortfolioEntry(@Param("portfolioId") String portfolioId, @Param("userId") String userId);

    @Modifying
    @Query(value = """
         DELETE FROM PortfolioEntity WHERE userId = :userId AND portfolioId = :portfolioId AND symbol = :symbol
         """)
    int deleteOnePortfolioEntry(@Param("userId") String userId, @Param("portfolioId") String portfolioId, @Param("symbol") String symbol);

}
