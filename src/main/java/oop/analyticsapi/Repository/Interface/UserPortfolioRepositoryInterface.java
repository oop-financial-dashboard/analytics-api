package oop.analyticsapi.Repository.Interface;

import oop.analyticsapi.Entity.UserPortfolio.UserPortfolioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

public interface UserPortfolioRepositoryInterface extends JpaRepository<UserPortfolioEntity, Long> {
    @Query("""
        SELECT p FROM PortfolioEntity p WHERE p.portfolioId = :portfolioId
    """)
    List<UserPortfolioEntity> getAllPortfoliosByUserId(String portfolioId);

}
