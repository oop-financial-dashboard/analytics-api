package oop.analyticsapi.Repository;

import oop.analyticsapi.Entity.PortfolioHistoricals.PortfolioValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PortfolioHistoricalValueRepository extends JpaRepository<PortfolioValue, Long> {
    @Query(value = """
           SELECT p FROM PortfolioValue p WHERE p.portfolioId = :portfolioId AND p.userId = :userId
       """)
    List<PortfolioValue> getPortfolioHistoricals(@Param("portfolioId") String portfolioId, @Param("userId") String userId);
}
