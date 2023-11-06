package oop.analyticsapi.Repository;

import oop.analyticsapi.Entity.PortfolioHistoricals.PortfolioValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PortfolioHistoricalValueRepository extends JpaRepository<PortfolioValue, Long> {
    @Query(value = """
           SELECT p FROM PortfolioValue p WHERE p.portfolioId = :portfolioId AND p.userId = :userId ORDER BY p.date ASC 
       """)
    List<PortfolioValue> getPortfolioHistoricals(@Param("portfolioId") String portfolioId, @Param("userId") String userId);

    @Modifying
    @Query(value = """
           DELETE FROM PortfolioValue p WHERE p.portfolioId = :portfolioId AND p.userId = :userId AND p.symbol = :symbol AND p.date >= :date
       """)
    void deleteAfterDate(String userId, String portfolioId, String symbol, LocalDate date);
}
